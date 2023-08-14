package Controllers;

import Entity.Matiere;
import Repository.MatiereRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MatiereController implements Initializable {
    @FXML
    private TableColumn<Matiere, String> cell_designation;

    @FXML
    private Button btn_search;

    @FXML
    private TextField input_search;

    @FXML
    private Button btn_add;

    @FXML
    private TableColumn<Matiere, String> cell_num_mat;

    @FXML
    private TableColumn<Matiere, Integer> cell_nbheure;

    @FXML
    private TextField input_num_mat;

    @FXML
    private TextField input_nbheure;

    @FXML
    private TableView<Matiere> table_matiere;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private TextField input_designation;

    @FXML
    void table_matiere_clicked() {
        if (table_matiere.getSelectionModel().getSelectedItem() != null) {
           MatiereRepository mat = (MatiereRepository) table_matiere.getSelectionModel().getSelectedItem();
            input_num_mat.setText(mat.getNum_mat());
            input_num_mat.setDisable(true);
            input_designation.setText(mat.getDesignation());
            input_nbheure.setText(String.valueOf(mat.getNbheure()));
        }
    }

    @FXML
    void btn_search_clicked() throws Exception {
        ObservableList<Matiere> mat;
        mat = MatiereRepository.recherche(input_search.getText());
        if (mat.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Aucune matière trouvé avec " + input_search.getText() + "",
                    javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        } else {
            cell_num_mat.setCellValueFactory(new PropertyValueFactory<Matiere, String>("num_mat"));
            cell_designation.setCellValueFactory(new PropertyValueFactory<Matiere, String>("designation"));
            cell_nbheure.setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("nbheure"));
            table_matiere.setItems(mat);
        }

    }

    @FXML
    void btn_add_clicked() throws Exception {
        if (input_num_mat.getText() == "" || input_designation.getText() == "" || input_nbheure.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir correctement les champs",
                    javafx.scene.control.ButtonType.OK);
            alert.showAndWait();
        } else {
            if (isCodeMatiere(input_num_mat, input_num_mat.getText())==true &&
                    isNbHeure(input_nbheure, input_nbheure.getText())==true &&
                    isDesignation(input_designation, input_designation.getText())==true) {
                MatiereRepository mat = new MatiereRepository(input_num_mat.getText(),
                        input_designation.getText(), Integer.valueOf(input_nbheure.getText()));
                var tmp = mat.exist(mat);
//            System.out.println(tmp);
                if (tmp == true) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Matière déjà existante", ButtonType.CLOSE);
                    alert.showAndWait();
                }else {
                    mat.ajout();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Matière enregistrer avec succès", javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                    showMatiere();
                }
            }

        }
    }

    @FXML
    void btn_update_clicked() throws Exception {
        if (table_matiere.getSelectionModel().getSelectedItem() != null && input_num_mat.getText() != "" ||
                input_designation.getText() != "" || input_nbheure.getText() != "") {
            MatiereRepository mat = new MatiereRepository(input_num_mat.getText(),
                    input_designation.getText(), Integer.valueOf(input_nbheure.getText()));
            if (isCodeMatiere(input_num_mat, input_num_mat.getText())==true &&
                    isNbHeure(input_nbheure, input_nbheure.getText())==true &&
                    isDesignation(input_designation, input_designation.getText())==true) {
                mat.modifier();
                showMatiere();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez selectionner une ligne !", ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    void btn_delete_clicked() throws Exception {
        if (table_matiere.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment poursuivre l'action ?",
                    ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult()!=ButtonType.NO) {
                MatiereRepository mat = (MatiereRepository) table_matiere.getSelectionModel().getSelectedItem();
                mat.supprimer();
                showMatiere();
            } else {
                showMatiere();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez selectionner une ligne !", ButtonType.OK);
            alert.showAndWait();
        }
    }
    void showMatiere () throws Exception {
        var mat = MatiereRepository.afficher();
        input_num_mat.setText("");
        input_designation.setText("");
        input_nbheure.setText("");
        cell_num_mat.setCellValueFactory(new PropertyValueFactory<Matiere, String>("num_mat"));
        cell_designation.setCellValueFactory(new PropertyValueFactory<Matiere, String>("designation"));
        cell_nbheure.setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("nbheure"));
        table_matiere.setItems(mat);
    }

    private boolean isCodeMatiere (TextField input, String message) {
        try {
            int tmp = Integer.parseInt(input.getText());
            if (input.getLength()==3){return true;}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas au format de 3 chiffres");
                alert.showAndWait();
                return false;
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas un entier", ButtonType.CLOSE);
            alert.showAndWait();

        }
        return false;
    }

    private boolean isDesignation (TextField input, String message) {
        try {

            if (3 <= input.getLength()){
                int tmp = Integer.parseInt(input.getText());
//                return true;
            }
            else {

                Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas correct, au moins 3 caractères!");
                alert.showAndWait();
                return false;
            }

        } catch (NumberFormatException e) {
            return true;

        }
        Alert alert = new Alert(Alert.AlertType.ERROR, message + " ne peut etre une désignation");
        alert.showAndWait();
        return false;

    }

    private boolean isNbHeure (TextField input, String message) {
        try {
            int tmp = Integer.parseInt(input.getText());
            if (tmp<200){
                return true;
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, message + " depasse les 200 Heures", ButtonType.CLOSE);
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas un entier ", ButtonType.CLOSE);
            alert.showAndWait();

        }
        return false;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showMatiere();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
