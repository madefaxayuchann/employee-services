package id.kawahedukasi.controller;

import id.kawahedukasi.service.FactorialServices;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/factorial")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FactorialController {

  @Inject
  FactorialServices factorialServices;

  @POST
  public Response create(JsonObject request) {
    Map<String, Object> result = new HashMap<>();
    try {
      Map<String, Object> data = factorialServices.generateFactorial(request);
      result.put("data", data);
      return Response.ok().entity(result).build();
    } catch (ValidationException e) {
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }
  }

  @GET
  public Response list() {
    List<Map<String, Object>> factorial = factorialServices.getAll();
    Map<String, Object> result = new HashMap<>();
    result.put("data", factorial);
    return Response.ok().entity(result).build();
  }


}
