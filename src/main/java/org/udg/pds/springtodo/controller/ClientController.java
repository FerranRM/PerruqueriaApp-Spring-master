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
                             @PathVariable("id") Long taskId) {
    getLoggedUser(session);
    clientService.crud().deleteById(taskId);
    return BaseController.OK_MESSAGE;
  }

  @PostMapping(path="/{id}/productes")
  public String addProductes(@RequestBody Collection<Long> productes, HttpSession session,
                        @PathVariable("id") Long taskId) {

    Long userId = getLoggedUser(session);
    clientService.addProductesToClient(userId, taskId, productes);
    return BaseController.OK_MESSAGE;
  }

  @GetMapping(path="/{id}/productes")
  public Collection<Producte> getTaskProductes(HttpSession session,
                                          @PathVariable("id") Long taskId) {

    Long userId = getLoggedUser(session);
    return clientService.getClientsProductes(userId, taskId);
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
