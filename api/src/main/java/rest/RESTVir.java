package rest;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/api/v1")
@CrossOrigin(supportedMethods = "GET, POST, UPDATE, DELETE, HEAD")
public class RESTVir extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return super.getClasses();
    }
}
