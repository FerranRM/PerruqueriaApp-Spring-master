package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Producte implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    public Producte() {
    }

    public Producte(String descProducte, Integer preuProducte) {
        this.descProducte = descProducte;
        this.preuProducte = preuProducte;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producte_id;

    private String descProducte;

    @NotNull
    private Integer preuProducte;

    /*@ManyToMany(mappedBy = "productes")
    private Set<Client> clients;*/



    //DECLARACIÓ DELS MÉTODES

    public Long getId() {
        return producte_id;
    }

    public void setId(Long id) {
        this.producte_id = id;
    }

    public String getDescProducte() {
        return descProducte;
    }

    public void setDescProducte(String desc) {
        this.descProducte = desc;
    }

    public Integer getPreuProducte() {
        return preuProducte;
    }

    public void setPreuProducte(Integer preu) {
        this.preuProducte = preu;
    }


    /*@JsonView(Views.Complete.class)
    public Set<Client> getClients() { return clients; }

    public void setClients(Set<Client> clients) { this.clients = clients; }*/


}
