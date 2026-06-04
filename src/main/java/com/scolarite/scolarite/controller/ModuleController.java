package com.scolarite.scolarite.controller;

import com.scolarite.scolarite.entities.Module;
import com.scolarite.scolarite.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // GET /api/modules
    @GetMapping
    public ResponseEntity<List<Module>> findAll() {
        return ResponseEntity.ok(moduleService.findAll());
    }

    // GET /api/modules/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Module> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(moduleService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/modules/semestre/{semestre}
    @GetMapping("/semestre/{semestre}")
    public ResponseEntity<List<Module>> findBySemestre(@PathVariable int semestre) {
        return ResponseEntity.ok(moduleService.findBySemestre(semestre));
    }

    // POST /api/modules
    @PostMapping
    public ResponseEntity<Module> save(@RequestBody Module module) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleService.save(module));
    }

    // PUT /api/modules/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Module> update(@PathVariable Long id, @RequestBody Module module) {
        try {
            return ResponseEntity.ok(moduleService.update(id, module));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/modules/{moduleId}/enseignant/{enseignantId}
    @PutMapping("/{moduleId}/enseignant/{enseignantId}")
    public ResponseEntity<Module> affecterEnseignant(@PathVariable Long moduleId,
                                                      @PathVariable Long enseignantId) {
        try {
            return ResponseEntity.ok(moduleService.affecterEnseignant(moduleId, enseignantId));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/modules/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            moduleService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
