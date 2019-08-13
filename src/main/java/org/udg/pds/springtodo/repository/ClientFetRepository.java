package org.udg.pds.springtodo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.ClientFet;

@Component
public interface ClientFetRepository extends CrudRepository<ClientFet, Long> {

}
