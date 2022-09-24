/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pousada.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pousada.model.dao.QuartoDAO;
import pousada.model.dao.TipoQuartoDAO;
import pousada.model.database.Database;
import pousada.model.database.DatabaseFactory;
import pousada.model.domain.Quarto;
import pousada.model.domain.TipoQuarto;


public class FXMLQuartoDialogController implements Initializable {
    
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Quarto quarto;
    private TipoQuarto tipoQuarto;
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final QuartoDAO quartoDAO = new QuartoDAO();
    private final TipoQuartoDAO tipoQuartoDAO = new TipoQuartoDAO();

    @FXML
    private JFXTextField textLabelNumeroQuarto;

    @FXML
    private JFXTextField textLabelQuantidadePessoa;

    @FXML
    private JFXTextField textLabelPreco;

    @FXML
    private JFXTextArea textLabelDescricao;

    @FXML
    private JFXComboBox<TipoQuarto> comboBoxTipoQuarto;

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;
    
    private List<TipoQuarto> listTipoQuarto;
    private ObservableList<TipoQuarto> observableListTipoQuarto;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quartoDAO.setConnection (connection);
        tipoQuartoDAO.setConnection (connection);
        
        //Valor no ComboBox
        comboBoxTipoQuarto.setEditable(true);
        comboBoxTipoQuarto.getEditor().setEditable(false);
        comboBoxTipoQuarto.setConverter(new StringConverter<TipoQuarto>() {
            @Override
            public String toString(TipoQuarto object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public TipoQuarto fromString(String string) {
                return comboBoxTipoQuarto.getSelectionModel().getSelectedItem();
            }
        });
        
        listTipoQuarto = tipoQuartoDAO.listar();

        observableListTipoQuarto = FXCollections.observableArrayList(listTipoQuarto);
        comboBoxTipoQuarto.setItems(observableListTipoQuarto);
    }
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Quarto getQuarto() {
        return quarto;
    }
    
     public void setQuarto(Quarto quarto) {        
        this.quarto = quarto;
        this.textLabelNumeroQuarto.setText(String.valueOf(quarto.getNumeroQuarto()));
        this.textLabelPreco.setText(String.valueOf(quarto.getPreco()));
        this.textLabelQuantidadePessoa.setText(String.valueOf(quarto.getQuantidadePessoa()));
        this.textLabelDescricao.setText(quarto.getDescricao());
        this.comboBoxTipoQuarto.getSelectionModel().select(quarto.getTipoQuarto());
    }
    
    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            quarto.setNumeroQuarto(Integer.parseInt(textLabelNumeroQuarto.getText()));
            quarto.setQuantidadePessoa(Integer.parseInt(textLabelQuantidadePessoa.getText()));
            quarto.setTipoQuarto((TipoQuarto)comboBoxTipoQuarto.getSelectionModel().getSelectedItem());
            quarto.setPreco(Double.parseDouble(textLabelPreco.getText()));
            quarto.setDescricao(textLabelDescricao.getText());
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textLabelNumeroQuarto.getText() == null || textLabelNumeroQuarto.getText().length() == 0) {
            errorMessage += "Número inválido!\n";
        }
        if (textLabelPreco.getText() == null || textLabelPreco.getText().length() == 0) {
            errorMessage += "Preço inválido!\n";
        }
        if (textLabelQuantidadePessoa.getText() == null || textLabelQuantidadePessoa.getText().length() == 0) {
            errorMessage += "Quantidade de Pessoa inválida!\n";
        }
        if (textLabelDescricao.getText() == null || textLabelDescricao.getText().length() == 0) {
            errorMessage += "Descrição inválida!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    

    
    
}
