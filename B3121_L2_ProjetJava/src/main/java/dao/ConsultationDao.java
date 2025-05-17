/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Consultation;

/**
 *
 * @author omercul
 */
public class ConsultationDao {
    
    public void create(Consultation consultation){
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }

    public Consultation update(Consultation consultation) {
        return JpaUtil.obtenirContextePersistance().merge(consultation);
    }
    
    public List<Consultation> findAll(){
        String s = "select c from Consultation c";
        TypedQuery q = JpaUtil.obtenirContextePersistance().createQuery(s,Consultation.class);
        return q.getResultList();
    }
    
    public Consultation findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }
    
}
