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

    @Autowired
    protected ProducteService producteService;


    public ClientRepository crud() {
        return clientRepository;
    }

    public Client getClient(Long userId, Long id) {
        Optional<Client> cl = clientRepository.findById(id);
        if (!cl.isPresent())
            throw new ServiceException("Client no existeix");
        if (cl.get().getPerruquer().getId() != userId)
            throw new ServiceException("User does not own this task");
        return cl.get();
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

    /*@Transactional
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

    }*/

    @Transactional
    public void addProductesToClient(Long userId, Long clientId, Collection<Long> productes) {
        Client t = this.getClient(userId, clientId);

        if (t.getPerruquer().getId() != userId)
            throw new ServiceException("This user is not the owner of the task");

        try {
            for (Long prodId : productes) {
                Optional<Producte> otag = producteService.crud().findById(prodId);
                if (otag.isPresent())
                    t.addProducte(otag.get());
                else
                    throw new ServiceException("Producto no existe");
            }
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }
    }

    public Collection<Producte> getClientsProducte(Long userId, Long id) {
        Client t = this.getClient(userId, id);
        Perruquer u = t.getPerruquer();

        if (u.getId() != userId)
            throw new ServiceException("Logged user does not own the client");

        return t.getProductes();
    }

    public Collection<Client> getClients(Long id) {
        Optional<Perruquer> p = perruquerService.crud().findById(id);
        if (!p.isPresent()) throw new ServiceException("Perruquer no existeix");
        return p.get().getClients();
    }



}
