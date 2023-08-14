package Repository;

import Entity.VolumeHoraire;
import com.wiki.mg.DataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public final class VolumeHoraireRepository extends VolumeHoraire {
    public VolumeHoraireRepository(String matricule, String num_mat, int annee) {
        super(matricule, num_mat, annee);
    }

    public static ObservableList<VolumeHoraire> afficher() throws Exception {
        Statement stmt;
        ResultSet res = null;
        ObservableList<VolumeHoraire> volumehaires;
        volumehaires = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = "SELECT * FROM `volumehoraire`";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);

                while (res.next()) {
                    volumehaires.add(new VolumeHoraireRepository(res.getString("matricule"),
                            res.getString("num_mat"),
                            res.getInt("annee")));
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
        return volumehaires;
    }

    public static ObservableList<VolumeHoraire> recherche(String search) throws Exception {
        Statement stmt;
        ResultSet res = null;
        ObservableList<VolumeHoraire> volumehoraires;
        volumehoraires = FXCollections.observableArrayList();

        var cnx = DataBase.connexionDB();
        String query = new StringBuilder().append("SELECT * FROM `volumehoraire` WHERE `volumehoraire`.`matricule` LIKE '%")
                .append(search).append("%' OR `volumehoraire`.`num_mat` LIKE '%")
                .append(search).append("%' OR `volumehoraire`.`annee` LIKE '%")
                .append(search).append("%'").toString();
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    volumehoraires.add(new VolumeHoraireRepository(res.getString("matricule"),
                            res.getString("num_mat"),
                            res.getInt("annee")));
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
        return volumehoraires;
    }

    public void ajout() throws Exception {
        PreparedStatement ps = null;

        var cnx = DataBase.connexionDB();
        String query = "INSERT INTO `volumehoraire`(`matricule`, `num_mat`, `annee`) VALUES (?,?,?)";
        try {
            if (cnx != null) {
                ps = cnx.prepareStatement(query);
                ps.setString(1, this.matricule);
                ps.setString(2, this.num_mat);
                ps.setInt(3, this.annee);
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

    public boolean exist(VolumeHoraire search) {
        Statement stmt;
        ResultSet res = null;

        var cnx = DataBase.connexionDB();
        boolean tmp = false;
        String query = "SELECT * FROM `volumehoraire` WHERE `volumehoraire`.`matricule` LIKE '%"+search.getMatricule()+"%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    if (res.getString("matricule").equals(search.getMatricule())==true &&
                            res.getString("num_mat").equals(search.getNum_mat())==true &&
                            res.getInt("annee")==search.getAnnee()){
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

    }


    public void supprimer() throws Exception {

    }
}
