package Repository;

import Entity.Matiere;
import com.wiki.mg.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public final class MatiereRepository extends Matiere {

    public MatiereRepository(String num_mat, String designation, int nbheure) {
        super(num_mat, designation, nbheure);
    }

    public void ajout() {
        PreparedStatement ps = null;

        var cnx = DataBase.connexionDB();
        String query = "INSERT INTO `matiere`(`num_mat`,`designation`, `nbheure`) VALUES (?,?,?)";
        try {
            if (cnx != null) {
                ps = cnx.prepareStatement(query);
                ps.setString(1, this.num_mat);
                ps.setString(2, this.designation);
                ps.setInt(3, this.nbheure);
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
    public boolean exist(Matiere search) {
        Statement stmt;
        ResultSet res = null;

        var cnx = DataBase.connexionDB();
        boolean tmp = false;
        String query = "SELECT * FROM `matiere` WHERE `matiere`.`num_mat` LIKE '%"+search.getNum_mat()+"%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    if (res.getString("num_mat").equals(search.getNum_mat())){
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
    public void modifier() {

    }

    public void supprimer() {
        PreparedStatement ps = null;

        var cnx = DataBase.connexionDB();
        String query = "DELETE FROM `matiere` WHERE `matiere`.`num_mat` =?";
        try {
            if (cnx != null) {
                ps = cnx.prepareStatement(query);
                ps.setString(1, this.num_mat);
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


    public static ObservableList<Matiere> afficher() {
        Statement stmt;
        ResultSet res = null;
        ObservableList<Matiere> matieres;
        matieres = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT * FROM `matiere`";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);

                while (res.next()) {
                   matieres.add(new MatiereRepository(res.getString("num_mat"),
                            res.getString("designation"),
                            res.getInt("nbheure")));
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
        return matieres;
    }


    public static ObservableList recherche(String search) throws Exception {
        Statement stmt;
        ResultSet res = null;
        ObservableList<Matiere> matieres;
        matieres = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT * FROM `matiere` WHERE `matiere`.`num_mat` LIKE '%" + search + "%' OR `matiere`.`designation` LIKE '%" + search + "%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    matieres.add(new MatiereRepository(res.getString("num_mat"),
                            res.getString("designation"),
                            res.getInt("nbheure")));
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
        return matieres;
    }

}
