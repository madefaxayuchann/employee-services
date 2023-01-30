package id.kawahedukasi.controller;

import id.kawahedukasi.service.EmployeeScoreServices;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Path("/employee_score")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeScoreController {
  @Inject
  EmployeeScoreServices employeeScoreServices;

  @Inject
  EntityManager em;

  @POST
  public Response create(JsonObject request) {
    Map<String, Object> result = new HashMap<>();
    try {
      Map<String, Object> data = employeeScoreServices.create(request);
      result.put("data", data);
      return Response.ok().entity(result).build();
    } catch (ValidationException e) {
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
  }

  @GET
  @Path("/avg_score/{id}")
  public JsonObject avgScore(@PathParam("id") Integer id) {
    Query query = em.createNativeQuery(employeeScoreServices.avgScoreQuery);
    query.setParameter("id", id);
    BigDecimal resultBigDecimal = (BigDecimal) query.getSingleResult();
    Double result = resultBigDecimal.doubleValue();
    return new JsonObject().put("overallAverageScore", result);

  }
}
