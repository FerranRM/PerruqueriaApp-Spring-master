package org.udg.pds.springtodo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class ClientFet implements Serializable {
    /**
     * Default value included to remove warning. Remove or modify at will.
     **/
    private static final long serialVersionUID = 1L;

    public ClientFet() {
    }

    public ClientFet(Data data, Client client, Perruquer perruquer) {
        this.data = data;
        this.client = client;
        this.perruquer = perruquer;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Data data;

    @NotNull
    private Client client;

    @NotNull
    private Perruquer perruquer;




    //DECLARACIÓ DELS MÉTODES

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Perruquer getPerruquer() {
        return perruquer;
    }

    public void setPerruquer(Perruquer perruquer) {
        this.perruquer = perruquer;
    }

}
