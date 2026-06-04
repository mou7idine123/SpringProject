package com.scolarite.scolarite.service;

import com.scolarite.scolarite.entities.Enseignant;
import com.scolarite.scolarite.repository.EnseignantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnseignantService {

    private final EnseignantRepository enseignantRepository;

    public EnseignantService(EnseignantRepository enseignantRepository) {
        this.enseignantRepository = enseignantRepository;
    }

    public List<Enseignant> findAll() {
        return enseignantRepository.findAll();
    }

    public Enseignant findById(Long id) {
        return enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable avec l'id : " + id));
    }

    public Enseignant save(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public Enseignant update(Long id, Enseignant enseignantModifie) {
        Enseignant enseignant = findById(id);
        enseignant.setNom(enseignantModifie.getNom());
        enseignant.setPrenom(enseignantModifie.getPrenom());
        enseignant.setEmail(enseignantModifie.getEmail());
        enseignant.setSpecialite(enseignantModifie.getSpecialite());
        return enseignantRepository.save(enseignant);
    }

    public void delete(Long id) {
        findById(id);
        enseignantRepository.deleteById(id);
    }
}
