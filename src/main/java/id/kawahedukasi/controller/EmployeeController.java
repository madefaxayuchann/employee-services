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
  @Path("/id/{id}")
  public Response getById(@PathParam("id") Integer id) {
    try {
      List<Map<String, Object>> result = employeeServices.getSubordinates(id);
      return Response.ok().entity(result).build();
    } catch (ValidationException e) {
      Map<String, Object> result = new HashMap<>();
      result.put("message", e.getMessage());
      return Response.status(Response.Status.BAD_REQUEST).entity(result).build();

    }
  }


}
