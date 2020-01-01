package org.udg.tfg.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.udg.tfg.spring.entity.ServeiPrestat;

@Component
public interface ServeiPrestatRepository extends CrudRepository<ServeiPrestat, Long> {}
