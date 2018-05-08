package authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import vloge.Uporabnik;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext context) throws IOException {
        String authorization = context.getHeaderString("Authorization");

        if (authorization != null && authorization.toLowerCase().startsWith("bearer")) {
            DecodedJWT token = JWT.decode(authorization.split(" ")[1]);
            String roleStr = token.getClaim("tip").asString();
            Integer id = token.getClaim("uid").asInt();

            log.info("role: " + roleStr + ", id: " + id);
            Role role = null;
            switch (roleStr.toLowerCase()) {
                case "student":
                    role = Role.STUDENT;
                    break;
                case "ucitelj":
                    role = Role.PREDAVATELJ;
                    break;
                case "referent":
                    role = Role.REFERENT;
                    break;
                case "admin":
                    role = Role.ADMIN;
            }

            if (role != null) {
                Uporabnik uporabnik = new Uporabnik();
                uporabnik.setId(id);

                context.setProperty("user", uporabnik);
                context.setProperty("role", role);
            }
        }

    }
}
