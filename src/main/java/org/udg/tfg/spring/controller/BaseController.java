package org.udg.tfg.spring.controller;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.udg.tfg.spring.controller.exceptions.ControllerException;

import javax.servlet.http.HttpSession;


public class BaseController {

  static String OK_MESSAGE = "\"ok\"";

  Long getLoggedUser(HttpSession session) {

    if (session == null) {
      throw new ControllerException("No hi ha cap sessió disponible!");
    }

    Long userId = (Long)session.getAttribute("simpleapp_auth_id");
    // Check if the session has the attribute "simpleapp_auth_id"
    if (userId == null)
      throw new ControllerException("Perruquer no esta autenticat!");

    return userId;
  }

  void checkNotLoggedIn(HttpSession session) {
    // Access to the HTTP session

    if (session == null) {
      throw new ControllerException("No hi ha sessions disponibles!");
    }

    Long userId = (Long)session.getAttribute("simpleapp_auth_id");
    // Check if the session has the attribute "simpleapp_auth_id"
    if (userId != null)
      throw new ControllerException("Perruquer is already authenticated!");
  }

  MappingJacksonValue toResponse(Object pojo, Class<?> view) {
    final MappingJacksonValue result = new MappingJacksonValue(pojo);
    result.setSerializationView(view);
    return result;
  }

}
