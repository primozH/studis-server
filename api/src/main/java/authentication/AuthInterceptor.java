package authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import vloge.Ucitelj;
import vloge.Uporabnik;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Interceptor
@Auth
public class AuthInterceptor {
    private final static Logger log = Logger.getLogger(AuthInterceptor.class.getName());

    @Inject
    HttpServletRequest httpServletRequest;

    @AroundInvoke
    public Object check(InvocationContext context) throws Exception {
        Role[] roles = context.getMethod().getAnnotation(Auth.class).rolesAllowed();

        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null && authorization.toLowerCase().startsWith("bearer")) {
            DecodedJWT token = JWT.decode(authorization.substring(7));
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

            Object[] parameters = context.getParameters();
            if (Arrays.asList(roles).contains(role)) {
                List<Class<?>> clazz = Arrays.asList(context.getMethod().getParameterTypes());
                for (int i = 0; i < clazz.size(); i++) {
                    if (Uporabnik.class.isAssignableFrom(clazz.get(i))) {
                        Uporabnik uporabnik = new Uporabnik();
                        uporabnik.setId(id);
                        parameters[i] = uporabnik;
                        context.setParameters(parameters);
                    }
                }

                return context.proceed();
            }
            else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
