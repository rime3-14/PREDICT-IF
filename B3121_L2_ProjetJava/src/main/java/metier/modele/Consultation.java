/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author omercul
 */
@Entity
public class Consultation implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Long duree; // en minutess
    

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDeb;
    
    private String commentaire;
    
    @ManyToOne
    private Medium medium;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Employe employe;

    public Consultation() {
    }
    
    public Consultation(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.duree);
        hash = 53 * hash + Objects.hashCode(this.dateDeb);
        hash = 53 * hash + Objects.hashCode(this.commentaire);
        hash = 53 * hash + Objects.hashCode(this.medium);
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
        final Consultation other = (Consultation) obj;
        if (!Objects.equals(this.commentaire, other.commentaire)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.duree, other.duree)) {
            return false;
        }
        if (!Objects.equals(this.dateDeb, other.dateDeb)) {
            return false;
        }
        return Objects.equals(this.medium, other.medium);
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", duree=" + duree + ", dateDeb=" + dateDeb + ", commentaire=" + commentaire + ", medium=" + medium + '}';
    }

    
    
    
}
