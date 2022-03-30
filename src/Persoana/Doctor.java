package Persoana;

import Persoana.Persoana;

public class Doctor extends Persoana {
    private String specialitate;

    public Doctor(){}

    public Doctor(String nume, String data, String specialitate){
        super(nume, data);
        this.specialitate = specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    public String getSpecialitate() {
        return specialitate;
    }

    @Override
    public String toString() {
        String output = "- Info doctor -\n";
        output += "Nume: " + this.nume + "\n";
        output += "Data nastere: " + this.data_nastere + "\n";
        output += "Specialitate: " + this.specialitate + "\n";

        return output;
    }
}
