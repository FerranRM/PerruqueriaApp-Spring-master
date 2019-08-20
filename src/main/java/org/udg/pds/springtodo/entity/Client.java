package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
public class Client implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    public Client() {
    }

    public Client(String nom, Integer preu, Boolean sexe, Integer pentinatClient, Date data) {
        this.nomClient = nom;
        this.preuTotal = preu;
        this.sexeClient = sexe;
        this.pentinatClient = pentinatClient;
        this.dataClient = data;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_client;

    @NotNull
    private String nomClient;

    /*@NotNull
    private String cognom1;
    @NotNull
    private String cognom2;*/

    @NotNull
    private Integer preuTotal;
    @NotNull
    private Boolean sexeClient;

    private Integer pentinatClient;

    @NotNull
    private Date dataClient;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Collection<Producte> productes;*/

    @ManyToMany(cascade = CascadeType.ALL)
    private Collection<Producte> productes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perruquer_id")
    private Perruquer perruquer;

    @Column(name = "perruquer_id", insertable = false, updatable = false)
    private Long perruquerId;



    //DECLARACIÓ DELS MÉTODES

    @JsonView(Views.Private.class)
    public Long getIdClient() {
        return id_client;
    }

    public void setId(Long id) {
        this.id_client = id;
    }

    @JsonView(Views.Private.class)
    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    @JsonView(Views.Private.class)
    public Integer getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(Integer preuTotal) {
        this.preuTotal = preuTotal;
    }

    @JsonView(Views.Private.class)
    public Boolean getSexeClient() {
        return sexeClient;
    }

    public void setSexeClient(Boolean sexeClient) {
        this.sexeClient = sexeClient;
    }

    @JsonView(Views.Private.class)
    public Integer getPentinatClient() {
        return pentinatClient;
    }

    public void setPentinatClient(Integer pentinatClient) {
        this.pentinatClient = pentinatClient;
    }

    @JsonView(Views.Private.class)
    public Date getDataClient() {return dataClient;}

    public void setDataClient(Date data) {this.dataClient= data;}

    public void addProducte(Producte producte) {
        productes.add(producte);
    }

    @JsonView(Views.Complete.class)
    public Collection<Producte> getProductes() {
        productes.size();
        return productes;
    }



    @JsonIgnore
    public Perruquer getPerruquer() {
        return perruquer;
    }

    public void setPerruquer(Perruquer perruquer) {
        this.perruquer = perruquer;
    }

    @JsonView(Views.Complete.class)
    public long getPerruquerId() {
        return perruquerId;
    }




}
