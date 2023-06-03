package fr.univtln.tdomenge293.rest;
import fr.univtln.tdomenge293.dao.DepartmentDAO;
import fr.univtln.tdomenge293.model.Department;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.univtln.tdomenge293.App.emf;

@Path("/departments")
public class DepartmentResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllDepartments( @Context UriInfo uriInfo,@DefaultValue("1") @QueryParam("page") int page,@DefaultValue("10") @QueryParam("size") int size) {
        try (DepartmentDAO departmentDAO = new DepartmentDAO(emf.createEntityManager())){
            long totalElements = departmentDAO.getCount();
            long totalPages = (long) Math.ceil((double) totalElements / size);
            if(size > totalElements || page > totalPages) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();

            GenericEntity<List<Department>> entity = new GenericEntity<>(departmentDAO.findAll(page, size)) {};
            List<Link> links = new ArrayList<>(2);
            if (totalPages >  1 && page < totalPages) {
                Link next = Link.fromUri(builder.queryParam("page", page + 1).build()).rel("next").build();
                links.add(next);
            }

                if (page > 1) {
                    Link previous = Link.fromUri(builder.queryParam("page", page - 1).build()).rel("previous").build();
                    links.add(previous);
                }
                return Response.ok(entity)
                        .header("X-Total-Count", totalElements)
                        .header("X-Total-Pages", totalPages)
                        .links(links.toArray(new Link[0]))
                        .build();

        }
    }

/*
    @GET
    @Path("/{id:[0-9a-fA-F]{8}(?:\\b-[0-9a-fA-F]{4}){3}\\b-[0-9a-fA-F]{12}}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Department getDepartment(@PathParam("id") UUID id) {
        return departmentDAO.findById(id);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Department addDepartment(Department department) {
        return departmentDAO.create(department);
    }

    @PUT
    @Path("/{id:[0-9a-fA-F]{8}(?:\\b-[0-9a-fA-F]{4}){3}\\b-[0-9a-fA-F]{12}}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Department updateDepartment( @PathParam("id") UUID id, Department department) {
        Department departmentToUpdate = departmentDAO.findById(id);
        departmentToUpdate.setName(department.getName());
        departmentToUpdate.setEmployees(department.getEmployees());
        return departmentDAO.update(departmentToUpdate);
    }

    @DELETE
    @Path("/{id:[0-9a-fA-F]{8}(\\b-[0-9a-fA-F]{4}){3}\\b-[0-9a-fA-F]{12}}")
    public void deleteDepartment(@PathParam("id") UUID id) {
        Department department = departmentDAO.findById(id);
        departmentDAO.delete(department);
    }*/
}
