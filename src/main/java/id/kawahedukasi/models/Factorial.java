package id.kawahedukasi.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "math")
public class Factorial extends PanacheEntityBase {

  @Id
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @GeneratedValue(generator = "uuid")
  @Column(name = "id")
  public String id;

  @Column(name = "n")
  private Integer n;

  @Column(name = "factorial")
  private Integer factorial;

  public Factorial() {

  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getN() {
    return n;
  }

  public void setN(Integer n) {
    this.n = n;
  }

  public Integer getFactorial() {
    return factorial;
  }

  public void setFactorial(Integer factorial) {
    this.factorial = factorial;
  }
}
