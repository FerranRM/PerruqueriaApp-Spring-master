package org.udg.tfg.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.tfg.spring.controller.exceptions.ServiceException;
import org.udg.tfg.spring.entity.ServeiPrestat;
import org.udg.tfg.spring.repository.ServeiPrestatRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServeiPrestatService {

  @Autowired
  ServeiPrestatRepository serveiPrestatRepository;

  public ServeiPrestatRepository crud() {
    return serveiPrestatRepository;
  }

  public ServeiPrestat getServeiPrestat(Long id) {
    Optional<ServeiPrestat> ot = serveiPrestatRepository.findById(id);
    if (!ot.isPresent())
      throw new ServiceException("Servei prestat no existeix");
    else
      return ot.get();
  }

  public Collection<ServeiPrestat> getServeiPrestats() {
    Collection<ServeiPrestat> r = new ArrayList<>();

    return StreamSupport.stream(serveiPrestatRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  @Transactional
  public ServeiPrestat addServeiPrestat(Integer preu, String descripcio) {
    try {
      ServeiPrestat serveiPrestat = new ServeiPrestat(preu, descripcio);

      serveiPrestatRepository.save(serveiPrestat);
      return serveiPrestat;
    } catch (Exception ex) {
      // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
      // We catch the normal exception and then transform it in a EJBException
      throw new ServiceException(ex.getMessage());
    }
  }
}
