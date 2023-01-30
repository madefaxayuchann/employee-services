package id.kawahedukasi.service;

import id.kawahedukasi.models.Employee;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.List;
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



}
