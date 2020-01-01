package org.udg.tfg.spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = "serveiPrestat")
public class ServeiPrestat implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will.
   **/
  private static final long serialVersionUID = 1L;

  public ServeiPrestat() {
  }

  public ServeiPrestat(Integer preuServei, String descripcioServei) {
    this.preuServei = preuServei;
    this.descripcioServei = descripcioServei;
  }

  // This tells JAXB that this field can be used as ID
  // Since XmlID can only be used on Strings, we need to use LongAdapter to transform Long <-> String
  @Id
  // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Integer preuServei;

  @NotNull
  private String descripcioServei;

  public Integer getPreuServei() {
    return preuServei;
  }

  public void setPreuServei(Integer preuServei) {
    this.preuServei = preuServei;
  }

  public Long getId() {
    return id;
  }

  public String getDescripcioServei() {
        return descripcioServei;
    }
}
