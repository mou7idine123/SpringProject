package com.scolarite.scolarite.service;

import com.scolarite.scolarite.entities.Enseignant;
import com.scolarite.scolarite.entities.Module;
import com.scolarite.scolarite.repository.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final EnseignantService enseignantService;

    public ModuleService(ModuleRepository moduleRepository, EnseignantService enseignantService) {
        this.moduleRepository = moduleRepository;
        this.enseignantService = enseignantService;
    }

    public List<Module> findAll() {
        return moduleRepository.findAll();
    }

    public Module findById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module introuvable avec l'id : " + id));
    }

    public List<Module> findBySemestre(int semestre) {
        return moduleRepository.findBySemestre(semestre);
    }

    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    public Module update(Long id, Module moduleModifie) {
        Module module = findById(id);
        module.setCode(moduleModifie.getCode());
        module.setNom(moduleModifie.getNom());
        module.setDescription(moduleModifie.getDescription());
        module.setCoefficient(moduleModifie.getCoefficient());
        module.setSemestre(moduleModifie.getSemestre());
        return moduleRepository.save(module);
    }

    public Module affecterEnseignant(Long moduleId, Long enseignantId) {
        Module module = findById(moduleId);
        Enseignant enseignant = enseignantService.findById(enseignantId);
        module.setEnseignant(enseignant);
        return moduleRepository.save(module);
    }

    public void delete(Long id) {
        findById(id);
        moduleRepository.deleteById(id);
    }
}
