/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import com.google.maps.model.LatLng;
import dao.JpaUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.service.Service;

/**
 *
 * @author omercul
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws ParseException, IOException {
        System.out.println("===== STARTING SERVICE TESTS =====");
        System.out.println(BG_MAGENTA + "===== Vue Client =====" + RESET);
        JpaUtil.creerFabriquePersistance();
        
        testInscrireClient();
        
        Client c4 = testAuthentifierClient("claire.martin@gmail.com", "mdp789");
        Client c1 = testAuthentifierClient("nom.prenom@gmail.com", "lalilaou");
        Client c5 = testAuthentifierClient("", "mdp789");
        
        testTrouverClientParID(c1.getId());
        testTrouverClientParID(c4.getId());
        testTrouverClientParID(5L);

        testInitialiser();
        
        Medium med1 = testTrouverMediumParID(25L);
        Medium med2 = testTrouverMediumParID(26L);
        
        Consultation cons1 = testCreerConsultation(c1,med1);
        Consultation cons2 = testCreerConsultation(c4,med2);
        
        System.out.println(BG_WHITE + "===== Vue Employe =====" + RESET);
        
        
        Employe emp1 = testAuthentifierEmploye("fabio.nizey@free.fr","urgkjhs458GFT");
        Employe emp2 = testAuthentifierEmploye("lucas.martin@mail.com", "pass123");
        Employe emp3 = testAuthentifierEmploye("", "");
        Employe employe = testAuthentifierEmploye(cons1.getEmploye().getMail(), cons1.getEmploye().getMdp());	

        testListerClient();
        testListerEmploye();
        testListerMedium();
        
        cons1 = testObtenirConsultationActuelle(employe);
        testObtenirConsultationActuelle(emp1);
        
        cons1 = testCommencerConsultation(cons1);
        cons1 = testTerminerConsultation(cons1);
        cons1 = testAjouterCommentaire(cons1);
        
        testObtenirConsultationActuelle(cons1.getClient());
        testObtenirConsultationActuelle(cons2.getClient());

        testListerConsultation();
        
        testRechercheHistoriqueMedium();
        
        Consultation cons3 = testCreerConsultation(c1, med1);
        cons3 = testCommencerConsultation(cons3);
        testObtenirPrediction(cons3.getClient());
        cons3 = testTerminerConsultation(cons3);
        cons3 = testAjouterCommentaire(cons3);
        
        testRechercheTopMediumPourClient();
        
        testObtenirPrediction(c4);
        
        testListerMediumNombreClient();
        testListerEmployeNombreClient(); 
        
        JpaUtil.fermerFabriquePersistance();
        System.out.println("===== ALL TESTS DONE =====");     
    }
    
private static void testInscrireClient() throws ParseException {
    System.out.println(FG_GREEN + "--- Testing InscrireClient ---" + RESET);
    
    Service service = new Service();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    Client client0 = new Client( "nom", "prenom",simpleDateFormat.parse("02/02/2002 00:00"), "16 rue des lilas, Villeurbanne","0351248943" ,"nom.prenom@gmail.com", "lalilaou");
    Client client1 = new Client("Dupont", "Marie", simpleDateFormat.parse("12/01/1990 10:30"), "10 rue de la paix, Lyon", "0123456789", "marie.dupont@gmail.com", "mdp123");
    Client client2 = new Client("Lemoine", "Pierre", simpleDateFormat.parse("20/03/1985 15:00"), "15 avenue des Champs, Paris", "0987654321", "pierre.lemoine@gmail.com", "mdp456");
    Client client3 = new Client("Martin", "Claire", simpleDateFormat.parse("05/07/1992 14:20"), "2 place du marché, Toulouse", "0147852369", "claire.martin@gmail.com", "mdp789");
    Client client4 = new Client("Martin", "Claire", simpleDateFormat.parse("05/07/1992 14:20"), "2 place du marché, Toulouse", "0147852369", "claire.martin@gmail.com", "mdp789");

    System.out.println("Client 0 inscrit : " + service.inscrireClient(client0) + client0);
    System.out.println("Client 1 inscrit : " + service.inscrireClient(client1));
    System.out.println("Client 2 inscrit : " + service.inscrireClient(client2));
    System.out.println("Client 3 inscrit : " + service.inscrireClient(client3));
    System.out.println("Client 3bis inscrit : " + service.inscrireClient(client4));
}
    
    private static void testTrouverClientParID(Long id) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing TrouverClientParId ---" + RESET);
        
        Service service = new Service();
       
        Client client = service.trouverClientParId(id);

        System.out.println("Client inscrit : " + client);
        
    }
    
    private static Medium testTrouverMediumParID(Long id) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Trouver Medium Par Id ---" + RESET);

        Service service = new Service();

        Medium medium = service.trouverMediumParId(id);

        System.out.println("Medium trouvé avec l'ID " + id + " : " + medium);
        return medium;
    }
    
    private static Client testAuthentifierClient(String mail, String mdp) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing AuthentifierClient ---" + RESET);
        
        Service service = new Service();

        Client client = service.authentifierClient(mail, mdp);
        
        System.out.println("Client authetifier : " + client);
        
        return client;
    }    
        
    private static Employe testAuthentifierEmploye(String mail, String mdp) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Authentifier Employe ---" + RESET);

        Service service = new Service();

       
        Employe employe = service.authentifierEmploye(mail, mdp);

        if (employe != null) {
            System.out.println("Employé authentifié : " + employe.getNom() + " " + employe.getPrenom());
        } else {
            System.out.println("Authentification échouée pour cet employé.");
        }
        return employe;
    }
    
        private static void testInitialiser() throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Initialiser ---" + RESET);
        
        Service service = new Service();

        System.out.println("Initialiser à fonctionné : " + service.initialiser());
        

    }
        
    private static void testListerEmploye() {
        System.out.println(FG_GREEN + "--- Testing Lister Employe ---" + RESET);

        Service service = new Service();

        System.out.println(FG_YELLOW + "--- Listing Employes ---" + RESET);
        List<Employe> listeEmploye = service.listerEmploye();
        if (listeEmploye != null && !listeEmploye.isEmpty()) {
            listeEmploye.forEach((employe) -> {
                System.out.println("Nom: " + employe.getNom() + ", Prénom: " + employe.getPrenom());
            });
        } 
        else {
            System.out.println("Aucun employé trouvé.");
        }
    }
        
    private static void testListerMedium() {
        System.out.println(FG_GREEN + "--- Testing Lister Medium ---" + RESET);

        Service service = new Service();


        System.out.println(FG_YELLOW + "--- Listing Mediums ---" + RESET);
        List<Medium> listeMedium = service.listerMedium();
        if (listeMedium != null && !listeMedium.isEmpty()) {
            listeMedium.forEach((medium) -> {
                System.out.println("Nom: " + medium.getNom());
            });
        } else {
            System.out.println("Aucun médium trouvé.");
        }
    }
    
    private static void testListerClient() {
        System.out.println(FG_GREEN + "--- Testing Lister Client ---" + RESET);

        Service service = new Service();


        System.out.println(FG_YELLOW + "--- Listing Clients ---" + RESET);
        List<Client> listeClient = service.listerClient();
        if (listeClient != null && !listeClient.isEmpty()) {
            listeClient.forEach((client) -> {
                System.out.println("Nom: " + client.getNom() + ", Email: " + client.getMail());
            });
        } else {
            System.out.println("Aucun client trouvé.");
        }
    }
    
    private static void testListerConsultation() {
        System.out.println(FG_GREEN + "--- Testing Lister Consultation ---" + RESET);

        Service service = new Service();


        System.out.println(FG_YELLOW + "--- Listing Consultations---" + RESET);
        List<Consultation> listeConsultation = service.listerConsultation();
        if (listeConsultation != null && !listeConsultation.isEmpty()) {
            listeConsultation.forEach((consultation) -> {
                System.out.println(consultation);
            });
        } else {
            System.out.println("Aucune consultation");
        }
    }
        
    private static Consultation testCreerConsultation(Client client, Medium medium) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Creer Consultation ---" + RESET);

        Service service = new Service();
        
            Consultation consultation = service.creerConsultation(client, medium);
            
            System.out.println(consultation);

            System.out.println("Consultation créée avec succès pour le client : " 
                               + consultation.getClient()
                               + " avec le médium : " + consultation.getMedium() + "et l'employé : " + consultation.getEmploye());
            return consultation;
    }
    
    
    
    private static Consultation testCommencerConsultation(Consultation consultation) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Commencer Consultation ---" + RESET);

        Service service = new Service();
        
        consultation = service.commencerConsultation(consultation);

        System.out.println("La consultation a commencé : " + consultation);
        return consultation;

    }
    
    private static Consultation testTerminerConsultation(Consultation consultation) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Terminer Consultation ---" + RESET);

        Service service = new Service();
        
        consultation = service.terminerConsultation(consultation);

        System.out.println("La consultation a durée à : " + consultation.getDuree());
        System.out.println(consultation);
        return consultation;

    }
    
    private static void testRechercheHistoriqueMedium() throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Recherche Historique Medium ---" + RESET);

        Service service = new Service();
        
        Medium medium = service.trouverMediumParId(new Long("25"));
        
        List<Consultation> historique = service.rechercheHistoriqueMedium(medium);
        
        historique.forEach((consultation) -> {
            System.out.println("Consultation pour le client : " 
                               + consultation.getClient()
                               + " avec le médium : " + consultation.getMedium() + "et l'employé : " + consultation.getEmploye());
        });

    }
    
    private static void testRechercheTopMediumPourClient() throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Recherche Top Medium Pour Client ---" + RESET);

        Service service = new Service();

        Client client = service.trouverClientParId(new Long("1")); 

        List<Object[]> topMediums = service.rechercheTopMediumPourClient(client);


        System.out.println("Top médiums pour le client " + client.getNom() + " " + client.getPrenom() + ":");
        topMediums.forEach((result) -> {
            Medium medium = (Medium) result[0]; 
            Long count = (Long) result[1];  
            System.out.println("Medium: " + medium.getNom() + ", Nombre de consultations: " + count);
        });
    }
    
    
    private static Consultation testAjouterCommentaire(Consultation consultation) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Ajouter Commentaire ---" + RESET);

        Service service = new Service();
        
        
        System.out.println("Commentaire de la consultation" + service.ajouterCommentaire(consultation, "YOUPIPIPIPIPIPIDJVBIH").getCommentaire());
        
        return consultation;
        
    }
    
    private static void testObtenirPrediction(Client client) throws ParseException {
        System.out.println(FG_GREEN + "--- Testing Obtenir Prediction ---" + RESET);

        Service service = new Service();

        String couleur = client.getProfil().getPorte_bonheur();
        String animal = client.getProfil().getAnimal();
        
        int amour = 2;
        int sante = 3;
        int travail = 4;


        List<String> predictions = service.obtenirPrediction(couleur, animal, amour, sante, travail);


        System.out.println("Predictions obtenues :");
        predictions.forEach((prediction) -> {
            System.out.println(" - " + prediction);
        });
    }
    private static Consultation testObtenirConsultationActuelle(Client client) {
        System.out.println(FG_GREEN + "--- Testing Obtenir Consultation Actuelle (client) ---" + RESET);

        Service service = new Service();

        Consultation consultation = service.obtenirConsultationActuelle(client);

        System.out.println("Consultation actuelle trouvée :");
        System.out.println(consultation);
        return consultation;

    }    

    private static Consultation testObtenirConsultationActuelle(Employe employe) {
        System.out.println(FG_GREEN + "--- Testing Obtenir Consultation Actuelle (employe) ---" + RESET);

        Service service = new Service();

        Consultation consultation = service.obtenirConsultationActuelle(employe);

        System.out.println("Consultation actuelle trouvée :");
        System.out.println(consultation);
        return consultation;

    }       
    private static void testListerMediumNombreClient() {
        System.out.println(FG_GREEN + "--- Testing Lister Medium Nombre Client ---" + RESET);

        Service service = new Service();

        List<Object[]> resultats = service.listerMediumNombreClient();

        
            System.out.println("Nombre de clients par médium :");
            resultats.forEach((ligne) -> {
                Medium medium = (Medium) ligne[0];
                Long nombreClients = (Long) ligne[1];
                System.out.println("- " + medium.getNom() + " : " + nombreClients + " client(s)");
        });
    }
    
    private static void testListerEmployeNombreClient() {
        System.out.println(FG_GREEN + "--- Testing Lister Employé Nombre Client ---" + RESET);

        Service service = new Service();

        List<Object[]> resultats = service.listerEmployeNombreClient();

        System.out.println("Nombre de clients par employé :");
        resultats.forEach((ligne) -> {
            Employe employe = (Employe) ligne[0];
            Long nombreClients = (Long) ligne[1];
            System.out.println("- " + employe.getPrenom() + " " + employe.getNom() + " : " + nombreClients + " client(s)");
        });
    }

    
    public static final String FG_BLACK = "\u001b[30m";
    public static final String FG_BLUE = "\u001b[34m";
    public static final String FG_CYAN = "\u001b[36m";
    public static final String FG_GREEN = "\u001b[32m";
    public static final String FG_MAGENTA = "\u001b[35m";
    public static final String FG_RED = "\u001b[31m";
    public static final String FG_WHITE = "\u001b[37m";
    public static final String FG_YELLOW = "\u001b[33m";

    public static final String BG_BLACK = "\u001b[40m";
    public static final String BG_BLUE = "\u001b[44m";
    public static final String BG_CYAN = "\u001b[46m";
    public static final String BG_GREEN = "\u001b[42m";
    public static final String BG_MAGENTA = "\u001b[45m";
    public static final String BG_RED = "\u001b[41m";
    public static final String BG_WHITE = "\u001b[47m";
    public static final String BG_YELLOW = "\u001b[43m";

    public static final String RESET = "\u001B[0m";
    
}
