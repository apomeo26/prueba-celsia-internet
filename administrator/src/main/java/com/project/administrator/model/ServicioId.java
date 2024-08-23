package com.project.administrator.model;

import java.io.Serializable;
import java.util.Objects;

public class ServicioId implements Serializable{
    private String identificacion;
    private String servicio;

    public ServicioId() {}

    public ServicioId(String identificacion, String servicio) {
        this.identificacion = identificacion;
        this.servicio = servicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServicioId that = (ServicioId) o;
        return Objects.equals(identificacion, that.identificacion) &&
               Objects.equals(servicio, that.servicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacion, servicio);
    }
}
