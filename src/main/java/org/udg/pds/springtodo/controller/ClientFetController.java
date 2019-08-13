package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.ClientFet;
import org.udg.pds.springtodo.entity.Perruquer;
import org.udg.pds.springtodo.entity.Data;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.service.ClientFetService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping("/clientsFets")
@RestController
public class ClientFetController extends BaseController {

    @Autowired
    ClientFetService clientFetService;

    @GetMapping("{id}")
    public ClientFet getClientFet(HttpSession session,
                      @PathVariable("id") Long id) {

        getLoggedUser(session);
        return clientFetService.getClientFet(id);
    }

    @GetMapping(path="/obtenirClientsFets")
    public Collection<ClientFet> listAllClientsFets(HttpSession session) {
        Long userId = getLoggedUser(session);
        return clientFetService.getClientsFets(userId);
    }

    @PostMapping(path="/afegirClientFet", consumes = "application/json")
    public String addClientFet(@Valid @RequestBody R_ClientFet clientFet, HttpSession session) {

        //Long userId = getLoggedUser(session);

        clientFetService.addClientFet(clientFet.data, clientFet.client, clientFet.perruquer);
        return BaseController.OK_MESSAGE;
    }

    static class R_ClientFet {
        @NotNull
        private Data data;
        @NotNull
        private Client client;
        @NotNull
        private Perruquer perruquer;

    }
}
