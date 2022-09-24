package pousada.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pousada.model.domain.ThreadContador;

/**
 * FXML Controller class
 *
 * @author joaoo
 */
public class FXMLThreadController implements Initializable {
   
    @FXML
    private Label labelContadorThread;
    
    private Thread contador;

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ThreadContador thread = new ThreadContador(labelContadorThread);
        this.contador = new Thread(thread);
        contador.start();
                
    }    
    
}
