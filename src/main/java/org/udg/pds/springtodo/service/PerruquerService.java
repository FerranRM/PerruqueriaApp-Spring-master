package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Perruquer;
import org.udg.pds.springtodo.entity.Task;
import org.udg.pds.springtodo.repository.PerruquerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PerruquerService {

  @Autowired
  private PerruquerRepository perruquerRepository;

  public PerruquerRepository crud() {
    return perruquerRepository;
  }

  public Perruquer matchPassword(String nom, String contrasenya) {

    List<Perruquer> uc = perruquerRepository.trobarPerNomPerruquer(nom);

    if (uc.size() == 0) throw new ServiceException("No existeix aquest Perruquer");

    Perruquer u = uc.get(0);

    if (u.getContrasenya().equals(contrasenya))
      return u;
    else
      throw new ServiceException("Contrasenya incorrecte");
  }

  public Perruquer register(String nom, String email, String contrasenya) {

    List<Perruquer> uEmail = perruquerRepository.trobarPerEmail(email);
    if (uEmail.size() > 0)
      throw new ServiceException("Email ja existent");


    List<Perruquer> pPerruquerNom = perruquerRepository.trobarPerNomPerruquer(nom);
    if (pPerruquerNom.size() > 0)
      throw new ServiceException("Nom d'usuari ja existent");

    Perruquer np = new Perruquer(nom, email, contrasenya);
    perruquerRepository.save(np);
    return np;
  }

  public Perruquer getPerruquer(Long id) {
    Optional<Perruquer> uo = perruquerRepository.findById(id);
    if (uo.isPresent())
      return uo.get();
    else
      throw new ServiceException(String.format("Perruquer amb id = % no existeix", id));
  }

  public Perruquer getIdPerruquer(long id) {
    Perruquer u = this.getPerruquer(id);
    for (Task t : u.getTasks())
      t.getTags();
    return u;
  }
}
