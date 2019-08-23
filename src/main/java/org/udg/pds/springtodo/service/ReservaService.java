package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Perruquer;
import org.udg.pds.springtodo.entity.Reserva;
import org.udg.pds.springtodo.repository.ReservaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class ReservaService {

  @Autowired
  ReservaRepository reservaRepository;

  @Autowired
  PerruquerService perruquerService;


  public ReservaRepository crud() {
    return reservaRepository;
  }

  public Collection<Reserva> getReserves(Long id) {
    Optional<Perruquer> p = perruquerService.crud().findById(id);
    if (!p.isPresent()) throw new ServiceException("Perruquer no existeix");
    return p.get().getReserves();
  }

  public Reserva getReserva(Long userId, Long id) {
    Optional<Reserva> t = reservaRepository.findById(id);
    if (!t.isPresent()) throw new ServiceException("Reserva does not exists");
    if (t.get().getPerruquer().getId() != userId)
      throw new ServiceException("User does not own this reserva");
    return t.get();
  }

  @Transactional
  public IdObject addReserva(String nomReserva, Long userId, Date dataReserva) {
    try {
      Perruquer user = perruquerService.getPerruquer(userId);

      Reserva reserva = new Reserva(dataReserva, nomReserva);

      reserva.setPerruquer(user);

      user.addReserva(reserva);

      reservaRepository.save(reserva);
      return new IdObject(reserva.getId());
    } catch (Exception ex) {
      // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
      // We catch the normal exception and then transform it in a ServiceException
      throw new ServiceException(ex.getMessage());
    }
  }



}
