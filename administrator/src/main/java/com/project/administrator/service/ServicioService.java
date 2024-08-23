package com.project.administrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.administrator.exception.DuplicateResourceException;
import com.project.administrator.exception.ResourceNotFoundException;
import com.project.administrator.model.Servicio;
import com.project.administrator.repository.ServicioRepository;
import com.project.administrator.repository.ClientRepository;
import java.util.List;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Servicio guardarServicio(Servicio servicio) {
        if (servicioRepository.existsByIdentificacionAndServicio(servicio.getIdentificacion(), servicio.getServicio())) {
            throw new DuplicateResourceException("El registro ya existe", 409);
        }

        if(!clientRepository.existsByIdentificacion(servicio.getIdentificacion())) {
            throw new ResourceNotFoundException("No se puede asociar el servicio a un cliente que no existe");
        }
        return servicioRepository.save(servicio);
    }

    public List<Servicio> obtenerServiciosPorCliente(String identificacion) {
        return servicioRepository.findByIdentificacion(identificacion);
    }
   
}
