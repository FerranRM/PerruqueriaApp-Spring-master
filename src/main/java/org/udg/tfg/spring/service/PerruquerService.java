package org.udg.tfg.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.tfg.spring.controller.exceptions.ServiceException;
import org.udg.tfg.spring.entity.Perruquer;
import org.udg.tfg.spring.entity.Client;
import org.udg.tfg.spring.repository.PerruquerRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

  public Perruquer register(String nomPerruquer, String nomUsuari, String contrasenya) {

    List<Perruquer> uNomUsuari = perruquerRepository.trobarPerNomUsuari(nomUsuari);
    if (uNomUsuari.size() > 0)
      throw new ServiceException("Nom d'usuari ja existent");


    List<Perruquer> pPerruquerNom = perruquerRepository.trobarPerNomPerruquer(nomPerruquer);
    if (pPerruquerNom.size() > 0)
      throw new ServiceException("Nom del perruquer ja existent");

    Perruquer np = new Perruquer(nomPerruquer, nomUsuari, contrasenya);
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

    for (Client c : u.getClients())
      c.getProductes();

    u.getReserves();

    return u;
  }

  public Collection<Perruquer> getPerruquers() {
    Collection<Perruquer> r = new ArrayList<>();

    return StreamSupport.stream(perruquerRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }
}
