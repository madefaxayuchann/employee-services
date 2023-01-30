package id.kawahedukasi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

@Entity
@Table(name = "employee_score")
public class EmployeeScore extends PanacheEntityBase {
  @SequenceGenerator(
          name = "employee_scoreSequence",
          sequenceName = "employee_score_sequence",
          initialValue = 1, allocationSize = 1
  )

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "employee_scoreSequence", strategy = GenerationType.SEQUENCE)
  private Integer id;

  public Integer getEmployee_id() {
    return employee_id;
  }

  public void setEmployee_id(Integer employee_id) {
    this.employee_id = employee_id;
  }

  @Column(name = "employee_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer employee_id;
  @Column(name = "name")
  private String name;
  @Column(name = "score")
  private Integer score;

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

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }
}
