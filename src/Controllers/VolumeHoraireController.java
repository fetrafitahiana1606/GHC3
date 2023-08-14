package Controllers;

import Entity.VolumeHoraire;
import Repository.VolumeHoraireRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class VolumeHoraireController implements Initializable {
    @FXML
    private Button btn_search;

    @FXML
    private TextField input_search;

    @FXML
    private Button btn_add;

    @FXML
    private TableColumn<VolumeHoraire, String> cell_num_mat;

    @FXML
    private TextField input_annee;

    @FXML
    private TextField input_num_mat;

    @FXML
    private TableView<VolumeHoraire> table_volumehoraire;

    @FXML
    private TableColumn<VolumeHoraire, Integer> cell_annee;

    @FXML
    private Button btn_delete;

    @FXML
    private TextField input_matricule;

    @FXML
    private Button btn_update;

    @FXML
    private TableColumn<VolumeHoraire, String> cell_matricule;

    @FXML
    void table_volumehoraire_clicked() {
        if (table_volumehoraire.getSelectionModel().getSelectedItem() != null) {

            VolumeHoraireRepository vh = (VolumeHoraireRepository) table_volumehoraire.getSelectionModel().getSelectedItem();
            input_matricule.setText(vh.getMatricule());
            input_matricule.setDisable(true);
            input_num_mat.setText(vh.getNum_mat());
            input_annee.setText(String.valueOf(vh.getAnnee()));

        }
    }

    @FXML
    void btn_search_clicked() throws Exception {
        ObservableList<VolumeHoraire> vh;
        vh = VolumeHoraireRepository.recherche(input_search.getText());
        if (vh.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Aucun VolumeHoraire trouvé avec " + input_search.getText() + "",
                    javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        } else {
            cell_matricule.setCellValueFactory(new PropertyValueFactory<VolumeHoraire, String>("matricule"));
            cell_num_mat.setCellValueFactory(new PropertyValueFactory<VolumeHoraire, String>("num_mat"));
            cell_annee.setCellValueFactory(new PropertyValueFactory<VolumeHoraire, Integer>("annee"));
            table_volumehoraire.setItems(vh);
        }
    }

    @FXML
    void btn_add_clicked() throws Exception {
        if (input_matricule.getText() == "" || input_num_mat.getText() == "" || input_annee.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir correctement les champs", javafx.scene.control.ButtonType.OK);
            alert.showAndWait();

        } else {
            VolumeHoraireRepository vh = new VolumeHoraireRepository(input_matricule.getText(),
                    input_num_mat.getText(),
                    Integer.parseInt(input_annee.getText()));
            var tmp = vh.exist(vh);
//            System.out.println(tmp);
            if (tmp == true) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Volume horaire déjà existant", ButtonType.CLOSE);
                alert.showAndWait();
            }else {
                vh.ajout();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Volume horaire enregistrer avec succès", javafx.scene.control.ButtonType.OK);
                alert.showAndWait();
                showVolumeHoraire();
            }


        }

    }

    @FXML
    void btn_update_clicked(MouseEvent event) {

    }

    @FXML
    void btn_delete_clicked(MouseEvent event) {

    }
    void showVolumeHoraire () throws Exception {
        var vh = VolumeHoraireRepository.afficher();
        input_matricule.setText("");
        input_num_mat.setText("");
        input_annee.setText("");
        cell_matricule.setCellValueFactory(new PropertyValueFactory<VolumeHoraire, String>("matricule"));
        cell_num_mat.setCellValueFactory(new PropertyValueFactory<VolumeHoraire, String>("num_mat"));
        cell_annee.setCellValueFactory(new PropertyValueFactory<VolumeHoraire, Integer>("annee"));
        table_volumehoraire.setItems(vh);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showVolumeHoraire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
