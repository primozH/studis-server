package viri;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import prijava.Prijava;
import vloge.Uporabnik;

@Path("prijava")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PrijavaVir {

    @PersistenceContext(unitName = "studis")
    private EntityManager em;

    private static Logger logger = Logger.getLogger(PrijavaVir.class.getSimpleName());
    private static final String STUDIS_MAIL = "studis.info.info@gmail.com";
    private static final String STUDIS_GESLO = "studis123";

    /**
     * Primer responsa: {"jwtToken":"hashedString"}
     *
     * @param prijava
     * @return json, ki vsebuje token
     */
    @POST
    public Response preveriPrijavo(Prijava prijava) {
        logger.info("preveriPrijavo");
        // Preveri, ce oseba obstaja v bazi
        Uporabnik uporabnik = (Uporabnik) this.em.createNamedQuery("entitete.vloge.Uporabnik.prijava")
                                                 .setParameter("email", prijava.getEmail()).getSingleResult();
        if (uporabnik == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (uporabnik.primerjajGeslo(prijava.getGeslo())) {
            String token = null;
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret");
                token = JWT.create()
                           .withClaim("tip", uporabnik.getTip())
                           .withClaim("uid", uporabnik.getId())
                           .sign(algorithm);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (token == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }

            return Response.ok(new JSONObject().put("jwtToken", token).toString()).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    public Response posljiGesloNaMail(Prijava prijava) {
        logger.info("posljiGesloNaMail");
        // Vrni uporabnika iz baze (ce ga ni, error)
        Uporabnik uporabnik = (Uporabnik) this.em.createNamedQuery("entitete.vloge.Uporabnik.prijava")
                                                 .setParameter("email", prijava.getEmail())
                                                 .getSingleResult();
        if (uporabnik != null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        String geslo = generirajRandomGeslo();
        uporabnik.setGeslo(geslo);

        String subject = "Ponastavljeno geslo - Studis";
        String body = "Spoštovani,\n\nPošiljamo Vam ponastavljeno geslo za sistem Studis,"
                      + " s katerim se poleg vašega elektronskega naslova prijavite v sistem.\n\nGeslo: <b>"
                      + geslo
                      + "<b>\n\nLep pozdrav,\nekipa Studis";

        if (!sendFromGMail(uporabnik.getEmail(), subject, body)) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    private static final String ALFANUMERICNI_ZNAKI = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DOLZINA_RANDOM_GESLA = 10;

    /**
     * Se uporablja v kolikor uporabnik pozabi geslo
     * oz. ob prvi prijavi v sistem.
     *
     * @return
     */
    private String generirajRandomGeslo() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < DOLZINA_RANDOM_GESLA; i++) {
            stringBuilder.append(ALFANUMERICNI_ZNAKI.charAt(random.nextInt(ALFANUMERICNI_ZNAKI.length())));
        }
        return stringBuilder.toString();
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
