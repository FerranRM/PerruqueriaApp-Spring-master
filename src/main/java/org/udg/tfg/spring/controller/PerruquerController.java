package org.udg.tfg.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.tfg.spring.controller.exceptions.ControllerException;
import org.udg.tfg.spring.entity.Perruquer;
import org.udg.tfg.spring.entity.Views;
import org.udg.tfg.spring.service.PerruquerService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@RequestMapping(path="/perruquers")
@RestController
public class PerruquerController extends BaseController {

    @Autowired
    PerruquerService perruquerService;

    @PostMapping(path="/login")
    @JsonView(Views.Private.class)
    public Perruquer login(HttpSession session, @Valid @RequestBody LoginPerruquer perruquer) {

        checkNotLoggedIn(session);

        Perruquer p = perruquerService.matchPassword(perruquer.nomPerruquer, perruquer.contrasenya);
        session.setAttribute("simpleapp_auth_id", p.getId());
        return p;
    }

    @PostMapping(path="/logout")
    @JsonView(Views.Private.class)
    public String logout(HttpSession session) {

        getLoggedUser(session);

        session.removeAttribute("simpleapp_auth_id");
        return OK_MESSAGE;
    }

    @GetMapping(path="/{id}")
    @JsonView(Views.Public.class)
    public Perruquer getPublicUser(HttpSession session, @PathVariable("id") Long perruquerId) {
        getLoggedUser(session);
        return perruquerService.getPerruquer(perruquerId);
    }

    @DeleteMapping(path="/{id}")
    public String deletePerruquer(HttpSession session, @PathVariable("id") Long perruquerId) {

        Long loggedUserId = getLoggedUser(session);

        if (!loggedUserId.equals(perruquerId))
            throw new ControllerException("No pots eliminar altres usuaris!");

        perruquerService.crud().deleteById(perruquerId);
        session.removeAttribute("simpleapp_auth_id");

        return OK_MESSAGE;
    }


    @PostMapping(path="/register", consumes = "application/json")
    public String register(HttpSession session, @Valid  @RequestBody RegistrarPerruquer ru) {

        checkNotLoggedIn(session);
        perruquerService.register(ru.nomPerruquer, ru.nomUsuari, ru.contrasenya);
        return OK_MESSAGE;

    }

    @GetMapping(path="/me")
    @JsonView(Views.Complete.class)
    public Perruquer getIdPerruquer(HttpSession session) {

        Long loggedUserId = getLoggedUser(session);

        return perruquerService.getIdPerruquer(loggedUserId);
    }

    @GetMapping(path="/check")
    public String checkLoggedIn(HttpSession session) {

        getLoggedUser(session);

        return OK_MESSAGE;
    }

    @GetMapping
    public Collection<Perruquer> listAllPerruquers(HttpSession session) {

        Long userId = getLoggedUser(session);

        return perruquerService.getPerruquers();
    }


    static class LoginPerruquer {
        @NotNull
        public String nomPerruquer;
        @NotNull
        public String contrasenya;
    }

    static class RegistrarPerruquer {
        @NotNull
        public String nomPerruquer;
        @NotNull
        public String nomUsuari;
        @NotNull
        public String contrasenya;
    }

    static class ID {
        public Long id;

        public ID(Long id) {
            this.id = id;
        }
    }

}
