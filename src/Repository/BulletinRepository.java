package Repository;

import Entity.Bulletin;
import com.wiki.mg.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BulletinRepository extends Bulletin {
    public BulletinRepository(String designation, int nbheure, int montant) {
        super(designation, nbheure, montant);
    }

    public static ObservableList<Bulletin> afficher(TextField inputM, TextField inputA) {
        Statement stmt;
        ResultSet res = null;
        ObservableList<Bulletin> bulletin = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT DISTINCT matiere.DESIGNATION AS designation, matiere.NBHEURE as nbheure, " +
                "matiere.NBHEURE*professeur.TAUXHORAIRE AS montant FROM matiere, volumehoraire, professeur " +
                "WHERE volumehoraire.MATRICULE="+inputM.getText() +" AND volumehoraire.NUM_MAT=matiere.NUM_MAT AND " +
                "professeur.TAUXHORAIRE IN (SELECT professeur.TAUXHORAIRE FROM professeur WHERE professeur.MATRICULE="+
                inputM.getText() +") AND volumehoraire.ANNEE="+ inputA.getText();
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    bulletin.add(new Bulletin(res.getString("designation"),
                            res.getInt("nbheure"), res.getInt("montant")));
                }
            }
        } catch (Exception e) {
            System.out.println("--> Exception : " + e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                System.out.println("--> Exception : " + e);
            }
        }
        return bulletin;
    }
    public static ArrayList<String> total (TextField inputM, TextField inputA) {
        Statement stmt;
        ResultSet res = null;
        ArrayList<String> info = new ArrayList();

        var cnx = DataBase.connexionDB();
        //requete pour recuperer la totalite de lapaie d'un professeur en une annee;
        String query = "SELECT DISTINCT SUM(matiere.NBHEURE)*professeur.TAUXHORAIRE AS total FROM matiere,volumehoraire,professeur " +
                "WHERE volumehoraire.MATRICULE="+inputM.getText() +" AND volumehoraire.NUM_MAT=matiere.NUM_MAT AND " +
                "professeur.TAUXHORAIRE IN (SELECT professeur.TAUXHORAIRE FROM professeur WHERE professeur.MATRICULE="+
                inputM.getText() +") AND volumehoraire.ANNEE="+ inputA.getText() + " GROUP BY professeur.MATRICULE";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    info.add(String.valueOf(res.getInt("total")));
                }
            }
        } catch (Exception e) {
            System.out.println("--> Exception : " + e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                System.out.println("--> Exception : " + e);
            }
        }
        return info;
    }

    public static ArrayList<String> nom (TextField inputM) {
        Statement stmt;
        ResultSet res = null;
        ArrayList<String> nom = new ArrayList();
//        ObservableList<Bulletin> bulletin = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT professeur.NOM as nom FROM professeur WHERE professeur.MATRICULE = "+inputM.getText();
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    nom.add(res.getString("nom"));
                }
            }
        } catch (Exception e) {
            System.out.println("--> Exception : " + e);
        } finally {
            try {
                res.close();
            } catch (Exception e) {
                System.out.println("--> Exception : " + e);
            }
        }
        return nom;
    }
}
