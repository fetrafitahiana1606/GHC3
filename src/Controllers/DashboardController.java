package Controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private Parent fxml;

    @FXML
    private Button btn_matiere;

    @FXML
    private Button btn_volumehoraire;

    @FXML
    private Pane modifiable;

    @FXML
    private Button btn_professeur;

    @FXML
    private Button btn_bulletin;

    @FXML
    void btn_matiere_clicked(MouseEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("../sample/FXML/matiere.fxml"));
        modifiable.getChildren().removeAll();
        modifiable.getChildren().setAll(fxml);
    }

    @FXML
    void btn_volumehoraire_clicked(MouseEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("../sample/FXML/volumehoraire.fxml"));
        modifiable.getChildren().removeAll();
        modifiable.getChildren().setAll(fxml);
    }

    @FXML
    void btn_professeur_clicked(MouseEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("../sample/FXML/professeur.fxml"));
        modifiable.getChildren().removeAll();
        modifiable.getChildren().setAll(fxml);
    }

    @FXML
    void btn_bulletin_clicked(MouseEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("../sample/FXML/bulletin.fxml"));
        modifiable.getChildren().removeAll();
        modifiable.getChildren().setAll(fxml);
    }

    @FXML
    void btn_HeureComp_clicked(MouseEvent event) throws IOException {
        fxml = FXMLLoader.load(getClass().getResource("../sample/FXML/heurecomplementaire.fxml"));
        modifiable.getChildren().removeAll();
        modifiable.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
