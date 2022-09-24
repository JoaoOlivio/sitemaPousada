package pousada.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import pousada.model.dao.QuartoDAO;
import pousada.model.dao.ReservaDAO;
import pousada.model.database.Database;
import pousada.model.database.DatabaseFactory;
import pousada.model.domain.Cliente;



public class FXMLRelatorioController implements Initializable { 
    
    @FXML
    private JFXComboBox<Integer> comboBoxMes;

    @FXML
    private JFXButton buttonImprimir;

    @FXML
    private JFXComboBox<Integer> comboBoxAno;
    
    private List<Integer> listMeses;
    private List<Integer> listAno;
    private ObservableList<Integer> observableListMeses;
    private ObservableList<Integer> observableListAno;
    
    
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        reservaDAO.setConnection (connection);
        carregarComboBoxAno();
        carregarComboBoxMeses();

    }
    
    public void handleImprimir() throws JRException{
    
        HashMap filtro = new HashMap();
        int mes = comboBoxMes.getSelectionModel().getSelectedItem();
        int ano = comboBoxAno.getSelectionModel().getSelectedItem();
        String nomeMes = retornaNomeMes(mes);
        
        filtro.put("mes", mes);
        filtro.put("ano", ano);
        filtro.put("nomeMes", nomeMes);

        URL url = getClass().getResource("/pousada/relatorio/FXMVRelatorioTipoQuarto.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, filtro, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }

    public String retornaNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Janeiro";
            case 2:
                return "Fevereiro";
            case 3:
                return "Março";
            case 4:
                return "Abril";
            case 5:
                return "Maio";
            case 6:
                return "Junho";
            case 7:
                return "Julho";
            case 8:
                return "Agosto";
            case 9:
                return "Setembro";
            case 10:
                return "Outubro";
            case 11:
                return "Novembro";
            case 12:
                return "Dezembro";
            default:
                return "";
        }
    }
    
    public void carregarComboBoxAno() {
        
    
        listAno = reservaDAO.listarAnos();

        observableListAno = FXCollections.observableArrayList(listAno);
        comboBoxAno.setItems(observableListAno);
    }
    
    public void carregarComboBoxMeses() {
        
    
        listMeses  = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);

        observableListMeses = FXCollections.observableArrayList(listMeses);
        comboBoxMes.setItems(observableListMeses);
    }
     
}
