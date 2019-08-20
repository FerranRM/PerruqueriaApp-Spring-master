package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.entity.Perruquer;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public interface PerruquerRepository extends CrudRepository<Perruquer, Long> {
    @Query("SELECT p FROM perruquers p WHERE p.nomPerruquer=:nomPerruquer")
    List<Perruquer> trobarPerNomPerruquer(@Param("nomPerruquer") String nomPerruquer);

    @Query("SELECT p FROM perruquers p WHERE p.email=:email")
    List<Perruquer> trobarPerEmail(@Param("email") String email);
}
