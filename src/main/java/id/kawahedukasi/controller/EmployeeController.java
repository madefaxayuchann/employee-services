package id.kawahedukasi.controller;


import id.kawahedukasi.models.Employee;
import id.kawahedukasi.service.EmployeeServices;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController {

  @Inject
  EmployeeServices employeeServices;

  @Inject
  EntityManager em;



  @POST
  public Response create(JsonObject request) {
    Map<String, Object> result = new HashMap<>();
    try {
      Map<String, Object> data = employeeServices.create(request);
      result.put("data", data);
      return Response.ok().entity(result).build();
    } catch (ValidationException e) {
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
  }

  @GET
  @Path("/{id}")
  public List<Map<String, Object>> getSubordinates(@PathParam("id") Integer id) {
    System.out.println(id);
    Query query = em.createNativeQuery(employeeServices.query, Employee.class);
    query.setParameter("id", id);

    List<Map<String, Object>> data = new ArrayList<>();


    List<Employee> results = query.getResultList();
    for (Employee result : results) {
      Map<String, Object> subordinate = new HashMap<>();
      subordinate.put("id", result.getId());
      subordinate.put("name", result.getName());
      subordinate.put("manger_id", result.getManager_id());

      data.add(subordinate);

    }

    return data;
  }

}
