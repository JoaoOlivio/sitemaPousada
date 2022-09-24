package pousada.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import pousada.model.domain.Cliente;
import pousada.model.domain.Quarto;
import pousada.model.domain.TipoQuarto;

public class QuartoDAO {
    
     private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Quarto quarto) {
        String sql = "INSERT INTO quarto(numeroQuarto, preco, quantidadePessoa, descricao, idTipoQuarto) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, quarto.getNumeroQuarto());
            stmt.setDouble(2, quarto.getPreco());
            stmt.setInt(3, quarto.getQuantidadePessoa());
            stmt.setString(4, quarto.getDescricao());
            stmt.setInt(5, quarto.getTipoQuarto().getIdTipoQuarto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Número de Quanto Indiponivel, informe outro.");
                alert.show();

            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Quarto quarto) {
        String sql = "UPDATE quarto SET numeroQuarto=?, preco=?, quantidadePessoa=?, descricao=?, idTipoQuarto=? WHERE idQuarto=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, quarto.getNumeroQuarto());
            stmt.setDouble(2, quarto.getPreco());
            stmt.setInt(3, quarto.getQuantidadePessoa());
            stmt.setString(4, quarto.getDescricao());
            stmt.setInt(5, quarto.getTipoQuarto().getIdTipoQuarto());
            stmt.setInt(6, quarto.getIdQuarto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Quarto quarto) {
        String sql = "DELETE FROM quarto WHERE idQuarto=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, quarto.getIdQuarto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Quarto> listar() {
        String sql = "SELECT * FROM quarto";
        List<Quarto> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
   
            while (resultado.next()) {
                
                TipoQuarto tipoQuarto = new TipoQuarto();
                Quarto quarto= new Quarto();
                quarto.setIdQuarto(resultado.getInt("idQuarto"));
                quarto.setNumeroQuarto(resultado.getInt("numeroQuarto"));
                quarto.setPreco(resultado.getDouble("preco"));
                quarto.setDescricao(resultado.getString("descricao"));
                quarto.setQuantidadePessoa(resultado.getInt("quantidadePessoa"));
                
                tipoQuarto.setIdTipoQuarto(resultado.getInt("idTipoQuarto"));
                
                //Obtendo os dados completos do Tipo Quarto associado à Quarto
                TipoQuartoDAO tipoQuartoDAO = new TipoQuartoDAO();
                tipoQuartoDAO.setConnection(connection);
                tipoQuarto = tipoQuartoDAO.buscar(tipoQuarto);
                
                quarto.setTipoQuarto(tipoQuarto);    
                retorno.add(quarto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Quarto> listarQuartosPorTipoQuarto(int idTipoQuarto) {
        String sql = "SELECT * FROM quarto WHERE idTipoQuarto=? ";
        List<Quarto> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTipoQuarto);
            ResultSet resultado = stmt.executeQuery();

   
            while (resultado.next()) {

               TipoQuarto tipoQuarto = new TipoQuarto();
                Quarto quarto= new Quarto();
                quarto.setIdQuarto(resultado.getInt("idQuarto"));
                quarto.setNumeroQuarto(resultado.getInt("numeroQuarto"));
                quarto.setPreco(resultado.getDouble("preco"));
                quarto.setDescricao(resultado.getString("descricao"));
                quarto.setQuantidadePessoa(resultado.getInt("quantidadePessoa"));
                
                tipoQuarto.setIdTipoQuarto(resultado.getInt("idTipoQuarto"));
                
                //Obtendo os dados completos do Tipo Quarto associado à Quarto
                TipoQuartoDAO tipoQuartoDAO = new TipoQuartoDAO();
                tipoQuartoDAO.setConnection(connection);
                tipoQuarto = tipoQuartoDAO.buscar(tipoQuarto);
                
                quarto.setTipoQuarto(tipoQuarto);    
                retorno.add(quarto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Quarto buscar(Quarto quarto) {
        String sql = "SELECT * FROM quarto WHERE idQuarto=?";
        Quarto retorno = new Quarto();
        TipoQuarto tipoQuarto = new TipoQuarto();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, quarto.getIdQuarto());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                quarto.setIdQuarto(resultado.getInt("idQuarto"));
                quarto.setNumeroQuarto(resultado.getInt("numeroQuarto"));
                quarto.setPreco(resultado.getDouble("preco"));
                quarto.setDescricao(resultado.getString("descricao"));
                quarto.setQuantidadePessoa(resultado.getInt("quantidadePessoa"));
                tipoQuarto.setIdTipoQuarto(resultado.getInt("idTipoQuarto"));
                quarto.setTipoQuarto(tipoQuarto);
                retorno = quarto;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
