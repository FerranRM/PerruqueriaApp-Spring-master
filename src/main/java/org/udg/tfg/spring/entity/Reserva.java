package org.udg.tfg.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.tfg.spring.serializer.JsonDateDeserializer;
import org.udg.tfg.spring.serializer.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "reserves")
// This tells JAXB that it has to ignore getters and setters and only use fields for JSON marshaling/unmarshaling
public class Reserva implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will.
   **/
  private static final long serialVersionUID = 1L;

  public Reserva() {
  }

  public Reserva(Date dataReserva, String nomReserva, Long userId) {
    this.dataReserva = dataReserva;
    this.nomReserva = nomReserva;
    this.perruquerId = userId;
  }

  // This tells JAXB that this field can be used as ID
  // Since XmlID can only be used on Strings, we need to use LongAdapter to transform Long <-> String
  @Id
  // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date dataReserva;

  private String nomReserva;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "fk_perruquer")
  private Perruquer perruquer;

  @Column(name = "fk_perruquer", insertable = false, updatable = false)
  private Long perruquerId;




  @JsonView(Views.Private.class)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @JsonIgnore
  public Perruquer getPerruquer() {
    return perruquer;
  }

  public void setPerruquer(Perruquer perruquer) {
    this.perruquer = perruquer;
  }


  @JsonView(Views.Private.class)
  public String getNomReserva() {
    return nomReserva;
  }

  @JsonView(Views.Complete.class)
  public long getPerruquerId() {
    return perruquerId;
  }

  @JsonView(Views.Private.class)
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(as= JsonDateDeserializer.class)
  public Date getDataReserva() {
    return dataReserva;
  }

}
