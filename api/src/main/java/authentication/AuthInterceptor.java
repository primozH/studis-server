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
    private HttpServletRequest httpServletRequest;

    @AroundInvoke
    public Object check(InvocationContext context) throws Exception {
        Role[] roles = context.getMethod().getAnnotation(Auth.class).rolesAllowed();

        Role role = (Role) httpServletRequest.getAttribute("role");
        if (role != null) {
            if (Arrays.asList(roles).contains(role)) {
                return context.proceed();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
