import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api/v1")
public class RESTEndpoint extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return super.getClasses();
    }
}
