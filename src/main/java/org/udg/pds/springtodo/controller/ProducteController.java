package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.service.ProducteService;

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

    @GetMapping(path="/obtenirProductes")
    public Collection<Producte> listAllProductes(HttpSession session) {
        Long userId = getLoggedUser(session);
        return producteService.getProductes(userId);
    }

    @PostMapping(path="/afegirProducte", consumes = "application/json")
    public String addProducte(@Valid @RequestBody R_Producte producte, HttpSession session) {

        //Long userId = getLoggedUser(session);

        producteService.addProducte(producte.descProducte, producte.preuProducte);
        return BaseController.OK_MESSAGE;
    }

    static class R_Producte {

        @NotNull
        private String descProducte;

        @NotNull
        private Integer preuProducte;
    }
}
