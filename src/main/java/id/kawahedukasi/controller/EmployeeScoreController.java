package id.kawahedukasi.controller;

import id.kawahedukasi.service.EmployeeScoreServices;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/employee_score")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeScoreController {
  @Inject
  EmployeeScoreServices employeeScoreServices;

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
}
