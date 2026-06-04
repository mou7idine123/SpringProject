package com.scolarite.scolarite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    private double note;
    private String annee;

    public Note() {}

    public Long getId() { return id; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }

    public double getNote() { return note; }
    public void setNote(double note) { this.note = note; }

    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
}
