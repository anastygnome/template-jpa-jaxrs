package fr.univtln.tdomenge293;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import fr.univtln.tdomenge293.model.Department;
import fr.univtln.tdomenge293.model.Employee;
import fr.univtln.tdomenge293.model.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.xml.bind.JAXB;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http2.Http2AddOn;
import org.glassfish.grizzly.http2.Http2Configuration;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.time.Instant;
import java.util.Date;

import static javax.security.auth.login.Configuration.getConfiguration;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static final String BASE_URI = "http://0.0.0.0:8080/api/";
    public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpPU");
    public static void main ( String[] args ) {
        EntityManager em = emf.createEntityManager();
                Department d = Department.of("ee");
                Employee e = Engineer.of("test", Date.from(Instant.now()),"test",d);
                Employee f = Engineer.of("test", Date.from(Instant.now()),"test",d);

        em.getTransaction().begin();
        em.persist(d);
        em.persist(e);
        em.persist(f);
        log.info(em.find(Department.class, d.getId()).getEmployees().toString());
                em.getTransaction().commit();
                em.close();
        final ResourceConfig rc = new ResourceConfig().packages("fr.univtln.tdomenge293.rest");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        Http2Configuration configuration = Http2Configuration.builder().build();
        Http2AddOn http2Addon = new Http2AddOn(configuration);
    NetworkListener listener =
                new NetworkListener("HTTPS",
                        NetworkListener.DEFAULT_NETWORK_HOST,
                        8081);
        listener.setSecure(false);
        listener.registerAddOn(http2Addon);
        server.addListener(listener);

    }
}
