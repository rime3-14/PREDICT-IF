/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 *
 * @author omercul
 */
@Embeddable
public class Profil implements Serializable{
    
    @Column(nullable = false)
    private String zodiac;
    
    @Column(nullable = false)
    private String chinois;
    
    @Column(nullable = false)
    private String porte_bonheur;
    
    @Column(nullable = false)
    private String animal;


    @Override
    public String toString() {
        return "Profil{" + ", zodiac=" + zodiac + ", chinois=" + chinois + ", porte_bonheur=" + porte_bonheur + ", animal=" + animal + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.zodiac);
        hash = 97 * hash + Objects.hashCode(this.chinois);
        hash = 97 * hash + Objects.hashCode(this.porte_bonheur);
        hash = 97 * hash + Objects.hashCode(this.animal);
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
        final Profil other = (Profil) obj;
        if (!Objects.equals(this.zodiac, other.zodiac)) {
            return false;
        }
        if (!Objects.equals(this.chinois, other.chinois)) {
            return false;
        }
        if (!Objects.equals(this.animal, other.animal)) {
            return false;
        }
        return Objects.equals(this.porte_bonheur, other.porte_bonheur);
    }

    public Profil(String zodiac, String chinois, String porte_bonheur, String animal) {
        this.zodiac = zodiac;
        this.chinois = chinois;
        this.porte_bonheur = porte_bonheur;
        this.animal = animal;
    }

    public Profil() {
    }

    public String getZodiac() {
        return zodiac;
    }

    public String getChinois() {
        return chinois;
    }

    public String getPorte_bonheur() {
        return porte_bonheur;
    }

    public String getAnimal() {
        return animal;
    }

    public void setZodiac(String zodiac) {
        this.zodiac = zodiac;
    }

    public void setChinois(String chinois) {
        this.chinois = chinois;
    }

    public void setPorte_bonheur(String porte_bonheur) {
        this.porte_bonheur = porte_bonheur;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
}
