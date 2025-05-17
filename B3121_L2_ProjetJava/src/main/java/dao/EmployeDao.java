/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Employe;
import metier.modele.Consultation;

/**
 *
 * @author omercul
 */
public class EmployeDao {
    
    public void create(Employe employe){
        JpaUtil.obtenirContextePersistance().persist(employe);
    }
    
    public Employe update(Employe employe) {
        return JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    public List<Employe> findAll(){
        String s = "select e from Employe e";
        TypedQuery q = JpaUtil.obtenirContextePersistance().createQuery(s,Employe.class);
        return q.getResultList();
    }
    
    public Employe findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }
    
    public Employe findByMail(String mail) {
        return JpaUtil.obtenirContextePersistance()
                     .createQuery("SELECT e FROM Employe e WHERE e.mail = :mail", Employe.class)
                     .setParameter("mail", mail)
                     .getSingleResult();
    }
    
    public Employe findEmployeDisponibleParGenreEtMoinsOccupe(String genre) {
        String jpql = "SELECT e FROM Employe e " +
                      "WHERE e.dispo = true AND e.genre = :genre " +
                      "ORDER BY SIZE(e.consultations) ASC";

        TypedQuery<Employe> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Employe.class);
        query.setParameter("genre", genre);
        query.setMaxResults(1);
        List<Employe> result = query.getResultList();

        return result.isEmpty() ? null : result.get(0);
    }
    
    public List<Object[]> countClientsByEmploye() {
        String jpql = "SELECT c.employe, COUNT(DISTINCT c.client) " +
                      "FROM Consultation c " +
                      "GROUP BY c.employe " +
                      "ORDER BY COUNT(DISTINCT c.client) DESC";

        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Object[].class);
        return query.getResultList();
    }
    
    public Consultation findCurrentConsultation(Employe employe) {
        String jpql = "SELECT c FROM Consultation c WHERE c.employe = :employe AND c.duree IS NULL";

        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance()
                                                .createQuery(jpql, Consultation.class);
        query.setParameter("employe", employe);

        List<Consultation> result = query.getResultList();

        return result.get(0);
    }
    
}

