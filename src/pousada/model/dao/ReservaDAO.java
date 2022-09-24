package pousada.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pousada.model.domain.Cliente;
import pousada.model.domain.Quarto;
import pousada.model.domain.Reserva;
import pousada.model.domain.TipoQuarto;

public class ReservaDAO {
    
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Reserva reserva) {
        String sql = "INSERT INTO reserva(quantidadeHospede, valor, dataInicio, dataFinal, idQuarto, idCliente) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getQuantidadeHospede());
            stmt.setDouble(2, reserva.getPreco());
            stmt.setDate(3, Date.valueOf(reserva.getDataInicio()));
            stmt.setDate(4, Date.valueOf(reserva.getDataFinal()));
            stmt.setInt(5, reserva.getQuarto().getIdQuarto());
            stmt.setInt(6, reserva.getCliente().getCdCliente());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(Reserva reserva) {
        String sql = "UPDATE reserva SET quantidadeHospede=?, valor=?, dataInicio=?, dataFinal=?, idQuarto=?, idCliente=? WHERE idReserva=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getQuantidadeHospede());
            stmt.setDouble(2, reserva.getPreco());
            stmt.setDate(3, Date.valueOf(reserva.getDataInicio()));
            stmt.setDate(4, Date.valueOf(reserva.getDataFinal()));
            stmt.setInt(5, reserva.getQuarto().getIdQuarto());
            stmt.setInt(6, reserva.getCliente().getCdCliente());
            stmt.setInt(7, reserva.getIdReserva());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
     public boolean remover(Reserva reserva) {
        String sql = "DELETE FROM reserva WHERE idReserva=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdReserva());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    public List<Reserva> listar() {
       String sql = "SELECT * FROM reserva";
       List<Reserva> retorno = new ArrayList<>();
       try {
           PreparedStatement stmt = connection.prepareStatement(sql);
           ResultSet resultado = stmt.executeQuery();

           while (resultado.next()) {

               Cliente cliente = new Cliente();
               Reserva reserva = new Reserva();
               Quarto quarto = new Quarto();
               TipoQuarto tipoQuarto = new TipoQuarto();

               reserva.setIdReserva(resultado.getInt("idReserva"));
               reserva.setQuantidadeHospede(resultado.getInt("quantidadeHospede"));
               reserva.setPreco(resultado.getDouble("valor"));
               reserva.setDataInicio(resultado.getDate("dataInicio").toLocalDate());
               reserva.setDataFinal(resultado.getDate("dataFinal").toLocalDate());
               cliente.setCdCliente(resultado.getInt("idCliente"));
               quarto.setIdQuarto(resultado.getInt("IdQuarto"));

               //Obtendo os dados completos do Cliente associado à Reserva
               ClienteDAO clienteDAO = new ClienteDAO();
               clienteDAO.setConnection(connection);
               cliente = clienteDAO.buscar(cliente);

               //Obtendo os dados completos do Quarto associado à Reserva
               QuartoDAO quartoDAO = new QuartoDAO();
               quartoDAO.setConnection(connection);
               quarto = quartoDAO.buscar(quarto);

               TipoQuartoDAO tipoQuartoDAO = new TipoQuartoDAO();
               tipoQuartoDAO.setConnection(connection);
               tipoQuarto = tipoQuartoDAO.buscar(quarto.getTipoQuarto());
               quarto.setTipoQuarto(tipoQuarto);

               reserva.setCliente(cliente); 
               reserva.setQuarto(quarto);
               retorno.add(reserva);
           }
       } catch (SQLException ex) {
           Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
       }
       return retorno;
   }
    
    
    public List<Reserva> listarQuartosDisponiveis(Date dataInicio, Date dataFinal, int idQuarto) {
        String sql = "SELECT * FROM reserva WHERE dataInicio >= ? AND dataFinal <= ? AND idQuarto=?";
        List<Reserva> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, dataInicio);
            stmt.setDate(2, dataFinal);
            stmt.setInt(3, idQuarto); 
            ResultSet resultado = stmt.executeQuery();
   
            while (resultado.next()) {

                Reserva reserva= new Reserva();
                Quarto quarto = new Quarto();
                
                quarto.setIdQuarto(resultado.getInt("IdQuarto"));
                
                //Obtendo os dados completos do Quarto associado à Reserva
                QuartoDAO quartoDAO = new QuartoDAO();
                quartoDAO.setConnection(connection);
                quarto = quartoDAO.buscar(quarto);
               
                reserva.setQuarto(quarto);
                retorno.add(reserva);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Map<Integer, ArrayList> listarQuantidadeReservaPorMes() {
        String sql = "select count(idReserva), extract(year from dataInicio) as ano, extract(month from dataInicio) as mes from reserva group by ano, mes order by ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
     
    public List<Integer> listarAnos(){
        
       String sql = "SELECT DISTINCT EXTRACT(year from dataInicio) AS ano FROM reserva GROUP BY ano ORDER BY ano;";
        List<Integer> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                retorno.add(resultado.getInt("ano"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
     
    
}
