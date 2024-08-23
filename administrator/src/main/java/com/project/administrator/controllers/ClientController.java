package com.project.administrator.controllers;

import jakarta.validation.Valid;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.administrator.service.ClientService;
import com.project.administrator.exception.DuplicateResourceException;
import com.project.administrator.model.Client;

@RestController
@RequestMapping("/clientes")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearCliente(@Valid @RequestBody Client client) {
        try {
            clientService.guardarCliente(client);
            return ResponseEntity.ok("Cliente creado exitosamente");
        } catch (DuplicateResourceException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<Client> obtenerCliente(@PathVariable String identificacion) {
        Client client = clientService.obtenerCliente(identificacion);
        return ResponseEntity.ok(client);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Client>> obtenerTodosLosClientesConServicios() {
        List<Client> clientes = clientService.obtenerTodosLosClientesConServicios();
        return ResponseEntity.ok(clientes);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> handleDuplicateResourceException(DuplicateResourceException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @DeleteMapping("/{identificacion}")
    public void eliminarCliente(@PathVariable String identificacion) {
        clientService.eliminarCliente(identificacion);
    }

}
