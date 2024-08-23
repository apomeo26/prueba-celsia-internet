package com.project.administrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.administrator.repository.ClientRepository;
import com.project.administrator.exception.DuplicateResourceException;
import java.util.*;

import jakarta.persistence.EntityManager;

import com.project.administrator.model.Client;
import com.project.administrator.exception.*;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private EntityManager entityManager;

    public Client guardarCliente(Client client) {
        if (clientRepository.existsByIdentificacion(client.getIdentificacion())) {
            throw new DuplicateResourceException("El registro ya existe", 409);
        }
        return clientRepository.save(client);
    }
    
    public Client obtenerCliente(String identificacion) {
        return clientRepository.findById(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }
        
    public List<Client> obtenerTodosLosClientesConServicios() {
        return entityManager.createQuery("SELECT c FROM Client c LEFT JOIN FETCH c.servicios", Client.class).getResultList();
    }


    public void eliminarCliente(String identificacion) {
        Client client = clientRepository.findById(identificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        clientRepository.delete(client);              
    }
    

}
