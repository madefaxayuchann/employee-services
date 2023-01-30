package id.kawahedukasi.service;

import id.kawahedukasi.models.EmployeeScore;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Map;

@ApplicationScoped
public class EmployeeScoreServices {
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
