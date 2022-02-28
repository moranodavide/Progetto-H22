package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import view.MainApplication;
import view.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable, IControlledScreen {

    ScreensController myController;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToHome(ActionEvent event){
        myController.setScreen(MainApplication.home);
    }

    @FXML
    private void goToSearch(ActionEvent event){
        myController.setScreen(MainApplication.search);
    }

    @FXML
    private void goToLogin(ActionEvent event){
        myController.setScreen(MainApplication.login);
    }

    @FXML
    private void logout(ActionEvent event){
        //cambia stato come non loggato
        myController.setScreen(MainApplication.home);
    }

}
