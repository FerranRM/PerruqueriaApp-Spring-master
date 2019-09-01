package org.udg.tfg.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.udg.tfg.spring.entity.TallCabells;

@Component
public interface TallCabellsRepository extends CrudRepository<TallCabells, Long> {}
