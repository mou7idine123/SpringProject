package com.scolarite.scolarite.repository;

import com.scolarite.scolarite.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findBySemestre(int semestre);
}
