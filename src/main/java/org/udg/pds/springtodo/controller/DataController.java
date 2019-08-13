package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Data;
import org.udg.pds.springtodo.service.DataService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@RequestMapping("/dates")
@RestController
public class DataController extends BaseController {

    @Autowired
    DataService dataService;

    @GetMapping("{id}")
    public Data getData(HttpSession session,
                      @PathVariable("id") Long id) {

        getLoggedUser(session);
        return dataService.getData(id);
    }

    @GetMapping(path="/obtenirDates")
    public Collection<Data> listAllDates(HttpSession session) {
        Long userId = getLoggedUser(session);
        return dataService.getDates(userId);
    }

    @PostMapping(path="/afegirData", consumes = "application/json")
    public String addData(@Valid @RequestBody R_Data data, HttpSession session) {

        //Long userId = getLoggedUser(session);

        dataService.addData(data.data, data.hora);
        return BaseController.OK_MESSAGE;
    }

    static class R_Data {
        @NotNull
        private Date data;
        @NotNull
        private int hora;
    }
}
