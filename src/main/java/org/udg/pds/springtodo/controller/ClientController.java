package org.udg.pds.springtodo.controller;

import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.service.ClientService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

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

    @GetMapping(path="/obtenirClients")
    public Collection<Client> listAllClients(HttpSession session) {
        Long userId = getLoggedUser(session);
        return clientService.getClients(userId);
    }

    @PostMapping(path="/afegirClient", consumes = "application/json")
    public String addClient(HttpSession session, @Valid @RequestBody R_Client client) {

        Long userId = getLoggedUser(session);

        clientService.addClient(client.nomClient, client.preuTotal, client.sexeClient, client.pentinatClient,client.data,userId);
        return BaseController.OK_MESSAGE;
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
