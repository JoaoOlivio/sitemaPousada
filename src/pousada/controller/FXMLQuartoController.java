/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pousada.controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pousada.model.dao.QuartoDAO;
import pousada.model.dao.TipoQuartoDAO;
import pousada.model.database.Database;
import pousada.model.database.DatabaseFactory;
import pousada.model.domain.Quarto;
import pousada.model.domain.TipoQuarto;


public class FXMLQuartoController implements Initializable {

    @FXML
    private TableView<Quarto> tableView;

    @FXML
    private TableColumn<Quarto, String> tableColumnNumero;

    @FXML
    private TableColumn<Quarto, Quarto> tableColumnTipoQuarto;

    @FXML
    private TableColumn<Quarto, String> tableColumnPreco;
    
    @FXML
    private Label labelQuartoNumero;

    @FXML
    private Label labelQuartoPreco;

    @FXML
    private Label labelQuartoQtdPessoa;

    @FXML
    private Label labelQuartoTipoQuarto;

    @FXML
    private Label labelQuartoDescricao;
    
    @FXML
    private JFXButton buttonInserir;

    @FXML
    private JFXButton buttonAlterar;

    @FXML
    private JFXButton buttonExcluir;

    
    private List<Quarto> listQuarto;
    private ObservableList<Quarto> observableListQuarto;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final QuartoDAO quartoDAO = new QuartoDAO();
    private final TipoQuartoDAO tipoQuartoDAO = new TipoQuartoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        quartoDAO.setConnection (connection);
        
        carregarTableViewQuarto();
        
        // Limpando a exibição dos detalhes do quarto
        selecionarItemTableViewQuarto(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewQuarto(newValue));
    }
    
     public void carregarTableViewQuarto() {
        tableColumnNumero.setCellValueFactory(new PropertyValueFactory<>("numeroQuarto"));
        tableColumnTipoQuarto.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listQuarto = quartoDAO.listar();

        observableListQuarto = FXCollections.observableArrayList(listQuarto);
        tableView.setItems(observableListQuarto);
    }
     
     public void selecionarItemTableViewQuarto(Quarto quarto) {
        if (quarto != null) {
            labelQuartoNumero.setText(String.valueOf(quarto.getNumeroQuarto()));
            labelQuartoPreco.setText(String.valueOf(quarto.getPreco()));
            labelQuartoQtdPessoa.setText(String.valueOf(quarto.getQuantidadePessoa()));
            labelQuartoDescricao.setText(quarto.getDescricao());
            labelQuartoTipoQuarto.setText(quarto.getTipoQuarto().getNome());
        } else {
            labelQuartoNumero.setText("");
            labelQuartoPreco.setText("");
            labelQuartoQtdPessoa.setText("");
            labelQuartoDescricao.setText("");
            labelQuartoTipoQuarto.setText("");
        }
    }
     
     @FXML
    public void handleButtonInserir() throws IOException {
        Quarto quarto = new Quarto();
        boolean buttonConfirmarClicked = showFXMLQuartoDialog(quarto);
        if (buttonConfirmarClicked) {
            quartoDAO.inserir(quarto);
            carregarTableViewQuarto();
        }
    }
    
    @FXML
    public void handleButtonAlterar() throws IOException {
        Quarto quarto = tableView.getSelectionModel().getSelectedItem();//Obtendo quarto selecionado
        if (quarto != null) {
            boolean buttonConfirmarClicked = showFXMLQuartoDialog(quarto);
            if (buttonConfirmarClicked) {
                quartoDAO.alterar(quarto);
                carregarTableViewQuarto();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um quarto na Tabela!");
            alert.show();
        }
    }
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Quarto quarto = tableView.getSelectionModel().getSelectedItem();
        if (quarto != null) {
            quartoDAO.remover(quarto);
            carregarTableViewQuarto();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    private boolean showFXMLQuartoDialog(Quarto quarto) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLQuartoDialogController.class.getResource("/pousada/view/FXMLQuartoDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Quarto");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLQuartoDialogController controller = loader.getController();
        controller.setQuarto(quarto);
        controller.setDialogStage(dialogStage);
        //controller.setQuarto(quarto);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
    
}
