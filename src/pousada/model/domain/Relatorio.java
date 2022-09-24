package pousada.model.domain;

import java.util.ArrayList;
import java.util.List;


public class Relatorio {
    
    private List<Reserva> listReservas;
    private String[] meses;
    private List<Integer> listTipoQuarto = new  ArrayList<>();
    private List<Integer> listTotalReservas = new  ArrayList<>();
    private List<Integer> listTotalHospedes = new  ArrayList<>();
    

    public Relatorio(List<Reserva> listReservas) {
        this.listReservas = listReservas;
    }
    
    public void CalcularTotalReserva(){
        for(Reserva r: listReservas){
            
        }
    }
    
    
}
