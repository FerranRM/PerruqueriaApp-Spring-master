package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity(name = "perruquers")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "nomPerruquer"}))
public class Perruquer implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will. *
   */
  private static final long serialVersionUID = 1L;

  public Perruquer() {
  }

  public Perruquer(String nom, String email, String contrasenya) {
    this.nomPerruquer = nom;
    this.email = email;
    this.contrasenya = contrasenya;
    this.tasks = new ArrayList<>();
    this.clients = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id_perruquer;

  @NotNull
  private String nomPerruquer;

  @NotNull
  private String email;

  @NotNull
  private String contrasenya;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "perruquer")
  private Collection<Task> tasks;

  /*@OneToMany(mappedBy = "perruquer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Client> clients;*/

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "perruquer")
  private Collection<Client> clients;

  @JsonView(Views.Private.class)
  public Long getId() {
    return id_perruquer;
  }

  public void setId(Long id) {
    this.id_perruquer = id;
  }

  @JsonView(Views.Private.class)
  public String getEmail() {
    return email;
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
  public Collection<Task> getTasks() {
    // Since tasks is collection controlled by JPA, it has LAZY loading by default. That means
    // that you have to query the object (calling size(), for example) to get the list initialized
    // More: http://www.javabeat.net/jpa-lazy-eager-loading/
    tasks.size();
    return tasks;
  }

  public void addTask(Task task) {
    tasks.add(task);
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

  /*public Set<Client> getClients() { return clients; }

  public void setClients(Set<Client> clients) {
    this.clients = clients;
  }*/



}
