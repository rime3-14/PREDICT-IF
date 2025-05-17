/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Consultation;

/**
 *
 * @author omercul
 */
public class ClientDao {
    
    public void create(Client client){
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public Client update(Client client) {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public List<Client> findAll(){
        String s = "select c from Client c";
        TypedQuery q = JpaUtil.obtenirContextePersistance().createQuery(s,Client.class);
        return q.getResultList();
    }
    
    public Client findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    public Client findByMail(String mail) {
        return JpaUtil.obtenirContextePersistance()
                     .createQuery("SELECT c FROM Client c WHERE c.mail = :mail", Client.class)
                     .setParameter("mail", mail)
                     .getSingleResult();
    }
    
    public List<Object[]> findMediumsWithConsultationCount(Client client) {
        String s = "SELECT m, COUNT(c) FROM Consultation c JOIN c.medium m WHERE c.client = :client GROUP BY m";
        TypedQuery<Object[]> query = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class);
        query.setParameter("client", client);
        List<Object[]> results = query.getResultList();
        
        return results;
    }
    
    public Consultation findCurrentConsultation(Client client) {
        String jpql = "SELECT c FROM Consultation c WHERE c.client = :client AND c.duree IS NULL";

        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance()
                                                .createQuery(jpql, Consultation.class);
        query.setParameter("client", client);

        List<Consultation> result = query.getResultList();

        return result.get(0);
    }
}
