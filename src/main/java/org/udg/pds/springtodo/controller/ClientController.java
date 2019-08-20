package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.ClientService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@RequestMapping("/clients")
@RestController
public class ClientController extends BaseController {

    @Autowired
    ClientService clientService;

    @GetMapping("{id}")
    public Client getClient(HttpSession session,
                      @PathVariable("id") Long id) {
        Long userId = getLoggedUser(session);

        return clientService.getClient(userId, id);
    }

    @GetMapping
    @JsonView(Views.Private.class)
    public Collection<Client> listAllClients(HttpSession session) {
        Long userId = getLoggedUser(session);
        return clientService.getClients(userId);
    }

    @GetMapping(path="/jajaja")
    @JsonView(Views.Private.class)
    public Collection<Client> jajaja(HttpSession session) {
        Long userId = getLoggedUser(session);
        return clientService.getClients(userId);
    }

    @GetMapping(path="/llistatClientsDates")
    @JsonView(Views.Private.class)
    public Collection<Client> llistatClientsDates(HttpSession session) {
        Long userId = getLoggedUser(session);
        Collection<Client> llistatClients = clientService.getClients(userId);

        Date data1 = new GregorianCalendar(2019, Calendar.AUGUST, 01).getTime();
        Date data2 = new Date();

        Collection<Client> llistaClients = null;
        for(Client c : llistatClients){
            if (((c.getDataClient().compareTo(data1)==0) || (c.getDataClient().compareTo(data2)==0))
                || ((c.getDataClient().compareTo(data1)>0 && c.getDataClient().compareTo(data2)<0)))
                llistaClients.add(c);
        }
        return llistaClients;
    }


    @PostMapping(path="/afegirClient", consumes = "application/json")
    public IdObject addClient(HttpSession session, @Valid @RequestBody R_Client client) {

        Long userId = getLoggedUser(session);

        return clientService.addClient(client.nomClient, client.preuTotal, client.sexeClient, client.pentinatClient,client.dataClient,userId);
    }

    @GetMapping(path="/{id}/productes")
    public Collection<Producte> getTaskTags(HttpSession session,
                                       @PathVariable("id") Long clientId) {

        Long userId = getLoggedUser(session);
        return clientService.getClientsProducte(userId, clientId);
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
        private Date dataClient;
    }
}
