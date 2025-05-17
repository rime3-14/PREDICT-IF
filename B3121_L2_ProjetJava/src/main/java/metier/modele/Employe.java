/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author omercul
 */
@Entity
public class Employe implements Serializable {
     
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)    
    private String nom;
   
    @Column(nullable = false)
    private String prenom;
    
    @Column(nullable = false)
    private String telephone;
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    @Column(nullable = false)
    private Boolean dispo;
    
    @Column(nullable = false)
    private String genre;
    
    @Column(nullable = false)
    private String mdp;
    
    @OneToMany(mappedBy = "employe")
    private List<Consultation> consultations;

    public Employe() {
    }

    public Employe(String nom, String prenom, String telephone, String mail, String mdp, Boolean dispo, String genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.mail = mail;
        this.mdp = mdp;
        this.dispo = true;
        this.genre = genre;
        this.consultations = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getMdp() {
        return mdp;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMail() {
        return mail;
    }

    public Boolean getDispo() {
        return dispo;
    }

    public String getGenre() {
        return genre;
    }
    
    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setDispo(Boolean dispo) {
        this.dispo = dispo;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public void ajouterConsultation(Consultation consultation){
        this.consultations.add(consultation);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.prenom);
        hash = 97 * hash + Objects.hashCode(this.telephone);
        hash = 97 * hash + Objects.hashCode(this.mail);
        hash = 97 * hash + Objects.hashCode(this.dispo);
        hash = 97 * hash + Objects.hashCode(this.genre);
        hash = 97 * hash + Objects.hashCode(this.mdp);
        hash = 97 * hash + Objects.hashCode(this.consultations);
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
        final Employe other = (Employe) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.mdp, other.mdp)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dispo, other.dispo)) {
            return false;
        }
        if (!Objects.equals(this.consultations, other.consultations)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", mail=" + mail + ", dispo=" + dispo + ", genre=" + genre + ", mdp=" + mdp + ", consultations=" + consultations.size() + '}';
    }

    
}
