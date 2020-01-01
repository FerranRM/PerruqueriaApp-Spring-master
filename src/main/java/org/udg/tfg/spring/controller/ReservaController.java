package org.udg.tfg.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.udg.tfg.spring.controller.exceptions.ControllerException;
import org.udg.tfg.spring.entity.IdObject;
import org.udg.tfg.spring.entity.Reserva;
import org.udg.tfg.spring.entity.Views;
import org.udg.tfg.spring.serializer.JsonDateDeserializer;
import org.udg.tfg.spring.service.ReservaService;

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
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date data1,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date data2) {
    Long userId = getLoggedUser(session);

    if (data1 != null)
      return reservaService.crud().reservesEntreDates(data1, data2, userId);
    else {
      return reservaService.getReserves(userId);
    }
  }

  @PostMapping(consumes = "application/json")
  public IdObject addReserva(HttpSession session, @Valid @RequestBody R_Reserva reserva) {

    Long userId = getLoggedUser(session);

    if (reserva.nomReserva == null) {
      throw new ControllerException("No text supplied");
    }
    if (reserva.dataReserva == null) {
      throw new ControllerException("No s'ha proporcionat la dataReserva");
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
    @JsonDeserialize(using= JsonDateDeserializer.class)
    public Date dataReserva;

  }

}
