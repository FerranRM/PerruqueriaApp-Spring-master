package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Producte implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    public Producte() {
    }

    public Producte(Long id, String descProducte, Integer preuProducte) {
        this.descProducte = descProducte;
        this.producte_id = id;
        this.preuProducte = preuProducte;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long producte_id;

    private String descProducte;

    @NotNull
    private Integer preuProducte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_client")
    private Client client;

    @Column(name = "fk_client", insertable = false, updatable = false)
    private Long clientId;



    //DECLARACIÓ DELS MÉTODES

    @JsonView(Views.Private.class)
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


    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @JsonView(Views.Complete.class)
    public long getClientId() {
        return clientId;
    }


}
