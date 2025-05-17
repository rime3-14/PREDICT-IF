/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author omercul
 */
@Entity
public class Client implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String prenom;
    
    @Temporal(TemporalType.DATE)
    private Date date_de_naissance;
    
    @Column(nullable = false)
    private String adresse;
    
    @Column(nullable = false)
    private String telephone;
    
    @Column(nullable = false, unique = true)
    private String mail;
    
    @Column(nullable = false)
    private String motDePasse;
    
    @OneToMany(mappedBy = "client")
    private List<Consultation> consultations;
    
    @Embedded
    private Profil profil;
    
    private LatLng coordonnees;

    public Client() {
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Client(String nom, String prenom, Date date_de_naissance, String adresse, String telephone, String mail, String motDePasse) {

        this.nom = nom;
        this.prenom = prenom;
        this.date_de_naissance = date_de_naissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.consultations = new ArrayList<>();
    }

    public LatLng getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(LatLng coordonnees) {
        this.coordonnees = coordonnees;
    }

    
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }
    
    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public void ajouterConsultation(Consultation consultation){
        this.consultations.add(consultation);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.nom);
        hash = 53 * hash + Objects.hashCode(this.prenom);
        hash = 53 * hash + Objects.hashCode(this.date_de_naissance);
        hash = 53 * hash + Objects.hashCode(this.adresse);
        hash = 53 * hash + Objects.hashCode(this.telephone);
        hash = 53 * hash + Objects.hashCode(this.mail);
        hash = 53 * hash + Objects.hashCode(this.motDePasse);
        hash = 53 * hash + Objects.hashCode(this.consultations);
        hash = 53 * hash + Objects.hashCode(this.profil);
        hash = 53 * hash + Objects.hashCode(this.coordonnees);
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
        final Client other = (Client) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.motDePasse, other.motDePasse)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date_de_naissance, other.date_de_naissance)) {
            return false;
        }
        if (!Objects.equals(this.consultations, other.consultations)) {
            return false;
        }
        if (!Objects.equals(this.profil, other.profil)) {
            return false;
        }
        return Objects.equals(this.coordonnees, other.coordonnees);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", date_de_naissance=" + date_de_naissance + ", adresse=" + adresse + ", telephone=" + telephone + ", mail=" + mail + ", motDePasse=" + motDePasse + ", consultations=" + consultations.size() + ", coordonnées=" + coordonnees + ", profil=" + profil + '}';
    }


}
