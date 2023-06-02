package fr.univtln.tdomenge293;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univtln.tdomenge293.model.Department;
import fr.univtln.tdomenge293.model.Employee;
import fr.univtln.tdomenge293.model.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.xml.bind.JAXB;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.StringWriter;
import java.net.URI;

/**
 * Hello world!
 */
@Slf4j
public class App {
    public static final String BASE_URI = "http://0.0.0.0:8080/api/";

    public static void main ( String[] args ) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TpPU");
        EntityManager em = emf.createEntityManager();
        log.info("hello");
        log.trace("e");
        Department d = Department.of("ee");
        Employee e = Engineer.of("test");
        e.setName("e");
        e.setDepartment(d);
        em.getTransaction().begin();
        em.persist(d);
        em.persist(e);
        log.info(em.find(Department.class, d.getId()).getEmployees().toString());
        em.getTransaction().commit();
        em.close();
        emf.close();
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.info(mapper.writeValueAsString(e));
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(ex);
        }
        StringWriter sw = new StringWriter();
        JAXB.marshal(e, sw);
        JAXB.marshal(d, sw);
        log.info(sw.toString());
        final ResourceConfig rc = new ResourceConfig().packages("fr.univtln.tdomenge293.rest");
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


}
