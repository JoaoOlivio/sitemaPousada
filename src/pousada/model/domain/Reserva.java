package pousada.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Reserva implements Serializable{
    
    private int idReserva;
    private LocalDate dataInicio;
    private LocalDate dataFinal;
    private double preco;
    private int quantidadeHospede;
    private Quarto quarto;
    private Cliente cliente;

    public Reserva(int idReserva, LocalDate dataInicio, LocalDate dataFinal, double preco, int quantidadeHospede) {
        this.idReserva = idReserva;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
        this.preco = preco;
        this.quantidadeHospede = quantidadeHospede;
    }

    public Reserva() {
        
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeHospede() {
        return quantidadeHospede;
    }

    public void setQuantidadeHospede(int quantidadeHospede) {
        this.quantidadeHospede = quantidadeHospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return this.quarto.toString();
    }
    
    
    
}
