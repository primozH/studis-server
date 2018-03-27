import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api/v1")
public class RESTVir extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return super.getClasses();
    }
}
