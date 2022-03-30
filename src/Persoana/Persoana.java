package Persoana;

public class Persoana {
    protected String nume;
    protected String data_nastere;

    public Persoana(){}

    public Persoana(String nume, String data){
        this.nume = nume;
        this.data_nastere = data;
    }

    public String getNume(){
        return nume;
    }

    public String getData_nastere(){
        return data_nastere;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setData_nastere(String data_nastere) {
        this.data_nastere = data_nastere;
    }

    @Override
    public String toString() {
        String output = "- Info persoana -\n";
        output += "Nume: " + this.nume + "\n";
        output += "Data nastere: " + this.data_nastere + "\n";

        return output;
    }
}
