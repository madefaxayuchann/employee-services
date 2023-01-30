package id.kawahedukasi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee extends PanacheEntityBase {
  @Id
  @SequenceGenerator(
          name = "employeeSequence",
          sequenceName = "employee_sequence",
          initialValue = 1,
          allocationSize = 1
  )
  @GeneratedValue(generator = "employeeSequence", strategy = GenerationType.SEQUENCE)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name")
  private String name;
  @Column(name = "manager_id")
  private Integer manager_id;

  public Employee() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getManager_id() {
    return manager_id;
  }

  public void setManager_id(Integer manager_id) {
    this.manager_id = manager_id;
  }
}
