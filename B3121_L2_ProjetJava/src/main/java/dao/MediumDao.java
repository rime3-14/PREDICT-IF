/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Consultation;
import metier.modele.Medium;

/**
 *
 * @author omercul
 */
public class MediumDao {
    
    public void create(Medium medium){
        JpaUtil.obtenirContextePersistance().persist(medium);
    }
    
    public Medium update(Medium medium) {
        return JpaUtil.obtenirContextePersistance().merge(medium);
    }
    
    public List<Medium> findAll(){
        String s = "select m from Medium m";
        TypedQuery q = JpaUtil.obtenirContextePersistance().createQuery(s,Medium.class);
        return q.getResultList();
    }
    
    public Medium findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
    
    public List<Consultation> findConsultations(Medium medium){
        String s = "SELECT c FROM Consultation c JOIN c.medium m WHERE m = :medium";
        TypedQuery q = JpaUtil.obtenirContextePersistance().createQuery(s,Consultation.class);
        q.setParameter("medium", medium);
        return q.getResultList();
    }
    
    public List<Object[]> countClientsByMedium() {
    String jpql = "SELECT c.medium, COUNT(DISTINCT c.client) " +
                    "FROM Consultation c " +
                    "GROUP BY c.medium " +
                    "ORDER BY COUNT(DISTINCT c.client) DESC";

      TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Object[].class);
      return query.getResultList();
    }
}
