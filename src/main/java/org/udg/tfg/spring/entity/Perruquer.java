package org.udg.tfg.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "perruquers")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nomUsuari", "nomPerruquer"}))
public class Perruquer implements Serializable {

  private static final long serialVersionUID = 1L;

  public Perruquer() {
  }

  public Perruquer(String nomPerruquer, String nomUsuari, String contrasenya) {
    this.nomPerruquer = nomPerruquer;
    this.nomUsuari = nomUsuari;
    this.contrasenya = contrasenya;

    this.clients = new ArrayList<>();
    this.reserves = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_perruquer;

  @NotNull
  private String nomPerruquer;

  @NotNull
  private String nomUsuari;

  @NotNull
  private String contrasenya;


  @OneToMany(cascade = CascadeType.ALL, mappedBy = "perruquer")
  private Collection<Client> clients;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "perruquer")
  private Collection<Reserva> reserves;




  @JsonView(Views.Private.class)
  public Long getId() {
    return id_perruquer;
  }

  public void setId(Long id) {
    this.id_perruquer = id;
  }

  @JsonView(Views.Private.class)
  public String getNomUsuari() {
    return nomUsuari;
  }

  @JsonView(Views.Public.class)
  public String getNomPerruquer() {
    return nomPerruquer;
  }

  @JsonIgnore
  public String getContrasenya() {
    return contrasenya;
  }



  @JsonView(Views.Complete.class)
  public Collection<Client> getClients() {
    // Since tasks is collection controlled by JPA, it has LAZY loading by default. That means
    // that you have to query the object (calling size(), for example) to get the list initialized
    // More: http://www.javabeat.net/jpa-lazy-eager-loading/
    clients.size();
    return clients;
  }

  public void addClient(Client client) {
    clients.add(client);
  }





  @JsonView(Views.Complete.class)
  public Collection<Reserva> getReserves() {
    reserves.size();
    return reserves;
  }

  public void addReserva(Reserva reserva) {
    reserves.add(reserva);
  }

}
