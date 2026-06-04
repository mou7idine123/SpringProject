package com.scolarite.scolarite.repository;

import com.scolarite.scolarite.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    // Toutes les notes d'un étudiant
    List<Note> findByEtudiantId(Long etudiantId);

    // Notes d'un étudiant pour un semestre donné (via le module)
    List<Note> findByEtudiantIdAndModuleSemestre(Long etudiantId, int semestre);

    // Notes d'un étudiant dans un module précis
    List<Note> findByEtudiantIdAndModuleId(Long etudiantId, Long moduleId);
}
