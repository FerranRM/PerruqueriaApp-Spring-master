package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Data;
import org.udg.pds.springtodo.repository.DataRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DataService {

    @Autowired
    PerruquerService perruquerService;

    @Autowired
    DataRepository dataRepository;

    public DataRepository crud() {
        return dataRepository;
    }

    public Data getData(Long id) {
        Optional<Data> ot = dataRepository.findById(id);
        if (!ot.isPresent())
            throw new ServiceException("Data no existeix");
        else
            return ot.get();
    }

    public Data addData(Date data, int hora) {
        try {
            Data novaData = new Data(data, hora);

            dataRepository.save(novaData);
            return novaData;
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
            // We catch the normal exception and then transform it in a EJBException
            throw new ServiceException(ex.getMessage());
        }
    }

    public Collection<Data> getDates(Long id) {
        Collection<Data> r = new ArrayList<>();

        return StreamSupport.stream(dataRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
