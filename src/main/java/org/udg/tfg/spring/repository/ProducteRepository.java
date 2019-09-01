package org.udg.tfg.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.udg.tfg.spring.entity.Producte;

@Component
public interface ProducteRepository extends CrudRepository<Producte, Long> {}
