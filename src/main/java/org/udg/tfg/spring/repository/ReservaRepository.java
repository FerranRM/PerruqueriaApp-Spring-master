package org.udg.tfg.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.udg.tfg.spring.entity.Reserva;

import java.util.Date;
import java.util.List;

@Component
public interface ReservaRepository extends CrudRepository<Reserva, Long> {
    @Query("SELECT r FROM reserves r WHERE r.dataReserva between :data1 and :data2 and r.perruquerId = :userId ORDER BY r.dataReserva")
    List<Reserva> reservesEntreDates(@Param("data1") Date data1, @Param("data2") Date data2, @Param("userId") Long userId);

    //List<Reserva> findByDataReservaBetween(Date data1, Date data2);
}
