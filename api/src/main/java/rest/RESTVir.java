package rest;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import rest.viri.KandidatVir;
import rest.viri.PrijavaVir;
import rest.viri.StudentVir;
import zrna.UvozPodatkov;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
@CrossOrigin(supportedMethods = "GET, POST, UPDATE, DELETE, HEAD")
public class RESTVir extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(KandidatVir.class);
        resources.add(StudentVir.class);
        resources.add(PrijavaVir.class);

        resources.add(MultiPartFeature.class);

        return resources;
    }
}
