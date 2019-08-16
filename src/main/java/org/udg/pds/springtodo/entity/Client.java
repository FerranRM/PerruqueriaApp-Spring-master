package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
        this.data = data;
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
    private Date data;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
    private Collection<Producte> productes;*/

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "productes_comprats",
            joinColumns = @JoinColumn(name="client_id"),
            inverseJoinColumns = @JoinColumn(name = "producte_id"))
    private Set<Producte> productes;

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

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public Integer getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(Integer preuTotal) {
        this.preuTotal = preuTotal;
    }

    public Boolean getSexeClient() {
        return sexeClient;
    }

    public void setSexeClient(Boolean sexeClient) {
        this.sexeClient = sexeClient;
    }

    public Integer getPentinatClient() {
        return pentinatClient;
    }

    public void setPentinatClient(Integer pentinatClient) {
        this.pentinatClient = pentinatClient;
    }

    public Date getData() {return data;}

    public void setData(Date data) {this.data= data;}

    public Set<Producte> getProductes() { return productes; }

    public void setProductes(Set<Producte> productes) { this.productes = productes; }



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
