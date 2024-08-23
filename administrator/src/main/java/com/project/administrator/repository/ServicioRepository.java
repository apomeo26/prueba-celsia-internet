package com.project.administrator.repository;
import com.project.administrator.model.Servicio;
import com.project.administrator.model.ServicioId;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, ServicioId> {
    List<Servicio> findByIdentificacion(String identificacion);

    boolean existsByIdentificacionAndServicio(String identificacion, String servicio);

}
