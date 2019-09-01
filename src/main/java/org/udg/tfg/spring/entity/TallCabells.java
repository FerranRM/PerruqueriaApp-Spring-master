package org.udg.tfg.spring.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity(name = "tallCabells")
public class TallCabells implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will.
   **/
  private static final long serialVersionUID = 1L;

  public TallCabells() {
  }

  public TallCabells(Integer preuTall, String descripcioTall) {
    this.preuTall = preuTall;
    this.descripcioTall = descripcioTall;
  }

  // This tells JAXB that this field can be used as ID
  // Since XmlID can only be used on Strings, we need to use LongAdapter to transform Long <-> String
  @Id
  // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Integer preuTall;

  @NotNull
  private String descripcioTall;

  public Integer getPreuTall() {
    return preuTall;
  }

  public void setPreuTall(Integer preuTall) {
    this.preuTall = preuTall;
  }

  public Long getId() {
    return id;
  }

  public String getDescripcioTall() {
        return descripcioTall;
    }
}
