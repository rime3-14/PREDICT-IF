/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import metier.modele.Client;
import metier.modele.Employe;
import util.AstroNetApi;
import util.Message;
import metier.modele.Profil;
import metier.modele.Medium;
import metier.modele.Consultation;
import util.GeoNetApi;


/**
 *
 * @author omercul
 */
public class Service {    
    public Boolean inscrireClient(Client client){
        
        ClientDao clientdao = new ClientDao();
        
        AstroNetApi astroApi = new AstroNetApi();
        
        Boolean succes = true;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            List<String> profil = astroApi.getProfil(client.getPrenom(), client.getDate_de_naissance());
            
            String signeZodiaque = profil.get(0);
            String signeChinois = profil.get(1);
            String couleur = profil.get(2);
            String animal = profil.get(3);
            
            Profil p = new Profil(signeZodiaque, signeChinois, couleur, animal);
            client.setProfil(p);
            
            String adresse = client.getAdresse();
            LatLng coords = GeoNetApi.getLatLng(adresse);
            
            if (coords != null) {
                client.setCoordonnees(coords);  
            }
            
            clientdao.create(client);
            
            JpaUtil.validerTransaction();
            
            Message.envoyerMail("service@gmail.com", client.getMail(), "Bienvenue chez Predict'IF" , "Bonjour "+client.getPrenom()+", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums");

            
        } catch (Exception e) {
            e.printStackTrace();
            succes = false;
            Message.envoyerMail("service@gmail.com", client.getMail(), "Echec de l'inscription chez PREDICT'IF" , "Bonjour "+client.getPrenom()+", votre inscription au service PREDICT'IF a malencontreusement échoué ... Merci de recommencer ultérieurement.");
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return succes;
    }
    
    public Client trouverClientParId(Long id){
        ClientDao clientdao = new ClientDao();
        Client client =  null;
        
        try {
            JpaUtil.creerContextePersistance();
            client = clientdao.findById(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return client;
    }
    
    public Medium trouverMediumParId(Long id){
        MediumDao mediumdao = new MediumDao();
        Medium medium = null;
        
        try {
            JpaUtil.creerContextePersistance();
            medium = mediumdao.findById(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return medium;
    }
    
        public Employe trouverEmployeParId(Long id){
        EmployeDao employedao = new EmployeDao();
        Employe employe = null;
        
        try {
            JpaUtil.creerContextePersistance();
            employe = employedao.findById(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return employe;
    }
        
        public Consultation trouverConsultationParId(Long id){
        ConsultationDao consultationdao = new ConsultationDao();
        Consultation consultation = null;
        
        try {
            JpaUtil.creerContextePersistance();
            consultation = consultationdao.findById(id);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
        
    
    public Consultation creerConsultation(Client client, Medium medium){
        ClientDao clientdao = new ClientDao();
        EmployeDao employedao = new EmployeDao();
        ConsultationDao consultationdao = new ConsultationDao();
        Consultation consultation = null;
        
        try {
            
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            /*
            List<Employe> listemploye = employedao.findAll();
            Employe employe = null;
            int act;
            int min = Integer.MAX_VALUE;
            for (Employe employe1 : listemploye) {
                if(employe1.getDispo() == true && employe1.getGenre().compareTo(medium.getGenre()) == 0){
                    act = employe1.getConsultations().size();
                    if(act < min){
                        min = act;
                        employe = employe1;
                    }
                }
            }
            */
            Employe employe = employedao.findEmployeDisponibleParGenreEtMoinsOccupe(medium.getGenre());
            
            consultation = new Consultation();
            
            if (employe == null){
                consultation = null;
            }
            else {
                employe.setDispo(false);

                consultation.setClient(client);
                consultation.setEmploye(employe);
                consultation.setMedium(medium);

                client.ajouterConsultation(consultation);
                employe.ajouterConsultation(consultation);

                consultationdao.create(consultation);
                client = clientdao.update(client);
                employe = employedao.update(employe);
                Message.envoyerNotification(employe.getTelephone(), "Bonjour " + employe.getPrenom() + ". Consultation requise pour " + client.getPrenom()+ " " + client.getNom() + ". Médium à incarner : " + medium.getNom());    
            }
            
            
            JpaUtil.validerTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
    
    public Consultation commencerConsultation(Consultation consultation){
        ClientDao clientdao = new ClientDao();
        EmployeDao employedao = new EmployeDao();
        ConsultationDao consultationdao = new ConsultationDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Date dateDeb = new Date();
            consultation.setDateDeb(dateDeb);
            
            Employe emp = consultation.getEmploye();
            Client cli = consultation.getClient();
            Medium med = consultation.getMedium();

            
            consultation = consultationdao.update(consultation);
            emp = employedao.update(emp);
            cli = clientdao.update(cli);
            
            JpaUtil.validerTransaction();
            Message.envoyerNotification(cli.getTelephone(), "Bonjour " + cli.getPrenom() + ". J'ai bien recu votre demande de consultation du " + consultation.getDateDeb()+ ". Vous pouvez dès à présent me contacter au " + emp.getTelephone() + ". A tout de suite ! Médiumiquement vôtre, " + med.getNom());

            
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
    
    public Consultation terminerConsultation(Consultation consultation){
        ClientDao clientdao = new ClientDao();
        EmployeDao employedao = new EmployeDao();
        ConsultationDao consultationdao = new ConsultationDao();
        
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Date fin = new Date();
            
            long diff = (consultation.getDateDeb().getTime() - fin.getTime())/60000;
            
            consultation.setDuree(diff);
            
            Employe emp = consultation.getEmploye();
            Client cli = consultation.getClient();
            emp.setDispo(true);
            consultation = consultationdao.update(consultation);
            emp = employedao.update(emp);
            cli = clientdao.update(cli);
            
            JpaUtil.validerTransaction();
            
        }
        catch(Exception e){
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
    
    public Consultation ajouterCommentaire(Consultation consultation, String commentaire){
        ConsultationDao consultationdao = new ConsultationDao();
        ClientDao clientdao = new ClientDao();
        EmployeDao employedao = new EmployeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            consultation.setCommentaire(commentaire);
            consultationdao.update(consultation);
            
            Employe emp = consultation.getEmploye();
            Client cli = consultation.getClient();
            
            emp = employedao.update(emp);
            cli = clientdao.update(cli);
            
            JpaUtil.validerTransaction();
        } 
        catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
    
    public Client authentifierClient(String mail, String motDePasse){
        ClientDao clientdao = new ClientDao();
        Client client = null;
        try {
            JpaUtil.creerContextePersistance();
            client = clientdao.findByMail(mail);
            if(!client.getMotDePasse().equals(motDePasse)){
                client = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        
        return client;
    }
    
    public Employe authentifierEmploye(String mail, String motDePasse){
        EmployeDao employedao = new EmployeDao();
        Employe employe = null;
        try {
            JpaUtil.creerContextePersistance();
            employe = employedao.findByMail(mail);
            if(!employe.getMdp().equals(motDePasse)){
                employe = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        
        return employe;
    }
    
    public List<Client> listerClient(){
        List<Client> listeClient = new ArrayList<>();
        ClientDao clientdao = new ClientDao();
        try {
            JpaUtil.creerContextePersistance();
            listeClient = clientdao.findAll();
            listeClient.sort(Comparator.comparing(Client::getNom).thenComparing(Client::getPrenom));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }        
        return listeClient;
    }
    
    public List<Employe> listerEmploye(){
        List<Employe> listeEmploye = new ArrayList<>();
        EmployeDao employedao = new EmployeDao();
        try {
            JpaUtil.creerContextePersistance();
            listeEmploye = employedao.findAll();
            listeEmploye.sort(Comparator.comparing(Employe::getNom).thenComparing(Employe::getPrenom));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }        
        return listeEmploye;
    }
    
    public List<Medium> listerMedium() {
    List<Medium> listeMedium = new ArrayList<>();
    MediumDao mediumdao = new MediumDao();
    try {
        JpaUtil.creerContextePersistance();
        listeMedium = mediumdao.findAll();  
        listeMedium.sort(Comparator.comparing(Medium::getNom)); 
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        JpaUtil.fermerContextePersistance();
    }
    return listeMedium;
}
    public List<Consultation> listerConsultation() {
    List<Consultation> listeConsultation = new ArrayList<>();
    ConsultationDao consultationdao = new ConsultationDao();
    try {
        JpaUtil.creerContextePersistance();
        listeConsultation = consultationdao.findAll();  
        listeConsultation.sort(Comparator.comparing(Consultation::getId)); 
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        JpaUtil.fermerContextePersistance();
    }
    return listeConsultation;
}
    
    public List<Consultation> rechercheHistoriqueMedium(Medium medium){
        List<Consultation> historique = new ArrayList<>();
        MediumDao mediumdao = new MediumDao();
        try {
            JpaUtil.creerContextePersistance();
            historique = mediumdao.findConsultations(medium);
            historique.sort(Comparator.comparing(Consultation::getId));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return historique;
    }
    
    public List<Object[]> rechercheTopMediumPourClient(Client client){
        List<Object[]> top = new ArrayList<>();
        ClientDao clientdao = new ClientDao();
        try {
            JpaUtil.creerContextePersistance();
            top = clientdao.findMediumsWithConsultationCount(client);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return top;
    }

    
    public Boolean initialiser(){
        EmployeDao employedao = new EmployeDao();
        MediumDao mediumdao = new MediumDao();
        
        Boolean succes = true;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Employe e1 = new Employe("NIZEY","Fabio","0778339069", "fabio.nizey@free.fr","urgkjhs458GFT", true, "M");
            employedao.create(e1);
            
            Employe e2 = new Employe("AUGENOA FERNANDEZ","Nicolas","0172819872", "nicolas.augenoa-fernandez@gmail.com","hgygy566754", true, "M");
            employedao.create(e2);
            
            Employe e3 = new Employe("SONGH","Fabienne","0372660272", "fabienne.songh@hotmail.com","YEJH875", true, "F");
            employedao.create(e3);
            
            Employe e4 = new Employe("SOMENNET","Agnieszka","0618844948", "asomennet@laposte.net","jhfty23369", true, "F");
            employedao.create(e4);
            
            employedao.create(new Employe("MARTIN", "Lucas", "0600000001", "lucas.martin@mail.com", "pass123", true, "M"));
            employedao.create(new Employe("DUPONT", "Claire", "0600000002", "claire.dupont@mail.com", "motdepasse", true, "F"));
            employedao.create(new Employe("LEFEBVRE", "Jean", "0600000003", "jean.lefebvre@mail.com", "azerty", true, "M"));
            employedao.create(new Employe("PETIT", "Julie", "0600000004", "julie.petit@mail.com", "julie2023", true, "F"));
            employedao.create(new Employe("MOREAU", "Hugo", "0600000005", "hugo.moreau@mail.com", "hugopass", true, "M"));
            employedao.create(new Employe("ROUSSEAU", "Emma", "0600000006", "emma.rousseau@mail.com", "emmaemma", true, "F"));
            employedao.create(new Employe("GARNIER", "Leo", "0600000007", "leo.garnier@mail.com", "leopass", true, "M"));
            employedao.create(new Employe("FAURE", "Sophie", "0600000008", "sophie.faure@mail.com", "sophiesafe", true, "F"));
            employedao.create(new Employe("CHEVALIER", "Nathan", "0600000009", "nathan.chevalier@mail.com", "nath1234", true, "M"));
            employedao.create(new Employe("LAMY", "Camille", "0600000010", "camille.lamy@mail.com", "lamypass", true, "F"));
            employedao.create(new Employe("REY", "Thomas", "0600000011", "thomas.rey@mail.com", "reycode", true, "M"));
            
            
            
            
            Medium m1 = new Medium("Gwenaelle", "Spirite", "F", "Spécialiste des grandes conversations au-delà des frontières.");
            m1.setSupport("Boule de cristal");
            mediumdao.create(m1);

            Medium m2 = new Medium("Mr M", "Astrologue", "M", "Avenir, avenir, que nous réserves-tu ? N'attendez plus !");
            m2.setFormation("Institut de Nouveaux Savoirs Astrologiques - 2010");
            mediumdao.create(m2);

            Medium m3 = new Medium("Cassandra", "Voyante", "F", "Les cartes n'ont aucun secret pour moi.");
            mediumdao.create(m3);

            Medium m4 = new Medium("AstroLuc", "Astrologue", "M", "Votre destin est écrit dans les étoiles.");
            m4.setFormation("École des Sciences Mystiques");
            mediumdao.create(m4);

            Medium m5 = new Medium("Soleil Levant", "Spirite", "F", "Lignée de spirites tibétains.");
            m5.setSupport("Encens sacré");
            mediumdao.create(m5);

            Medium m6 = new Medium("Maître Zhao", "Tarologue", "M", "Signes anciens de la dynastie Ming.");
            m6.setSupport("Cartes de tarot");
            mediumdao.create(m6);

            Medium m7 = new Medium("Vera", "Voyante", "F", "Expériences troublantes avec l’au-delà.");
            mediumdao.create(m7);

            Medium m8 = new Medium("El Magico", "Astrologue", "M", "Mystique mexicain.");
            m8.setFormation("Institut de l'Astrologie Avancée");
            m8.setSupport("Pendule de cuivre");
            mediumdao.create(m8);

            Medium m9 = new Medium("Oumou", "Spirite", "F", "Chanteuse inspirée des traditions sahéliennes.");
            mediumdao.create(m9);

            Medium m10 = new Medium("Zoltar", "Voyant", "M", "Légende des fêtes foraines.");
            m10.setSupport("Marqueur runique");
            mediumdao.create(m10);

            Medium m11 = new Medium("Isadora", "Tarologue", "F", "Maître du destin avec le tarot.");
            m11.setFormation("Académie de Cartomancie Moderne");
            mediumdao.create(m11);

            Medium m12 = new Medium("Ariadna", "Astrologue", "F", "Les étoiles lui parlent depuis l’enfance.");
            mediumdao.create(m12);

            Medium m13 = new Medium("Merlin", "Spirite", "M", "Sorcier connecté aux esprits anciens.");
            m13.setSupport("Encens sacré");
            mediumdao.create(m13);

            Medium m14 = new Medium("Cléopâtre", "Voyante", "F", "Descendante des prêtresses égyptiennes.");
            mediumdao.create(m14);

            Medium m15 = new Medium("Théophile", "Tarologue", "M", "Né avec un tarot dans la main.");
            m15.setSupport("Cartes de tarot");
            m15.setFormation("Temple des Rêves Oubliés");
            mediumdao.create(m15);

            Medium m16 = new Medium("Athéna", "Astrologue", "F", "Maîtrise la sagesse zodiacale grecque.");
            mediumdao.create(m16);

            Medium m17 = new Medium("Karma", "Voyante", "F", "Elle voit clairement les dettes karmiques.");
            m17.setSupport("Boule de cristal");
            mediumdao.create(m17);

            Medium m18 = new Medium("Sirius", "Astrologue", "M", "Guidé par l'étoile du chien céleste.");
            m18.setFormation("Institut de l'Astrologie Avancée");
            mediumdao.create(m18);

            Medium m19 = new Medium("Lilith", "Spirite", "F", "Connue des esprits de la nuit.");
            mediumdao.create(m19);

            Medium m20 = new Medium("Ragnar", "Voyant", "M", "L’œil des anciens vikings vit en lui.");
            m20.setSupport("Marqueur runique");
            mediumdao.create(m20);
            
            JpaUtil.validerTransaction();
            
        } catch (Exception e) {
            e.printStackTrace();
            succes = false;
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return succes;
        
    }   
    
    public List<String> obtenirPrediction(String couleur, String animal, int amour, int sante, int travail){
        AstroNetApi astroApi = new AstroNetApi();
        List<String> predictions = new ArrayList<>();
        try{
             predictions = astroApi.getPredictions(couleur, animal, amour, sante, travail);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return predictions;
    }
    
    public Consultation obtenirConsultationActuelle(Client client){
        Consultation consultation = null;
        ClientDao clientdao = new ClientDao();
        
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            consultation = clientdao.findCurrentConsultation(client);
            
            JpaUtil.validerTransaction();
        }
        catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }

    public Consultation obtenirConsultationActuelle(Employe employe){
        Consultation consultation = null;
        EmployeDao employedao = new EmployeDao();
        
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            consultation = employedao.findCurrentConsultation(employe);
            
            JpaUtil.validerTransaction();
        }
        catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally{
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
    
    public List<Object[]> listerMediumNombreClient() {
        List<Object[]> resultat = new ArrayList<>();
        MediumDao mediumdao = new MediumDao();
        
        try {
            JpaUtil.creerContextePersistance();

            resultat = mediumdao.countClientsByMedium();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
    
    public List<Object[]> listerEmployeNombreClient() {
        List<Object[]> resultat = new ArrayList<>();
        EmployeDao employedao = new EmployeDao();
        try {
            JpaUtil.creerContextePersistance();

            resultat = employedao.countClientsByEmploye();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return resultat;
    }
}
