package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Reserva;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.service.ReservaService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@RequestMapping(path="/reserves")
@RestController
public class ReservaController extends BaseController {

  @Autowired
  ReservaService reservaService;

  @GetMapping(path="/{id}")
  public Reserva getReserva(HttpSession session,
                          @PathVariable("id") Long id) {
    Long userId = getLoggedUser(session);

    return reservaService.getReserva(userId, id);
  }

  @GetMapping
  @JsonView(Views.Private.class)
  public Collection<Reserva> listAllReserves(HttpSession session,
                                           @RequestParam(value = "from", required = false) Date from) {
    Long userId = getLoggedUser(session);

    return reservaService.getReserves(userId);
  }

  @PostMapping(consumes = "application/json")
  public IdObject addReserva(HttpSession session, @Valid @RequestBody R_Reserva reserva) {

    Long userId = getLoggedUser(session);

    if (reserva.nomReserva == null) {
      throw new ControllerException("No text supplied");
    }
    if (reserva.dataReserva == null) {
      throw new ControllerException("No creation date supplied");
    }

    return reservaService.addReserva(reserva.nomReserva, userId, reserva.dataReserva);
  }

  @DeleteMapping(path="/{id}")
  public String deleteReserva(HttpSession session,
                             @PathVariable("id") Long reservaId) {
    getLoggedUser(session);
    reservaService.crud().deleteById(reservaId);
    return BaseController.OK_MESSAGE;
  }


  static class R_Reserva {

    @NotNull
    public String nomReserva;

    @NotNull
    @JsonDeserialize(using=JsonDateDeserializer.class)
    public Date dataReserva;

  }

}
