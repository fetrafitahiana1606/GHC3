package Controllers;

import Entity.HeureComplementaire;
import Repository.BulletinRepository;
import Repository.HeureComplementaireRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HeureComplementaireController implements Initializable {
    @FXML
    private TextField input_annee;

    @FXML
    private TextField input_total;

    @FXML
    private Label label_total;

    @FXML
    private Button btn_show;

    @FXML
    private TableView<HeureComplementaire> table_HeureComp;

    @FXML
    private TableColumn<BulletinRepository, Integer> cell_nom;

    @FXML
    private Button btn_print;

    @FXML
    private TableColumn<HeureComplementaireRepository, String> cell_matricule;

    @FXML
    private TableColumn<BulletinRepository, Integer> cell_montant;

    public void showHeureComp() {
        var hc =  HeureComplementaireRepository.afficher(input_annee);
        cell_matricule.setCellValueFactory(new PropertyValueFactory<HeureComplementaireRepository, String>("matricule"));
        cell_nom.setCellValueFactory(new PropertyValueFactory<BulletinRepository, Integer>("nom"));
        cell_montant.setCellValueFactory(new PropertyValueFactory<BulletinRepository, Integer>("montant"));
        table_HeureComp.setItems(hc);
        var total = HeureComplementaireRepository.total(input_total);
        input_total.setText(total.get(0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showHeureComp();

    }
}
