
package pousada.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pousada.model.dao.ClienteDAO;
import pousada.model.dao.QuartoDAO;
import pousada.model.dao.ReservaDAO;
import pousada.model.dao.TipoQuartoDAO;
import pousada.model.database.Database;
import pousada.model.database.DatabaseFactory;
import pousada.model.domain.Cliente;
import pousada.model.domain.Quarto;
import pousada.model.domain.Reserva;
import pousada.model.domain.TipoQuarto;


public class FXMLReservaDialogController implements Initializable {
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Reserva reserva;
    private Cliente cliente;
    
     //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final QuartoDAO quartoDAO = new QuartoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final TipoQuartoDAO tipoQuartoDAO = new TipoQuartoDAO();
    
    @FXML
    private JFXComboBox<Cliente> comboBoxCliente;

    @FXML
    private JFXDatePicker dataInicio;

    @FXML
    private JFXDatePicker dataFinal;

    @FXML
    private JFXComboBox<Quarto> comboBoxQuarto;

    @FXML
    private JFXComboBox<TipoQuarto> comboBoxTipoQuarto;

    @FXML
    private JFXTextField labelQtdPessoa;

    @FXML
    private JFXTextField labelPrecoFinal;

    @FXML
    private JFXButton buttonConfirmar;

    @FXML
    private JFXButton buttonCancelar;

    @FXML
    private TableView<Quarto> tableViewQuartos;

    @FXML
    private TableColumn<Quarto, Integer> tableColumnNumero;

    @FXML
    private TableColumn<Quarto, Double> tableColumnPreco;
    
    private List<Cliente> listCliente;
    private ObservableList<Cliente> observableListCliente;
    
    private List<TipoQuarto> listTipoQuarto;
    private ObservableList<TipoQuarto> observableListTipoQuarto;
    
    private List<Quarto> listQuarto;
    private ObservableList<Quarto> observableListQuarto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        quartoDAO.setConnection (connection);
        clienteDAO.setConnection (connection);
        tipoQuartoDAO.setConnection (connection);
        // TODO
        
        carregarComboBoxCliente();
        carregarComboBoxTipoQuarto();
        
        comboBoxQuarto.setEditable(true);
        comboBoxQuarto.getEditor().setEditable(false);
        comboBoxQuarto.setConverter(new StringConverter<Quarto>() {
            @Override
            public String toString(Quarto object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Quarto fromString(String string) {
                return comboBoxQuarto.getSelectionModel().getSelectedItem();
            }
        });
        
        
        // Limpando a exibição dos detalhes da venda
        carregarTableViewQuarto();
        
        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        //tableViewQuartos.getSelectionModel().selectedItemProperty().addListener(
                //(observable, oldValue, newValue) -> carregarComboBoxQuarto());
       
        
        
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

    public Reserva getReserva() {
        return reserva;
    }
    
    public void setReserva(Reserva reserva) {        
        this.reserva= reserva;
        this.labelPrecoFinal.setText(String.valueOf(reserva.getPreco()));
        this.labelQtdPessoa.setText(String.valueOf(reserva.getQuantidadeHospede()));
        this.comboBoxCliente.getSelectionModel().select(reserva.getCliente());
        
        if( reserva.getQuarto() != null){
            
            listQuarto = quartoDAO.listarQuartosPorTipoQuarto(reserva.getQuarto().getTipoQuarto().getIdTipoQuarto());
            
            observableListQuarto = FXCollections.observableArrayList(listQuarto);
            comboBoxQuarto.setItems(observableListQuarto);
        
            this.comboBoxQuarto.getSelectionModel().select(reserva.getQuarto());
            this.comboBoxTipoQuarto.getSelectionModel().select(reserva.getQuarto().getTipoQuarto());
        }
        this.dataInicio.setValue(reserva.getDataInicio());
        this.dataFinal.setValue(reserva.getDataFinal());
    }
    
    
    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }
    
    @FXML
    public void handleButtonConfirmar() {
        //.size
            int dias = (int)ChronoUnit.DAYS.between(dataInicio.getValue(), dataFinal.getValue());
            double preco = comboBoxQuarto.getSelectionModel().getSelectedItem().getPreco() * (dias + 1);
             
            reserva.setCliente((Cliente)comboBoxCliente.getSelectionModel().getSelectedItem());
            reserva.setQuantidadeHospede(Integer.parseInt(labelQtdPessoa.getText()));
            reserva.setQuarto((Quarto)comboBoxQuarto.getSelectionModel().getSelectedItem());
            //reserva.setTipoQuarto((TipoQuarto)comboBoxTipoQuarto.getSelectionModel().getSelectedItem());
            reserva.setPreco(preco);
            reserva.setDataInicio(dataInicio.getValue());
            reserva.setDataFinal(dataFinal.getValue());
            
            buttonConfirmarClicked = true;
            dialogStage.close();
        
    }
    
    public void carregarComboBoxCliente() {
        //Valor no ComboBox Cliente
        comboBoxCliente.setEditable(true);
        comboBoxCliente.getEditor().setEditable(false);
        comboBoxCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Cliente fromString(String string) {
                return comboBoxCliente.getSelectionModel().getSelectedItem();
            }
        });
        
        listCliente = clienteDAO.listar();

        observableListCliente = FXCollections.observableArrayList(listCliente);
        comboBoxCliente.setItems(observableListCliente);
    }
    
    public void carregarComboBoxTipoQuarto() {
        //Valor no ComboBox Tipo Quarto
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
    
    @FXML
    public void carregarComboBoxQuarto() {
        //Valor no ComboBox Tipo Quarto
        comboBoxQuarto.setEditable(true);
        comboBoxQuarto.getEditor().setEditable(false);
        comboBoxQuarto.setConverter(new StringConverter<Quarto>() {
            @Override
            public String toString(Quarto object) {
                if (object == null) return null;
                return object.toString();
            }

            @Override
            public Quarto fromString(String string) {
                return comboBoxQuarto.getSelectionModel().getSelectedItem();
            }
        });
        
        //TipoQuarto t = comboBoxTipoQuarto.getSelectionModel().getSelectedItem();
        
        listQuarto = quartoDAO.listarQuartosPorTipoQuarto(comboBoxTipoQuarto.getSelectionModel().getSelectedItem().getIdTipoQuarto());

        observableListQuarto = FXCollections.observableArrayList(listQuarto);
        comboBoxQuarto.setItems(observableListQuarto);
    }
    
   public void carregarTableViewQuarto() {
        tableColumnNumero.setCellValueFactory(new PropertyValueFactory<>("numeroQuarto"));
        tableColumnPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        listQuarto = quartoDAO.listar();

        observableListQuarto = FXCollections.observableArrayList(listQuarto);
        tableViewQuartos.setItems(observableListQuarto);
    }
     
    
}
