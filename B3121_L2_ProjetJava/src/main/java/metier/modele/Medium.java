/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author omercul
 */
@Entity
public class Medium implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String specialite;
    
    @Column(nullable = false)
    private String genre;
    
    @Column(nullable = false) 
    private String presentation;
     
    private String support;
    
    private String formation;

    public Medium() {
    }
    
    public Medium(String nom, String specialite, String genre, String presentation) {
        this.nom = nom;
        this.specialite = specialite;
        this.genre = genre;
        this.presentation = presentation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.nom);
        hash = 41 * hash + Objects.hashCode(this.specialite);
        hash = 41 * hash + Objects.hashCode(this.genre);
        hash = 41 * hash + Objects.hashCode(this.presentation);
        hash = 41 * hash + Objects.hashCode(this.support);
        hash = 41 * hash + Objects.hashCode(this.formation);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Medium other = (Medium) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.specialite, other.specialite)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.presentation, other.presentation)) {
            return false;
        }
        if (!Objects.equals(this.support, other.support)) {
            return false;
        }
        if (!Objects.equals(this.formation, other.formation)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "Medium{" + "id=" + id + ", nom=" + nom + ", specialite=" + specialite + ", genre=" + genre + ", presentation=" + presentation + ", support=" + support + ", formation=" + formation + '}';
    }

    
    
}
