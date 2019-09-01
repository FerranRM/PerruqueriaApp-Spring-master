package org.udg.tfg.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.tfg.spring.entity.TallCabells;
import org.udg.tfg.spring.service.TallCabellsService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping("/tallCabells")
@RestController
public class TallCabellsController extends BaseController {

  @Autowired
  TallCabellsService tallCabellsService;

  @GetMapping("{id}")
  public TallCabells getTallCabells(HttpSession session,
                                    @PathVariable("id") Long id) {

    getLoggedUser(session);
    return tallCabellsService.getTallCabells(id);
  }

  @GetMapping
  public Collection<TallCabells> listAllTallsCabells(HttpSession session) {

    Long userId = getLoggedUser(session);

    return tallCabellsService.getTallsCabells();
  }

  @PostMapping(consumes = "application/json")
  public String addTallCabells(@Valid @RequestBody R_TallCabells tallCabells, HttpSession session) {

    Long userId = getLoggedUser(session);

    if (tallCabells.descripcioTall == null) {
      tallCabells.descripcioTall = "";
    }

    tallCabellsService.addTallCabells(tallCabells.preuTall, tallCabells.descripcioTall);
    return BaseController.OK_MESSAGE;
  }

  @DeleteMapping(path="/{id}")
  public String deleteTallCabells(HttpSession session,
                            @PathVariable("id") Long tallCabellsId) {

    Long userId = getLoggedUser(session);

    tallCabellsService.crud().deleteById(tallCabellsId);
    return BaseController.OK_MESSAGE;
  }

  static class R_TallCabells {
    @NotNull
    public Integer preuTall;

    public String descripcioTall;
  }

}
