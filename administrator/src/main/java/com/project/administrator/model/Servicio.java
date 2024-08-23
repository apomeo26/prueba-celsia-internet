package com.project.administrator.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "servicios")
@IdClass(ServicioId.class)
public class Servicio {
    @Id
    @Size(max = 20)
    @Column(name = "identificacion")
    private String identificacion;

    @Id
    @Size(max = 80)
    @Column(name = "servicio")
    private String servicio;

    @NotNull
    @Column(name = "fechaInicio")
    private LocalDate fechaInicio;

    @NotNull
    @Column(name = "ultimaFacturacion")
    private LocalDate ultimaFacturacion;

    @NotNull
    @Column(name = "ultimoPago")
    private LocalDate ultimoPago;

    @ManyToOne
    @JoinColumn(name = "identificacion", insertable = false, updatable = false)
    @JsonIgnore
    private Client client;

}
