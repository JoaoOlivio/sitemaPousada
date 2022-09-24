/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pousada.model.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author joaoo
 */
public class ThreadContador implements Runnable{
    
    private Socket socket;
    Label labelContadorThread;

    public ThreadContador(Label labelContadorThread) {
        this.labelContadorThread = labelContadorThread;
    }

    @Override
    public void run() {
        
        try {
            while(true){
                this.socket = new Socket("34.125.13.172", 12345);
                ObjectInputStream entradaValor = new ObjectInputStream(socket.getInputStream());
                Integer qtdGeral = (Integer)entradaValor.readObject();
                Platform.runLater(() -> this.labelContadorThread.setText(qtdGeral.toString()));
                Thread.sleep(10000);
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ThreadContador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadContador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
