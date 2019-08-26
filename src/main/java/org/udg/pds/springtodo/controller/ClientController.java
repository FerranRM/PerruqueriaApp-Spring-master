package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.service.ClientService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequestMapping(path="/clients")
@RestController
public class ClientController extends BaseController {

  @Autowired
  ClientService clientService;

  @GetMapping(path="/{id}")
  public Client getClient(HttpSession session,
                      @PathVariable("id") Long id) {
    Long userId = getLoggedUser(session);

    return clientService.getClient(userId, id);
  }

  @GetMapping
  @JsonView(Views.Private.class)
  public Collection<Client> listAllClients(HttpSession session,
                                       @RequestParam(value = "from", required = false) Date from) {
    Long userId = getLoggedUser(session);

    return clientService.getClients(userId);
  }

  @PostMapping(consumes = "application/json")
  public IdObject addClient(HttpSession session, @Valid @RequestBody R_Client client) {

    Long userId = getLoggedUser(session);

    if (client.nomClient == null) {
      throw new ControllerException("No nomClient supplied");
    }
    if (client.dataClient == null) {
      throw new ControllerException("No dataClient supplied");
    }
    if (client.pentinatClient == null) {
      throw new ControllerException("No limit date supplied");
    }
    if (client.preuTotal == null) {
      throw new ControllerException("No preuTotal supplied");
    }
    if (client.sexeClient == null) {
      throw new ControllerException("No sexeClient supplied");
    }

    return clientService.addClient(client.nomClient, userId, client.dataClient, client.pentinatClient, client.preuTotal, client.sexeClient);
  }

  @DeleteMapping(path="/{id}")
  public String deleteClient(HttpSession session,
                             @PathVariable("id") Long clientId) {
    getLoggedUser(session);
    clientService.crud().deleteById(clientId);
    return BaseController.OK_MESSAGE;
  }

  @PostMapping(path="/{id}/productes")
  public String addProductes(@RequestBody Collection<Long> productes, HttpSession session,
                        @PathVariable("id") Long clientId) {

    Long userId = getLoggedUser(session);
    clientService.addProductesToClient(userId, clientId, productes);
    return BaseController.OK_MESSAGE;
  }

  @GetMapping(path="/{id}/productes")
  public Collection<Producte> getClientProductes(HttpSession session,
                                          @PathVariable("id") Long clientId) {

    Long userId = getLoggedUser(session);
    return clientService.getClientProductes(userId, clientId);
  }

  @GetMapping(path="/{data1,data2}")
  public List<Client> listClientsDates(HttpSession session,
                                       @RequestParam(value = "data1", required = true) Date data1,
                                       @RequestParam(value = "data2", required = true) Date data2) {
    return clientService.crud().clientsEntreDates(data1,data2);
  }

  static class R_Client {

    @NotNull
    public String nomClient;

    @NotNull
    @JsonDeserialize(using=JsonDateDeserializer.class)
    public Date dataClient;

    @NotNull
    public Integer pentinatClient;

    @NotNull
    public Integer preuTotal;

    @NotNull
    public Boolean sexeClient;
  }

}
