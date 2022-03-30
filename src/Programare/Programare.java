package Programare;

import Persoana.Client;
import Persoana.Doctor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Programare {
    protected Doctor doctor;
    protected Client client;
    protected Date data;
    protected String ora;
    protected Integer nr_cabinet;

    public Programare(){}

    public Programare(Doctor doctor, Client client, Date data, String ora, Integer nr_cabinet){
        this.doctor = doctor;
        this.client = client;
        this.data = data;
        this.ora = ora;
        this.nr_cabinet = nr_cabinet;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public void setNr_cabinet(Integer nr_cabinet) {
        this.nr_cabinet = nr_cabinet;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Client getClient() {
        return client;
    }

    public Date getData() {
        return data;
    }

    public String getOra() {
        return ora;
    }

    public Integer getNr_cabinet() {
        return nr_cabinet;
    }

    @Override
    public String toString(){
        String output = "-- Detalii PROGRAMARE --\n";
        output += "Nume doctor: " + doctor.getNume() + "\n";
        output += "Nume client: " + client.getNume() + "\n";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        output += "Data: " + sdf.format(this.data) + "\n";
        output += "Ora: " + this.ora + "\n";
        output += "Nr cabinet: " + this.nr_cabinet + "\n";

        return output;
    }
}
