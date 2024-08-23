package com.project.administrator.repository;

import com.project.administrator.model.Client;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ClientRepository  extends JpaRepository<Client, String>{
    boolean existsByIdentificacion(String identificacion);
    
}
