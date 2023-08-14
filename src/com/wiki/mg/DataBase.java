package com.wiki.mg;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {
    Connection cnx;

    public static void main(String[] args) {
        Connection cnx = connexionDB();
    }

    public static Connection connexionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver OK!");
            String url = "jdbc:mysql://localhost:3306/ghce";
            String user = "root";
            String password = "Wiki15_01_17";
            Connection cnx = DriverManager.getConnection(url, user, password);
            if (cnx != null) {
                System.out.println("Connexion bien établie");
            } else {
                System.out.println("Problème de Connexion à la base de donnée");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Désolé erreur de connexion ", ButtonType.CLOSE);
                alert.showAndWait();
            }
            return cnx;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Désolé erreur de connexion ", ButtonType.CLOSE);
            alert.showAndWait();
            e.printStackTrace();
            return null;
        }
    }
}
