package org.udg.pds.springtodo.service;

import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.repository.ClientFetRepository;
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
    ClientFetRepository clientFetRepository;

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

    public Client addClient(String nom, Integer preu, Boolean sexe, Integer tipusTall,Date data,Long id) {
        Client nouClient = new Client(nom, preu, sexe, tipusTall,data,id);
        clientRepository.save(nouClient);

        //Obtenim el dia actual per poder afegir el client a ClientFet
        Date currentTime = Calendar.getInstance().getTime();
        Calendar calendar = GregorianCalendar.getInstance();

        //Creem Data
        Data dataHora = new Data(currentTime,calendar.get(Calendar.HOUR_OF_DAY));

        //Creem ClientFet i el guardem
        /*ClientFet nouClientFet = new ClientFet(data,nouClient,perruquerService.getPerruquer((long) 1));
        //COMO OBTENGO EL ID DEL PELUQUERO ACTUAL?????????????????????
        clientFetRepository.save(nouClientFet);*/

        return nouClient;

    }

    public Collection<Client> getClients(Long id) {
        Collection<Client> r = new ArrayList<>();

        return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
