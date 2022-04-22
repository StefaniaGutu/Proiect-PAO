package Programare;

import Persoana.Client;
import Persoana.Doctor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProgramareCuAsigurare extends Programare {
    private String cod_asigurare;
    private String tip;

    public ProgramareCuAsigurare(){}

    public ProgramareCuAsigurare(Doctor doctor, Client client, Date data, String ora, Integer nr_cabinet,
                                 String cod_asigurare, String tip){
        super(doctor, client, data, ora, nr_cabinet);
        this.cod_asigurare = cod_asigurare;
        this.tip = tip;
    }

    public String getCod_asigurare() {
        return cod_asigurare;
    }

    public String getTip() {
        return tip;
    }

    public void setCod_asigurare(String cod_asigurare) {
        this.cod_asigurare = cod_asigurare;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString(){
        String output = "-- Detalii PROGRAMARE cu Asigurare --\n";
        output += "Nume doctor: " + doctor.getNume() + "\n";
        output += "Nume client: " + client.getNume() + "\n";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        output += "Data: " + sdf.format(this.data) + "\n";
        output += "Ora: " + this.ora + "\n";
        output += "Nr cabinet: " + this.nr_cabinet + "\n";
        output += "Cod asigurare: " + this.cod_asigurare + "\n";
        output += "Tip: " + this.tip + "\n";

        return output;
    }
}
