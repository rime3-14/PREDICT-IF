/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Profil;

/**
 *
 * @author omercul
 */
public class ProfilDao {
    
    public void create(Profil profil){
        JpaUtil.obtenirContextePersistance().persist(profil);
    }
    
    public List<Profil> findAll(){
        String s = "select p from Profil p";
        TypedQuery q = JpaUtil.obtenirContextePersistance().createQuery(s,Profil.class);
        return q.getResultList();
    }
    
    public Profil findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Profil.class, id);
    }
    
}
