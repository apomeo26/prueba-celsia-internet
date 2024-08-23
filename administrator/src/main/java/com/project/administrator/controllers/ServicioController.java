package com.project.administrator.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.project.administrator.model.Servicio;
import com.project.administrator.service.ServicioService;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearServicio(@Valid @RequestBody Servicio servicio) {
        servicioService.guardarServicio(servicio);
        return ResponseEntity.ok("Servicio creado exitosamente");
    }

    @GetMapping("/cliente/{identificacion}")
    public ResponseEntity<List<Servicio>> obtenerServiciosPorCliente(@PathVariable String identificacion) {
        List<Servicio> servicios = servicioService.obtenerServiciosPorCliente(identificacion);
        return ResponseEntity.ok(servicios);
    }
    
}
