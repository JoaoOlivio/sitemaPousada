package pousada.controller;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import pousada.model.dao.QuartoDAO;
import pousada.model.dao.ReservaDAO;
import pousada.model.dao.TipoQuartoDAO;
import pousada.model.database.Database;
import pousada.model.database.DatabaseFactory;
import pousada.model.domain.Quarto;
import pousada.model.domain.Reserva;
import pousada.model.domain.TipoQuarto;

/**
 * FXML Controller class
 *
 * @author joaoo
 */
public class FXMLDashboardController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChartReserva;

    @FXML
    private CategoryAxis categoryAxisMeses;
    
    @FXML
    private Label labelTotalReserva = new Label();

    @FXML
    private Label labelQtdQuartos;

    @FXML
    private Label labelClientesCadastrados;

    @FXML
    private NumberAxis numberAxisReservas;
    
     private ObservableList<String> observableListMeses = FXCollections.observableArrayList();

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ExibirReserva();
        ExibirQuarto();
        ExibirCliente();

        
        
      // Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableListMeses.addAll(Arrays.asList(arrayMeses));

        // Associa os nomes de mês como categorias para o eixo horizontal.
        categoryAxisMeses.setCategories(observableListMeses);
        
        reservaDAO.setConnection(connection);
        Map<Integer, ArrayList> dados = reservaDAO.listarQuantidadeReservaPorMes();

        for (Map.Entry<Integer, ArrayList> dadosItem : dados.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());

            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;
                Integer quantidade;

                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                quantidade = (Integer) dadosItem.getValue().get(i + 1);

                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            barChartReserva.getData().add(series);
        }
        //barChart.getData().sorted();
        
        //ObservableList<String> list = categoryAxis.getCategories();
        //Comparator<String> byValue = (e1, e2) -> Integer.compare(Integer.parseInt(e1), Integer.parseInt(e2));
        //list.sort(byValue);
        //categoryAxis.setCategories(list);
    }

    public String retornaNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
        }
    }
    
    public String listarTotalReserva() throws SQLException {
        
      Statement stmt = connection.createStatement();
      String sql = "SELECT count(reserva) FROM reserva";
      ResultSet rs = stmt.executeQuery(sql);

      rs.next();
      int count = rs.getInt(1);
      String valor = Integer.toString(count);
              
      return valor;
    }
    
     public String listarTotalQuartos() throws SQLException {
        
      Statement stmt = connection.createStatement();
      String sql = "SELECT count(quarto) FROM quarto";
      ResultSet rs = stmt.executeQuery(sql);

      rs.next();
      int count = rs.getInt(1);
      String valor = Integer.toString(count);
              
      return valor;
    }
     
      public String listarTotalClientes() throws SQLException {
        
      Statement stmt = connection.createStatement();
      String sql = "SELECT count(clientes) FROM clientes";
      ResultSet rs = stmt.executeQuery(sql);

      rs.next();
      int count = rs.getInt(1);
      String valor = Integer.toString(count);
              
      return valor;
    }
    
    public void ExibirReserva(){
        String totalReserva = null;
        try {
            totalReserva = listarTotalReserva();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelTotalReserva.setText(totalReserva);
    }
    
    public void ExibirQuarto(){
        String totalQuarto = null;
        try {
            totalQuarto = listarTotalQuartos();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelQtdQuartos.setText(totalQuarto);
    }
    
    public void ExibirCliente(){
        String totalCliente = null;
        try {
            totalCliente = listarTotalClientes();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelClientesCadastrados.setText(totalCliente);
    }
}
