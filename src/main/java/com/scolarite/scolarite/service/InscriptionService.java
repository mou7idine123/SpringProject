package com.scolarite.scolarite.service;

import com.scolarite.scolarite.entities.Etudiant;
import com.scolarite.scolarite.entities.Inscription;
import com.scolarite.scolarite.entities.Module;
import com.scolarite.scolarite.repository.InscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InscriptionService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantService etudiantService;
    private final ModuleService moduleService;

    public InscriptionService(InscriptionRepository inscriptionRepository,
                               EtudiantService etudiantService,
                               ModuleService moduleService) {
        this.inscriptionRepository = inscriptionRepository;
        this.etudiantService = etudiantService;
        this.moduleService = moduleService;
    }

    public Inscription inscrire(Long etudiantId, Long moduleId) {
        if (inscriptionRepository.existsByEtudiantIdAndModuleId(etudiantId, moduleId)) {
            throw new RuntimeException("L'étudiant est déjà inscrit à ce module.");
        }
        Etudiant etudiant = etudiantService.findById(etudiantId);
        Module module = moduleService.findById(moduleId);

        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setModule(module);
        inscription.setDateInscription(LocalDate.now());

        return inscriptionRepository.save(inscription);
    }

    public List<Inscription> findModulesParEtudiant(Long etudiantId) {
        etudiantService.findById(etudiantId); // vérifie l'existence
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }

    public List<Inscription> findEtudiantsParModule(Long moduleId) {
        moduleService.findById(moduleId); // vérifie l'existence
        return inscriptionRepository.findByModuleId(moduleId);
    }

    public void supprimer(Long inscriptionId) {
        if (!inscriptionRepository.existsById(inscriptionId)) {
            throw new RuntimeException("Inscription introuvable avec l'id : " + inscriptionId);
        }
        inscriptionRepository.deleteById(inscriptionId);
    }
}
