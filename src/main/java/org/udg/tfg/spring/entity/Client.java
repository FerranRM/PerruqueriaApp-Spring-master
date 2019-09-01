package org.udg.tfg.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.tfg.spring.serializer.JsonDateDeserializer;
import org.udg.tfg.spring.serializer.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity(name = "clients")
// This tells JAXB that it has to ignore getters and setters and only use fields for JSON marshaling/unmarshaling
public class Client implements Serializable {

  private static final long serialVersionUID = 1L;

  public Client() {
  }

  public Client(Date dataClient, Integer pentinatClient, Boolean sexeClient, String nomClient, Integer preuTotal) {
    this.dataClient = dataClient;
    this.pentinatClient = pentinatClient;
    this.sexeClient = sexeClient;
    this.nomClient = nomClient;
    this.preuTotal = preuTotal;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date dataClient;

  private Integer pentinatClient;

  private Boolean sexeClient;

  private String nomClient;

  private Integer preuTotal;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "fk_perruquer")
  private Perruquer perruquer;

  @Column(name = "fk_perruquer", insertable = false, updatable = false)
  private Long perruquerId;

  @ManyToMany(cascade = CascadeType.ALL)
  private Collection<Producte> productes = new ArrayList<>();

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

  public void addProducte(Producte producte) {
    productes.add(producte);
  }

  @JsonView(Views.Complete.class)
  public Collection<Producte> getProductes() {
    productes.size();
    return productes;
  }

  @JsonView(Views.Private.class)
  public Boolean getSexeClient() {
    return sexeClient;
  }

  @JsonView(Views.Private.class)
  public String getNomClient() {
    return nomClient;
  }

  @JsonView(Views.Private.class)
  public Integer getPreuTotal() {
    return preuTotal;
  }

  @JsonView(Views.Private.class)
  public Integer getPentinatClient() {
    return pentinatClient;
  }

  @JsonView(Views.Complete.class)
  public long getPerruquerId() {
    return perruquerId;
  }

  @JsonView(Views.Private.class)
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(as= JsonDateDeserializer.class)
  public Date getDataClient() {
    return dataClient;
  }

}
