package org.udg.tfg.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.tfg.spring.controller.exceptions.ServiceException;
import org.udg.tfg.spring.entity.TallCabells;
import org.udg.tfg.spring.repository.TallCabellsRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TallCabellsService {

  @Autowired
  TallCabellsRepository tallCabellsRepository;

  public TallCabellsRepository crud() {
    return tallCabellsRepository;
  }

  public TallCabells getTallCabells(Long id) {
    Optional<TallCabells> ot = tallCabellsRepository.findById(id);
    if (!ot.isPresent())
      throw new ServiceException("Tall cabell no existeix");
    else
      return ot.get();
  }

  public Collection<TallCabells> getTallsCabells() {
    Collection<TallCabells> r = new ArrayList<>();

    return StreamSupport.stream(tallCabellsRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  @Transactional
  public TallCabells addTallCabells(Integer preu, String descripcio) {
    try {
      TallCabells tallCabells = new TallCabells(preu, descripcio);

      tallCabellsRepository.save(tallCabells);
      return tallCabells;
    } catch (Exception ex) {
      // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
      // We catch the normal exception and then transform it in a EJBException
      throw new ServiceException(ex.getMessage());
    }
  }
}
