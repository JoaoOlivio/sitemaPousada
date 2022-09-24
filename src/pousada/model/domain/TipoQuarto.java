package pousada.model.domain;

import java.io.Serializable;

public class TipoQuarto implements Serializable{
    
    private int idTipoQuarto;
    private String nome;

    public TipoQuarto(){
    }

    public TipoQuarto(int idTipoQuarto, String nome) {
        this.idTipoQuarto = idTipoQuarto;
        this.nome = nome;
    }
    
    public int getIdTipoQuarto() {
        return idTipoQuarto;
    }

    public void setIdTipoQuarto(int idTipoQuarto) {
        this.idTipoQuarto = idTipoQuarto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }
        
    
}
