package rest;

//import com.kumuluz.ee.cors.annotations.CrossOrigin;
import authentication.AuthFilter;
import izpit.IzpitniRok;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import rest.viri.*;
import sifranti.Predmet;
import vloge.Referent;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/v1")
//@CrossOrigin(supportedMethods = "GET, POST, UPDATE, DELETE, HEAD")
public class RESTVir extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(AuthFilter.class);
        resources.add(KandidatVir.class);
        resources.add(StudentVir.class);
        resources.add(PrijavaVir.class);
        resources.add(ZetonVir.class);
        resources.add(PredmetnikVir.class);
        resources.add(SifrantiVir.class);
        resources.add(IzvozVir.class);
        resources.add(IzpitVir.class);
        resources.add(PredmetVir.class);
        resources.add(IzpitniRokVir.class);
        resources.add(ReferentVir.class);
        resources.add(UciteljVir.class);
        resources.add(AdminVir.class);

        resources.add(MultiPartFeature.class);

        return resources;
    }
}
