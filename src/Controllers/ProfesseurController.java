package Controllers;

import Entity.Professeur;
import Repository.ProfesseurRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfesseurController implements Initializable {
    @FXML
    private Button btn_search;

    @FXML
    private TextField input_search;

    @FXML
    private Button btn_add;

    @FXML
    private TextField input_nom;

    @FXML
    private TableColumn<Professeur, Integer> cell_tauxhoraire;

    @FXML
    private Button btn_delete;

    @FXML
    private TextField input_matricule;

    @FXML
    private TableColumn<Professeur, String> cell_nom;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<Professeur, String> cell_matricule;

    @FXML
    private TextField input_tauxhoraire;

    @FXML
    private TableView<Professeur> table_professeur;

    @FXML
    void btn_search_clicked() throws Exception {
        ObservableList<Professeur> prof;
        prof = ProfesseurRepository.recherche(input_search.getText());
        if (prof.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Aucun professeur trouvé avec " + input_search.getText() + "",
                    javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        } else {
            cell_matricule.setCellValueFactory(new PropertyValueFactory<Professeur, String>("matricule"));
            cell_nom.setCellValueFactory(new PropertyValueFactory<Professeur, String>("nom"));
            cell_tauxhoraire.setCellValueFactory(new PropertyValueFactory<Professeur, Integer>("tauxhoraire"));
            table_professeur.setItems(prof);
        }

    }

    @FXML
    void btn_add_clicked() throws Exception {
        if (input_matricule.getText().equals("") || input_nom.getText().equals("") || input_tauxhoraire.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir correctement les champs", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        } else {
            if (isMatricule(input_matricule, input_matricule.getText())==true &&
                isNom(input_nom, input_nom.getText())==true &&
                isTauxHoraire(input_tauxhoraire, input_tauxhoraire.getText())==true){
                ProfesseurRepository prof = new ProfesseurRepository(input_matricule.getText(),
                        input_nom.getText(), Integer.parseInt(input_tauxhoraire.getText()));
                var tmp = prof.exist(prof);
//            System.out.println(tmp);
                if (tmp == true) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Professeur déjà existant", ButtonType.CLOSE);
                    alert.showAndWait();
                }else {
                    prof.ajout();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Professeur enregistrer avec succès", javafx.scene.control.ButtonType.OK);
                    alert.showAndWait();
                    showProfesseur();
                }
            }
        }


    }

    @FXML
    void btn_update_clicked() throws Exception {
        if (table_professeur.getSelectionModel().getSelectedItem() != null && !input_matricule.getText().equals("") ||
                !input_nom.getText().equals("") || !input_tauxhoraire.getText().equals("")) {
            ProfesseurRepository prof = new ProfesseurRepository(input_matricule.getText(),
                    input_nom.getText(), Integer.parseInt(input_tauxhoraire.getText()));
            if (isMatricule(input_matricule, input_matricule.getText())==true &&
                    isNom(input_nom, input_nom.getText())==true &&
                    isTauxHoraire(input_tauxhoraire, input_tauxhoraire.getText())==true){
                prof.modifier();
                showProfesseur();
            }


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez selectionner une ligne !", ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    void btn_delete_clicked() throws Exception {
        if (table_professeur.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous vraiment poursuivre l'action ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult()!=ButtonType.NO) {
                ProfesseurRepository prof = (ProfesseurRepository) table_professeur.getSelectionModel().getSelectedItem();
                prof.supprimer();
                showProfesseur();
            } else {
                showProfesseur();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez selectionner une ligne !", ButtonType.OK);
            alert.showAndWait();
        }


    }

    void showProfesseur() throws Exception {
        var prof = ProfesseurRepository.afficher();
        input_matricule.setText("");
        input_nom.setText("");
        input_tauxhoraire.setText("");
        cell_matricule.setCellValueFactory(new PropertyValueFactory<Professeur, String>("matricule"));
        cell_nom.setCellValueFactory(new PropertyValueFactory<Professeur, String>("nom"));
        cell_tauxhoraire.setCellValueFactory(new PropertyValueFactory<Professeur, Integer>("tauxhoraire"));
        table_professeur.setItems(prof);

    }

    @FXML
    void table_professeur_clicked() {
        if (table_professeur.getSelectionModel().getSelectedItem() != null) {
            ProfesseurRepository prof = (ProfesseurRepository) table_professeur.getSelectionModel().getSelectedItem();
            input_matricule.setText(prof.getMatricule());
            input_matricule.setDisable(true);
            input_nom.setText(prof.getNom());
            input_tauxhoraire.setText(String.valueOf(prof.getTauxhoraire()));
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

    private boolean isNom (TextField input, String message) {
        try {

            if (3 <= input.getLength()){
                int tmp = Integer.parseInt(input.getText());
//                return true;
            }
            else {

                Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas correct, au moins 2 caractères!");
                alert.showAndWait();
                return false;
            }

        } catch (NumberFormatException e) {
            return true;

        }
        Alert alert = new Alert(Alert.AlertType.ERROR, message + " ne peut etre un nom valide !");
        alert.showAndWait();
        return false;
    }

    private boolean isTauxHoraire (TextField input, String message) {
        try {
            int tmp = Integer.parseInt(input.getText());
            if (4<=input.getLength()){return true;}
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, message + "au moins au format de 4 chiffres");
                alert.showAndWait();
                return false;
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message + " n'est pas un entier", ButtonType.CLOSE);
            alert.showAndWait();

        }
        return false;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showProfesseur();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
