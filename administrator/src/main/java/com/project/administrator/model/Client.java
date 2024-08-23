package com.project.administrator.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.persistence.CascadeType;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Client {
    @Id
    private String identificacion;

    @NotNull
    @Size(max = 80)
    @Column(name = "nombres")
    private String nombres;

    @NotNull
    @Size(max = 80)
    @Column(name = "apellidos")
    private String apellidos;

    @NotNull
    @Size(max = 2)
    @Column(name = "tipoIdentificacion")
    private String tipoIdentificacion;

    @NotNull
    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    @NotNull
    @Size(max = 20)
    @Column(name = "numeroCelular")
    private String numeroCelular;

    @NotNull
    @Email
    @Size(max = 80)
    @Column(name = "correoElectronico")
    private String correoElectronico;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servicio> servicios;
}
