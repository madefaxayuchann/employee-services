package id.kawahedukasi.service;

import id.kawahedukasi.models.Employee;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.Map;

@ApplicationScoped
public class EmployeeServices {

  public Map<String, Object> create(JsonObject request) {
    String name = request.getString("name");
    Integer manager_id = request.getInteger("manager_id");

    if (name == null) {
      throw new ValidationException("BAD_REQUEST");
    }
    Employee employee = persistItem(name, manager_id);

    return Map.of("id", employee.getId());
  }


  @Transactional
  public Employee persistItem(String name, Integer manager_id) {
    Employee employee = new Employee();
    employee.setName(name);
    employee.setManager_id(manager_id);
    employee.persist();


    return employee;
  }

  public String query = "with recursive subordinates as ("
          + "select e.id, e.name, e.manager_id "
          + "from internship.employee e "
          + "where e.id = :id "
          + "union "
          + "select es.id, es.name, e.manager_id "
          + "from internship.employee_score es "
          + "inner join internship.employee e on es.employee_id = e.id "
          + "inner join subordinates s on s.id = e.manager_id "
          + ") "
          + "select * from subordinates;";



}
