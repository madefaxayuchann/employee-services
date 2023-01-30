package id.kawahedukasi.service;

import id.kawahedukasi.models.Employee;
import id.kawahedukasi.models.EmployeeScore;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EmployeeScoreServices {

  @Inject
  EntityManager em;

  public String avgScoreQuery = "with recursive subordinates as ( " +
          "select e.id, e.name, e.manager_id, es.score " +
          "from internship.employee e " +
          "join internship.employee_score es on e.id = es.employee_id " +
          "where e.id = :id " +
          "union " +
          "select e.id, e.name, e.manager_id, es.score " +
          "from internship.employee e " +
          "join internship.employee_score es on e.id = es.employee_id " +
          "join subordinates s on e.manager_id = s.id " +
          ") " +
          "select AVG(score) as overall_average_score " +
          "from subordinates;";


  public Map<String, Object> create(JsonObject request) {
    String name = request.getString("name");
    Integer score = request.getInteger("score");
    Integer employee_id = request.getInteger("employee_id");


    if (name == null || score == null || employee_id == null) {
      throw new ValidationException("BAD_REQUEST");
    }
    EmployeeScore employeeScore = persistItem(name, score, employee_id);


    return Map.of("id", employeeScore.getId());
  }


  @Transactional
  public EmployeeScore persistItem(String name, Integer score, Integer employee_id) {
    EmployeeScore employeeScore = new EmployeeScore();
    employeeScore.setName(name);
    employeeScore.setScore(score);
    employeeScore.setEmployee_id(employee_id);

    employeeScore.persist();

    return employeeScore;
  }

}
