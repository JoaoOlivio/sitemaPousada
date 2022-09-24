package pousada.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author joaoo
 */
public class FXMLDocumentController implements Initializable {
       
    @FXML
    private Button buttonDashboard;

    @FXML
    private Button buttonQuarto;

    @FXML
    private Button buttonReserva;

    @FXML
    private Button buttonRelatorios;

    @FXML
    private Button buttonThread;
    
    @FXML
    private AnchorPane anchorPanePrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            handleMenuDashboard();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    public void handleMenuQuarto() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/pousada/view/FXMLQuarto.fxml"));
        anchorPanePrincipal.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuDashboard() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/pousada/view/FXMLDashboard.fxml"));
        anchorPanePrincipal.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuReserva() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/pousada/view/FXMLReserva.fxml"));
        anchorPanePrincipal.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuRelatorios() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/pousada/view/FXMLRelatorio.fxml"));
        anchorPanePrincipal.getChildren().setAll(a);
    }
    
    @FXML 
    public void handleMenuThread() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/pousada/view/FXMLThread.fxml"));
        anchorPanePrincipal.getChildren().setAll(a);
    }
    
}
