package rest.viri;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParser;
import common.Email;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import orodja.GeneratorPodatkov;
import prijava.Prijava;
import vloge.Uporabnik;

@Path("avtorizacija")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PrijavaVir {

    @PersistenceContext(unitName = "studis")
    private EntityManager em;

    @Inject
    private GeneratorPodatkov generator;

    private static Logger logger = Logger.getLogger(PrijavaVir.class.getSimpleName());
    private static final String STUDIS_MAIL = "studis.info.info@gmail.com";
    private static final String STUDIS_GESLO = "studis123";
    private static final int EXPIRATION_TIME_MINUTES = 15;

    /**
     * Primer responsa: {"jwtToken":"hashedString"}
     *
     * @param prijava
     * @return json, ki vsebuje token
     */
    @Path("prijava")
    @POST
    public Response preveriPrijavo(Prijava prijava) {
        logger.info("preveriPrijavo");
        // Preveri, ce oseba obstaja v bazi
        Uporabnik uporabnik;
        try {
            uporabnik = (Uporabnik) this.em.createNamedQuery("entitete.vloge.Uporabnik.prijava")
                                                     .setParameter("uporabniskoIme", prijava.getUporabniskoIme()).getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        logger.info("Uporabnik najden.");

        if (uporabnik.primerjajGeslo(prijava.getGeslo())) {
            String token = null;
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                token = JWT.create()
                            .withClaim("tip", uporabnik.getTip())
                            .withClaim("uid", uporabnik.getId())
                            .withExpiresAt(Date.from(ZonedDateTime.now().plusMinutes(EXPIRATION_TIME_MINUTES).toInstant()))
                            .sign(algorithm);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (token == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

            return Response.ok(new JSONObject().put("access_token", token).toString()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Path("pozabljeno-geslo")
    @POST
    @Transactional
    public Response posljiGesloNaMail(Email emailObj) {
        logger.info("posljiGesloNaMail");
        // Vrni uporabnika iz baze (ce ga ni, error)
        Uporabnik uporabnik;

        try {
            uporabnik = (Uporabnik) this.em.createNamedQuery("entitete.vloge.Uporabnik.pozabljeno.geslo")
                                           .setParameter("email", emailObj.getEmail()).getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        logger.info("Uporabnik najden.");
        String geslo = generator.generirajGeslo();
        uporabnik.setGeslo(geslo);
        em.merge(uporabnik);
        String subject = "Ponastavljeno geslo - Studis";
        String body = "Spoštovani,\n\nPošiljamo Vam ponastavljeno geslo za sistem Studis,"
                      + " s katerim se poleg vašega elektronskega naslova prijavite v sistem.\n\nGeslo:"
                      + geslo
                      + "\n\nLep pozdrav,\nekipa Studis";
        if (!sendFromGMail(uporabnik.getEmail(), subject, body)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    /**
     * Posiljanje maila z geslom.
     *
     * @param to
     * @param subject
     * @param body
     */
    private boolean sendFromGMail(String to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", STUDIS_MAIL);
        props.put("mail.smtp.password", STUDIS_GESLO);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(STUDIS_MAIL));
            InternetAddress toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, STUDIS_MAIL, STUDIS_GESLO);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception me) {
            me.printStackTrace();
            return false;
        }
        return true;
    }
}
