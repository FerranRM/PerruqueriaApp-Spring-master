package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.repository.ProducteRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProducteService {

  @Autowired
  ProducteRepository producteRepository;

  public ProducteRepository crud() {
    return producteRepository;
  }

  public Producte getProducte(Long id) {
    Optional<Producte> ot = producteRepository.findById(id);
    if (!ot.isPresent())
      throw new ServiceException("Producte no existeix");
    else
      return ot.get();
  }

  public Producte addProducte(Integer name, String description) {
    try {
      Producte producte = new Producte(name, description);

      producteRepository.save(producte);
      return producte;
    } catch (Exception ex) {
      // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
      // We catch the normal exception and then transform it in a EJBException
      throw new ServiceException(ex.getMessage());
    }
  }

  public Collection<Producte> getProductes() {
    Collection<Producte> r = new ArrayList<>();

    return StreamSupport.stream(producteRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }
}
