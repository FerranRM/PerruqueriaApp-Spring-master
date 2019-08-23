package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Perruquer;
import org.udg.pds.springtodo.entity.Producte;
import org.udg.pds.springtodo.entity.Client;
import org.udg.pds.springtodo.repository.ClientRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  ClientRepository clientRepository;

  @Autowired
  PerruquerService perruquerService;

  @Autowired
  protected ProducteService producteService;

  public ClientRepository crud() {
    return clientRepository;
  }

  public Collection<Client> getClients(Long id) {
    Optional<Perruquer> p = perruquerService.crud().findById(id);
    if (!p.isPresent()) throw new ServiceException("Perruquer no existeix");
    return p.get().getClients();
  }

  public Client getClient(Long userId, Long id) {
    Optional<Client> t = clientRepository.findById(id);
    if (!t.isPresent()) throw new ServiceException("Task does not exists");
    if (t.get().getPerruquer().getId() != userId)
      throw new ServiceException("User does not own this task");
    return t.get();
  }

  @Transactional
  public IdObject addClient(String nomClient, Long userId,
                          Date dataClient, Integer pentinatClient, Integer preuTotal, Boolean sexeClient) {
    try {
      Perruquer user = perruquerService.getPerruquer(userId);

      Client client = new Client(dataClient, pentinatClient, sexeClient, nomClient, preuTotal);

      client.setPerruquer(user);

      user.addClient(client);

      clientRepository.save(client);
      return new IdObject(client.getId());
    } catch (Exception ex) {
      // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
      // We catch the normal exception and then transform it in a ServiceException
      throw new ServiceException(ex.getMessage());
    }
  }

  @Transactional
  public void addProductesToClient(Long userId, Long taskId, Collection<Long> productes) {
    Client t = this.getClient(userId, taskId);

    if (t.getPerruquer().getId() != userId)
      throw new ServiceException("This user is not the owner of the Client");

    try {
      for (Long producteId : productes) {
        Optional<Producte> oProducte = producteService.crud().findById(producteId);
        if (oProducte.isPresent())
          t.addProducte(oProducte.get());
        else
          throw new ServiceException("Producte dos not exists");
      }
    } catch (Exception ex) {
      // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
      // We catch the normal exception and then transform it in a ServiceException
      throw new ServiceException(ex.getMessage());
    }
  }

  public Collection<Producte> getClientsProductes(Long userId, Long id) {
    Client t = this.getClient(userId, id);
    Perruquer u = t.getPerruquer();

    if (u.getId() != userId)
      throw new ServiceException("Logged user does not own the Client");

    return t.getProductes();
  }

}
