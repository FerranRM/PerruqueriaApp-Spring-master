package org.udg.tfg.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.udg.tfg.spring.entity.Perruquer;


import java.util.List;

@Component
public interface PerruquerRepository extends CrudRepository<Perruquer, Long> {
    @Query("SELECT p FROM perruquers p WHERE p.nomPerruquer=:nomPerruquer")
    List<Perruquer> trobarPerNomPerruquer(@Param("nomPerruquer") String nomPerruquer);

    @Query("SELECT p FROM perruquers p WHERE p.nomUsuari=:nomUsuari")
    List<Perruquer> trobarPerNomUsuari(@Param("nomUsuari") String nomUsuari);
}
