package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.pds.springtodo.serializer.JsonProducteSerializer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonSerialize(using = JsonProducteSerializer.class)
@Entity
// This tells JAXB that it has to ignore getters and setters and only use fields for JSON marshaling/unmarshaling
public class Producte implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will.
   **/
  private static final long serialVersionUID = 1L;

  public Producte() {
  }

  public Producte(Integer preuProducte, String descripcioProducte) {
    this.preuProducte = preuProducte;
    this.descripcioProducte = descripcioProducte;
  }

  // This tells JAXB that this field can be used as ID
  // Since XmlID can only be used on Strings, we need to use LongAdapter to transform Long <-> String
  @Id
  // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private Integer preuProducte;

  @NotNull
  private String descripcioProducte;

  public Integer getPreuProducte() {
    return preuProducte;
  }

  public void setPreuProducte(Integer preuProducte) {
    this.preuProducte = preuProducte;
  }

  public Long getId() {
    return id;
  }

  public String getDescripcioProducte() {
        return descripcioProducte;
    }
}
