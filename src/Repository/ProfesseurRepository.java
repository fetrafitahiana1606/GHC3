package Repository;

import Entity.Professeur;
import com.wiki.mg.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfesseurRepository extends Professeur {

    public ProfesseurRepository(String matricule, String nom, int tauxhoraire) {
        super(matricule, nom, tauxhoraire);
    }

    public static void main(String[] args) throws Exception {

//        ProfesseurRepository test = new ProfesseurRepository("", "", 0);
//        Collections.sort(test.recherche(test.matricule));
//        for (String i : test.recherche(test.getMatricule())) {
//            System.out.println(i);
//        }
    }

    public static ObservableList<Professeur> afficher() throws Exception {
        Statement stmt;
        ResultSet res = null;
        ObservableList<Professeur> professeurs;
        professeurs = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT * FROM `professeur`";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);

                while (res.next()) {
                    professeurs.add(new ProfesseurRepository(res.getString("matricule"),
                            res.getString("nom"),
                            res.getInt("tauxhoraire")));
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
        return professeurs;
    }

    public static ObservableList<Professeur> recherche(String search) throws Exception {
        Statement stmt;
        ResultSet res = null;
        ObservableList<Professeur> professeurs;
        professeurs = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT * FROM `professeur` WHERE `professeur`.`matricule` LIKE '%" + search + "%' OR `professeur`.`nom` LIKE '%" + search + "%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    professeurs.add(new ProfesseurRepository(res.getString("matricule"),
                            res.getString("nom"),
                            res.getInt("tauxhoraire")));
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
        return professeurs;
    }

    public void ajout() throws Exception {
        PreparedStatement ps = null;

        var cnx = DataBase.connexionDB();
        String query = "INSERT INTO `professeur`(`matricule`, `nom`, `tauxhoraire`) VALUES (?,?,?)";
        try {
            if (cnx != null) {
                ps = cnx.prepareStatement(query);
                ps.setString(1, this.matricule);
                ps.setString(2, this.nom);
                ps.setInt(3, this.tauxhoraire);
                ps.execute();
            }

        } catch (Exception e) {
            System.out.println("--> Exception : " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("--> Exception : " + e);
            }
        }
    }

    public boolean exist(Professeur search) {
        Statement stmt;
        ResultSet res = null;

        var cnx = DataBase.connexionDB();
        boolean tmp = false;
        String query = "SELECT * FROM `professeur` WHERE `professeur`.`matricule` LIKE '%"+search.getMatricule()+"%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    if (res.getString("matricule").equals(search.getMatricule())==true ){
                        tmp = true;
                    }
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
        return tmp;
    }

    public void modifier() throws Exception {
        PreparedStatement ps = null;

        var cnx = DataBase.connexionDB();
        String query = new StringBuilder().append("UPDATE `professeur` SET `nom` = '")
                .append(this.nom).append("', `tauxhoraire` = '")
                .append(this.tauxhoraire).append("' WHERE `professeur`.`matricule` = '")
                .append(this.matricule).append("'")
                .toString();
        try {
            if (cnx != null) {
                ps = cnx.prepareStatement(query);
                ps.execute();
            }

        } catch (Exception e) {
            System.out.println("--> Exception : " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                System.out.println("--> Exception : " + e);
            }
        }
    }

    public void supprimer() throws Exception {
        PreparedStatement ps = null;

        var cnx = DataBase.connexionDB();
        String query = "DELETE FROM `professeur` WHERE `professeur`.`matricule` =?";
        try {
            if (cnx != null) {
                ps = cnx.prepareStatement(query);
                ps.setString(1, this.matricule);
                ps.execute();
            }

        } catch (Exception e) {
            System.out.println("--> Exception : " + e);
        } finally {
            try {
                ps.close();
            } catch (Exception e) {
                System.out.println("--> Exception : " + e);
            }
        }
    }
}