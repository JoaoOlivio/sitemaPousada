package pousada.model.domain;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int idCliente;
    private String nome;
    private String cpf;
    private String telefone;
    private int quantidadeDeHospedagem;

    public Cliente(){
    }
    
    public Cliente(int idCliente, String nome, String cpf, int quantidadeDeHospedagem) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.quantidadeDeHospedagem = quantidadeDeHospedagem;
    }
    
    

    public int getCdCliente() {
        return idCliente;
    }

    public void setCdCliente(int cdCliente) {
        this.idCliente = cdCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getQuantidadeDeHospedagem() {
        return quantidadeDeHospedagem;
    }

    public void setQuantidadeDeHospedagem(int quantidadeDeHospedagem) {
        this.quantidadeDeHospedagem = quantidadeDeHospedagem;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
