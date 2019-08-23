package org.udg.pds.springtodo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.Reserva;

@Component
public interface ReservaRepository extends CrudRepository<Reserva, Long> {}
