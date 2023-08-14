package Controllers;

import Repository.BulletinRepository;
import com.wiki.mg.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StatistiquesController implements Initializable {

    @FXML
    private TextField input_annee;

    @FXML
    private TextField input_total;

    @FXML
    private Label label_total;

    @FXML
    private TextField input_matricule;

    @FXML
    private PieChart pie_chart;

    @FXML
    private Button btn_show;

    @FXML
    private Button btn_print;

    @FXML
    void showBulletin() {
        if (input_matricule.getText() == "" || input_annee.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir correctement les champs",
                    ButtonType.OK);
            alert.showAndWait();
        } else {
            if (isMatricule(input_matricule, input_matricule.getText()) &&
                    isAnnee(input_annee, input_annee.getText())) {
                if (exist(input_matricule, input_matricule.getText())) {
                    var total = BulletinRepository.total(input_matricule, input_annee);
                    input_total.setText(total.get(0));
                    var nom = BulletinRepository.nom(input_matricule);
//                    input_nom.setText(nom.get(0));
                    var bull =  BulletinRepository.afficher(input_matricule, input_annee);
//                    cell_designation.setCellValueFactory(new PropertyValueFactory<BulletinRepository, String>("designation"));
//                    cell_nbheure.setCellValueFactory(new PropertyValueFactory<BulletinRepository, Integer>("nbheure"));
//                    cell_montant.setCellValueFactory(new PropertyValueFactory<BulletinRepository, Integer>("montant"));
//                    table_bulletin.setItems(bull);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Matricule non existant, veuillez un matricule existant");
                    alert.showAndWait();
                }
            }

        }
    }
    private boolean isMatricule (TextField input, String message) {
        try {
            int tmp = Integer.parseInt(input.getText());
            if (input.getLength()==4){return true;}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas au format de 4 chiffres");
                alert.showAndWait();
                return false;
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas un entier", ButtonType.CLOSE);
            alert.showAndWait();

        }
        return false;
    }
    private boolean  isAnnee (TextField input, String message) {
        try {
            int tmp = Integer.parseInt(input.getText());
            if (input.getLength()==4){return true;}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas au format de 4 chiffres");
                alert.showAndWait();
                return false;
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas un entier", ButtonType.CLOSE);
            alert.showAndWait();

        }
        return false;
    }
    public boolean exist(TextField input, String text) {
        Statement stmt;
        ResultSet res = null;

        var cnx = DataBase.connexionDB();
        boolean tmp = false;
        String query = "SELECT * FROM `professeur` WHERE `professeur`.`matricule` LIKE '%"+input.getText()+"%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    if (res.getString("matricule").equals(input.getText())==true ){
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
