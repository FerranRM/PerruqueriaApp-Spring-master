package org.udg.tfg.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.tfg.spring.entity.Producte;
import org.udg.tfg.spring.service.ProducteService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping("/productes")
@RestController
public class ProducteController extends BaseController {

  @Autowired
  ProducteService producteService;

  @GetMapping("{id}")
  public Producte getProducte(HttpSession session,
                              @PathVariable("id") Long id) {

    getLoggedUser(session);
    return producteService.getProducte(id);
  }

  @GetMapping
  public Collection<Producte> listAllProductes(HttpSession session) {

    Long userId = getLoggedUser(session);

    return producteService.getProductes();
  }

  @PostMapping(consumes = "application/json")
  public String addProducte(@Valid @RequestBody R_Producte producte, HttpSession session) {

    Long userId = getLoggedUser(session);

    if (producte.descripcioProducte == null) {
      producte.descripcioProducte = " ";
    }

    producteService.addProducte(producte.preuProducte, producte.descripcioProducte);
    return OK_MESSAGE;
  }

  @DeleteMapping(path="/{id}")
  public String deleteProducte(HttpSession session,
                            @PathVariable("id") Long producteId) {

    Long userId = getLoggedUser(session);

    producteService.crud().deleteById(producteId);
    return OK_MESSAGE;
  }

  static class R_Producte {
    @NotNull
    public Integer preuProducte;

    public String descripcioProducte;
  }

}
