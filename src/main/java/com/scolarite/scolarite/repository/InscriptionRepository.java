package com.scolarite.scolarite.repository;

import com.scolarite.scolarite.entities.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    // Tous les modules d'un étudiant
    List<Inscription> findByEtudiantId(Long etudiantId);

    // Tous les étudiants inscrits à un module
    List<Inscription> findByModuleId(Long moduleId);

    // Vérifier si une inscription existe déjà
    boolean existsByEtudiantIdAndModuleId(Long etudiantId, Long moduleId);
}
