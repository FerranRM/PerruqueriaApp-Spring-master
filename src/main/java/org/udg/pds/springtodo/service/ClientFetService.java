package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.entity.ClientFet;
import org.udg.pds.springtodo.entity.Data;
import org.udg.pds.springtodo.entity.Perruquer;
import org.udg.pds.springtodo.repository.ClientFetRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientFetService {

    @Autowired
    PerruquerService perruquerService;

    @Autowired
    ClientFetRepository clientFetRepository;

    public ClientFetRepository crud() {
        return clientFetRepository;
    }

    public ClientFet getClientFet(Long id) {
        Optional<ClientFet> ot = clientFetRepository.findById(id);
        if (!ot.isPresent())
            throw new ServiceException("ClienFet no existeix");
        else
            return ot.get();
    }

    public ClientFet addClientFet(Data data, Client client, Perruquer perruquer) {
        try {
            ClientFet nouClientFet = new ClientFet(data,client,perruquer);

            clientFetRepository.save(nouClientFet);
            return nouClientFet;
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
            // We catch the normal exception and then transform it in a EJBException
            throw new ServiceException(ex.getMessage());
        }
    }

    public Collection<ClientFet> getClientsFets(Long id) {
        Collection<ClientFet> r = new ArrayList<>();

        return StreamSupport.stream(clientFetRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
