package Controllers;

import com.wiki.mg.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Parent fxml;
    @FXML
    private AnchorPane loginMain;

    @FXML
    private TextField input_username;

    @FXML
    private Button btn_connexion;

    @FXML
    private PasswordField input_password;

    @FXML
    private Label close;

    @FXML
    private void signin() {
        Statement stmt;
        ResultSet res = null;

        var cnx = DataBase.connexionDB();
        boolean tmp = false;
        String query = "SELECT * FROM `user` WHERE `user`.`username` LIKE '%"+input_username.getText()+"%'";
        try {
            if (cnx != null) {
                stmt = cnx.createStatement();
                res = stmt.executeQuery(query);
                while (res.next()) {
                    if (res.getString("username").equals(input_username.getText())==true ){
                        tmp = true;
                        if (res.getString("password").equals(input_password.getText())==true){
                            loginMain.getScene().getWindow().hide();
                            Stage dashboard = new Stage();
                            fxml =  FXMLLoader.load(getClass().getResource("../sample/FXML/dashboard.fxml"));
                            Scene scene = new Scene(fxml);
                            dashboard.setScene(scene);
                            dashboard.initStyle(StageStyle.UNDECORATED);
                            dashboard.show();

                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "mot de passe incorrect", ButtonType.CLOSE);
                            alert.showAndWait();
                        }
                    }
                }
                if (tmp == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Utilisateur non existant", ButtonType.CLOSE);
                    alert.showAndWait();
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
//        return tmp;
    }

    public void hanldleClose(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}
