package Cabinet_medical;

import Persoana.Client;
import Persoana.Doctor;
import Programare.Programare;

public class AdresaCabinet {
    private String oras;
    private String strada;
    private Integer numar;

    public AdresaCabinet(){}

    public AdresaCabinet(String oras, String strada, Integer numar){
        this.oras = oras;
        this.strada = strada;
        this.numar = numar;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public void setNumar(Integer numar) {
        this.numar = numar;
    }

    public String getOras() {
        return oras;
    }

    public String getStrada() {
        return strada;
    }

    public Integer getNumar() {
        return numar;
    }

    @Override
    public String toString(){
        String output = "Oras: "+ this.oras +"\n";
        output += "Strada: "+ this.strada +"\n";
        output += "Numar: "+ this.numar +"\n";

        return output;
    }

}
