package org.udg.tfg.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.tfg.spring.entity.ServeiPrestat;
import org.udg.tfg.spring.service.ServeiPrestatService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping("/serveiPrestat")
@RestController
public class ServeiPrestatController extends BaseController {

  @Autowired
  ServeiPrestatService serveiPrestatService;

  @GetMapping("{id}")
  public ServeiPrestat getServeiPrestat(HttpSession session,
                                      @PathVariable("id") Long id) {

    getLoggedUser(session);
    return serveiPrestatService.getServeiPrestat(id);
  }

  @GetMapping
  public Collection<ServeiPrestat> listAllServeiPrestats(HttpSession session) {

    Long userId = getLoggedUser(session);

    return serveiPrestatService.getServeiPrestats();
  }

  @PostMapping(consumes = "application/json")
  public String addServeiPrestat(@Valid @RequestBody R_ServeiPrestat serveiPrestat, HttpSession session) {

    Long userId = getLoggedUser(session);

    if (serveiPrestat.descripcioServei == null) {
      serveiPrestat.descripcioServei = "";
    }

    serveiPrestatService.addServeiPrestat(serveiPrestat.preuServei, serveiPrestat.descripcioServei);
    return BaseController.OK_MESSAGE;
  }

  @DeleteMapping(path="/{id}")
  public String deleteServeiPrestat(HttpSession session,
                            @PathVariable("id") Long serveiPrestatId) {

    Long userId = getLoggedUser(session);

    serveiPrestatService.crud().deleteById(serveiPrestatId);
    return BaseController.OK_MESSAGE;
  }

  static class R_ServeiPrestat {
    @NotNull
    public Integer preuServei;

    public String descripcioServei;
  }

}
