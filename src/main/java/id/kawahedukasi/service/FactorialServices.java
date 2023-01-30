package id.kawahedukasi.service;

import id.kawahedukasi.models.Factorial;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import java.util.*;

@ApplicationScoped
public class FactorialServices {

   @Transactional
  public Map<String, Object> generateFactorial(JsonObject request) {
    Integer n = request.getInteger("n");

    if (n == null || n < 0) {
      throw new ValidationException("BAD_REQUEST: n must be a non-negative integer.");
    }

    Integer factorial = factorialRecursive(n);
    Factorial factorial1 = new Factorial();
    factorial1.setN(n);
    factorial1.setFactorial(factorial);
    factorial1.persist();

    return Map.of("id", factorial1.getId());
  }

  public Integer factorialRecursive(Integer number) {
    if (number == 0) {
      return 1;
    }
    return number * factorialRecursive(number - 1);
  }

  public List<Map<String, Object>> getAll() {
    List<Factorial> factorial = Factorial.listAll();
    List<Map<String, Object>> result = new ArrayList<>();

    factorial.sort(Comparator.comparing(Factorial::getN));

    for (Factorial item : factorial) {
      Map<String, Object> map = new HashMap<>();
      map.put("n", item.getN());
      map.put("factorial", item.getFactorial());

      result.add(map);
    }

    return result;
  }
}
