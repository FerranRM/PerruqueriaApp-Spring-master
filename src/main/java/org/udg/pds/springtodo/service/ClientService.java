package org.udg.pds.springtodo.service;

import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.repository.ClientRepository;

import java.rmi.server.ServerCloneException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientService {

    @Autowired
    PerruquerService perruquerService;

    @Autowired
    ClientRepository clientRepository;


    public ClientRepository crud() {
        return clientRepository;
    }

    public Client getClient(Long id) {
        Optional<Client> ot = clientRepository.findById(id);
        if (!ot.isPresent())
            throw new ServiceException("Client no existeix");
        else
            return ot.get();
    }

    @Transactional
    public IdObject addClient(String nom, Integer preu, Boolean sexe, Integer tipusTall,Date data, Long perruquerId) {

        try {
            Perruquer user = perruquerService.getPerruquer(perruquerId);

            Client nouClient = new Client(nom, preu, sexe, tipusTall, data);

            nouClient.setPerruquer(user);

            user.addClient(nouClient);

            clientRepository.save(nouClient);
            return new IdObject(nouClient.getIdClient());
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }

    }

    @Transactional
    public IdObject addClient(Client client, Long perruquerId) {

        try {
            Perruquer user = perruquerService.getPerruquer(perruquerId);

            client.setPerruquer(user);

            user.addClient(client);

            clientRepository.save(client);
            return new IdObject(client.getIdClient());
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }

    }

    public Collection<Client> getClients(Long id) {
        Optional<Perruquer> p = perruquerService.crud().findById(id);
        if (!p.isPresent()) throw new ServiceException("Perruquer no existeix");
        return p.get().getClients();
    }



}
