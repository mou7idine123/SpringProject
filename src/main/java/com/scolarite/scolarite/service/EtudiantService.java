package com.scolarite.scolarite.service;

import com.scolarite.scolarite.entities.Etudiant;
import com.scolarite.scolarite.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    public Etudiant findById(Long id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec l'id : " + id));
    }

    public Etudiant findByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec le matricule : " + matricule));
    }

    public Etudiant save(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public Etudiant update(Long id, Etudiant etudiantModifie) {
        Etudiant etudiant = findById(id);
        etudiant.setNom(etudiantModifie.getNom());
        etudiant.setPrenom(etudiantModifie.getPrenom());
        etudiant.setMatricule(etudiantModifie.getMatricule());
        etudiant.setEmail(etudiantModifie.getEmail());
        etudiant.setDateNaissance(etudiantModifie.getDateNaissance());
        etudiant.setFiliere(etudiantModifie.getFiliere());
        etudiant.setNiveau(etudiantModifie.getNiveau());
        return etudiantRepository.save(etudiant);
    }

    public void delete(Long id) {
        findById(id); // vérifie l'existence avant suppression
        etudiantRepository.deleteById(id);
    }
}
