package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ClientService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequestMapping("/clients")
@RestController
public class ClientController extends BaseController {

    @Autowired
    ClientService clientService;

    @GetMapping("{id}")
    public Client getClient(HttpSession session,
                      @PathVariable("id") Long id) {

        getLoggedUser(session);
        return clientService.getClient(id);
    }

    @GetMapping
    @JsonView(Views.Private.class)
    public Collection<Client> listAllClients(HttpSession session) {
        Long userId = getLoggedUser(session);
        return clientService.getClients(userId);
    }

    @GetMapping(path="/obtenirLlistaPreus", consumes = "application/json")
    public List<Integer> listAllPreus(HttpSession session) {
        Long userId = getLoggedUser(session);
        Collection<Client> llistatClients = clientService.getClients(userId);

        List<Integer> llistaPreus = null;
        for(Client c : llistatClients){
            llistaPreus.add(c.getPreuTotal());
        }
        return llistaPreus;
    }



    @PostMapping(path="/afegirClient", consumes = "application/json")
    public IdObject addClient(HttpSession session, @Valid @RequestBody R_Client client) {

        Long userId = getLoggedUser(session);

        return clientService.addClient(client.nomClient, client.preuTotal, client.sexeClient, client.pentinatClient,client.data,userId);
    }

    static class R_Client {
        @NotNull
        private String nomClient;
        @NotNull
        private Integer preuTotal;
        @NotNull
        private Boolean sexeClient;

        private Integer pentinatClient;
        @NotNull
        private Date data;
    }
}
