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
import java.sql.Date;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import pousada.model.dao.ClienteDAO;
import pousada.model.dao.QuartoDAO;
import pousada.model.dao.ReservaDAO;
import pousada.model.database.Database;
import pousada.model.database.DatabaseFactory;
import pousada.model.domain.Reserva;

public class FXMLReservaController implements Initializable {
    
    @FXML
    private TableView<Reserva> tableViewReserva;

     @FXML
    private TableColumn<Reserva, String> tableColumnDataInicio;

    @FXML
    private TableColumn<Reserva, Date> tableColumnDataFim;

    @FXML
    private TableColumn<Reserva, String> tableColumnQtdPessoas;

    @FXML
    private TableColumn<Reserva, Reserva> tableColumnCliente;

    @FXML
    private JFXButton buttonCadastrar;

    @FXML
    private JFXButton buttonExcluir;

    @FXML
    private Label labelCliente;

    @FXML
    private Label labelDataInicio;

    @FXML
    private Label labelDataFim;

    @FXML
    private Label labelQtdPessoas;

    @FXML
    private Label labelNumeroQuarto;

    @FXML
    private Label labelTipoQuarto;

    @FXML
    private Label labelValor;
    
    @FXML
    private JFXButton buttonAlterar;
    
    private List<Reserva> listReserva;
    private ObservableList<Reserva> observableListReserva;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final QuartoDAO quartoDAO = new QuartoDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO ();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservaDAO.setConnection(connection);
        clienteDAO.setConnection(connection);

        carregarTableViewReserva();

        // Limpando a exibição dos detalhes da venda
        selecionarItemTableViewReserva(null);
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewReserva.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewReserva(newValue));

    }   
    
    public void carregarTableViewReserva() {
        tableColumnDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        tableColumnDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFinal"));
        tableColumnQtdPessoas.setCellValueFactory(new PropertyValueFactory<>("quantidadeHospede"));
        tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        listReserva = reservaDAO.listar();

        observableListReserva = FXCollections.observableArrayList(listReserva);
        tableViewReserva.setItems(observableListReserva);
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Reserva reserva = new Reserva();
        boolean buttonConfirmarClicked = showFXMLReservaDialog(reserva);
        if (buttonConfirmarClicked) {
            
            Date dataInicio = java.sql.Date.valueOf( reserva.getDataInicio());
            Date dataFinal = java.sql.Date.valueOf( reserva.getDataFinal());
            
            if(reservaDAO.listarQuartosDisponiveis(dataInicio, dataFinal, reserva.getQuarto().getIdQuarto()).size() <= 0){
                reservaDAO.inserir(reserva);
                reserva.getCliente().setQuantidadeDeHospedagem(reserva.getCliente().getQuantidadeDeHospedagem() + 1);
                clienteDAO.alterar(reserva.getCliente());
                carregarTableViewReserva();
                System.out.print(reservaDAO.listarQuartosDisponiveis(dataInicio, dataFinal, reserva.getQuarto().getIdQuarto()).size());
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Não é Possiel finalizar o cadastro da Reserva. Quarto Indisponivel");
                alert.show();

            }
                     
        }
    }
    
     @FXML
    public void handleButtonAlterar() throws IOException {
        Reserva reserva = tableViewReserva.getSelectionModel().getSelectedItem();//Obtendo reserva selecionado
        if (reserva != null) {
            boolean buttonConfirmarClicked = showFXMLReservaDialog(reserva);
            if (buttonConfirmarClicked) {
                reservaDAO.alterar(reserva);
                carregarTableViewReserva();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Reserva na Tabela!");
            alert.show();
        }
    }
    
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Reserva reserva = tableViewReserva.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            reservaDAO.remover(reserva);
            carregarTableViewReserva();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma Reserva na Tabela!");
            alert.show();
        }
    }
    
    public void selecionarItemTableViewReserva(Reserva reserva) {
        if (reserva != null) {
            labelCliente.setText(String.valueOf(reserva.getCliente().getNome()));
            labelDataInicio.setText(String.valueOf(reserva.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelDataFim.setText(String.valueOf(reserva.getDataFinal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelQtdPessoas.setText(String.valueOf(reserva.getQuantidadeHospede()));
            labelNumeroQuarto.setText(String.valueOf(reserva.getQuarto().getNumeroQuarto()));
            labelTipoQuarto.setText(String.valueOf(reserva.getQuarto().getTipoQuarto()));
            labelValor.setText(String.valueOf(reserva.getPreco()));
        } else {
            labelCliente.setText("");
            labelQtdPessoas.setText("");
            labelDataInicio.setText("");
            labelDataFim.setText("");
            labelNumeroQuarto.setText("");
            labelTipoQuarto.setText("");
            labelValor.setText("");
        }
    }
    
    
    private boolean showFXMLReservaDialog(Reserva reserva) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLQuartoDialogController.class.getResource("/pousada/view/FXMLReservaDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Reserva");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        FXMLReservaDialogController controller = loader.getController();
        controller.setReserva(reserva);
        controller.setDialogStage(dialogStage);
        //controller.setQuarto(quarto);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
    
}
