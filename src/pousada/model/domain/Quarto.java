package pousada.model.domain;

import java.io.Serializable;


public class Quarto implements Serializable {
    
    private int idQuarto;
    private int numeroQuarto;
    private double preco;
    private String descricao;
    private int quantidadePessoa;
    private TipoQuarto tipoQuarto;

    public Quarto() {
    }

    public Quarto(int idQuarto, int numeroQuarto, double preco, String descricao, int quantidadePessoa) {
        this.idQuarto = idQuarto;
        this.numeroQuarto = numeroQuarto;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidadePessoa = quantidadePessoa;
    }

    public int getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(int numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadePessoa() {
        return quantidadePessoa;
    }

    public void setQuantidadePessoa(int quantidadePessoa) {
        this.quantidadePessoa = quantidadePessoa;
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroQuarto);
    }

    
    
}
