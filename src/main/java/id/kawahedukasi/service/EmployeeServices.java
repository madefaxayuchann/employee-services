package id.kawahedukasi.service;

import id.kawahedukasi.models.Employee;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EmployeeServices {
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

  @Inject
  EntityManager em;

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

  public List<Map<String, Object>> getSubordinates(Integer id) {

    System.out.println(id + "liken ayu");

    Employee employee = em.find(Employee.class, id);
    if (employee == null) {
      throw new ValidationException("Employee with ID " + id + " not found.");
    }

    Query query = em.createNativeQuery(this.query, Employee.class);
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
