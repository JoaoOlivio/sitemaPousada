package pousada.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pousada.model.domain.Quarto;
import pousada.model.domain.TipoQuarto;

/**
 *
 * @author joaoo
 */
public class TipoQuartoDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public List<TipoQuarto> listar() {
        String sql = "SELECT * FROM tipoQuarto";
        List<TipoQuarto> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                
                TipoQuarto tipoQuarto= new TipoQuarto();
                tipoQuarto.setIdTipoQuarto(resultado.getInt("idTipoQuarto"));
                tipoQuarto.setNome(resultado.getString("nome"));
                
                retorno.add(tipoQuarto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public TipoQuarto buscar(TipoQuarto tipoQuarto) {
        String sql = "SELECT * FROM tipoQuarto WHERE idTipoQuarto=?";
        TipoQuarto retorno = new TipoQuarto();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, tipoQuarto.getIdTipoQuarto());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                tipoQuarto.setIdTipoQuarto(resultado.getInt("idTipoQuarto"));
                tipoQuarto.setNome(resultado.getString("nome"));
                retorno = tipoQuarto;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TipoQuartoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
