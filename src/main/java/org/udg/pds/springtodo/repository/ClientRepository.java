package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.Client;

import java.util.Date;
import java.util.List;

@Component
public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("SELECT c FROM clients c WHERE c.dataClient between :data1 and :data2")
    List<Client> clientsEntreDates(@Param("data1") Date data1, @Param("data2") Date data2);
}
