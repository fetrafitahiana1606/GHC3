package Repository;

import Entity.HeureComplementaire;
import com.wiki.mg.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HeureComplementaireRepository extends HeureComplementaire {
    public HeureComplementaireRepository(String matricule, String nom, int montant) {
        super(matricule, nom, montant);
    }

    public static ObservableList<HeureComplementaire> afficher(TextField input) {
        if (input.getText().isEmpty()){
            input.setText("2022");
        }
        Statement stmt;
        ResultSet res = null;
        ObservableList<HeureComplementaire> heureComplementaires;
        heureComplementaires = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT professeur.MATRICULE AS matricule, professeur.NOM AS nom, " +
                "SUM(matiere.NBHEURE)*professeur.TAUXHORAIRE AS montant FROM matiere, volumehoraire, professeur WHERE " +
                "volumehoraire.NUM_MAT=matiere.NUM_MAT AND professeur.TAUXHORAIRE IN (SELECT professeur.TAUXHORAIRE FROM " +
                "professeur WHERE professeur.MATRICULE=volumehoraire.MATRICULE) AND volumehoraire.ANNEE="+input.getText()+
                " GROUP BY professeur.MATRICULE";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);

                while (res.next()) {
                    heureComplementaires.add(new HeureComplementaireRepository(res.getString("matricule"),
                            res.getString("nom"),
                            res.getInt("montant")));
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
        return heureComplementaires;
    }

    public static ArrayList<String> total (TextField input) {
        if (input.getText().isEmpty()){
            input.setText("2022");
        }
        Statement stmt;
        ResultSet res = null;
        ArrayList<String> tot = new ArrayList();
//        ObservableList<Bulletin> bulletin = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT SUM(matiere.NBHEURE*professeur.TAUXHORAIRE) AS total FROM matiere, volumehoraire, professeur WHERE volumehoraire.NUM_MAT=matiere.NUM_MAT AND professeur.TAUXHORAIRE IN (SELECT professeur.TAUXHORAIRE FROM professeur WHERE professeur.MATRICULE=volumehoraire.MATRICULE) AND volumehoraire.ANNEE="+input.getText();
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    tot.add(String.valueOf(res.getInt("total")));
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
        return tot;
    }
}
