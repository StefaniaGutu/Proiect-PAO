package Programare;

import Persoana.Client;
import Persoana.Doctor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Programare_Cu_Plata extends Programare {
    private Integer cost;
    private String modalitate_plata;

    public Programare_Cu_Plata(){}

    public Programare_Cu_Plata(Doctor doctor, Client client, Date data, String ora, Integer nr_cabinet,
                               Integer cost, String modalitate_plata){
        super(doctor, client, data, ora, nr_cabinet);
        this.cost = cost;
        this.modalitate_plata = modalitate_plata;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setModalitate_plata(String modalitate_plata) {
        this.modalitate_plata = modalitate_plata;
    }

    public String getModalitate_plata() {
        return modalitate_plata;
    }

    public Integer getCost() {
        return cost;
    }
    @Override
    public String toString(){
        String output = "-- Detalii PROGRAMARE cu Plata --\n";
        output += "Nume doctor: " + doctor.getNume() + "\n";
        output += "Nume client: " + client.getNume() + "\n";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        output += "Data: " + sdf.format(this.data) + "\n";
        output += "Ora: " + this.ora + "\n";
        output += "Nr cabinet: " + this.nr_cabinet + "\n";
        output += "Cost: " + this.cost + "\n";
        output += "Modalitate plata: " + this.modalitate_plata + "\n";

        return output;
    }
}
