package Persoana;

import java.util.Set;

public class Client extends Persoana {
    private Set<String> afectiuni;

    public Client(){}

    public Client(String nume, String data, Set<String> afectiuni){
        super(nume, data);
        this.afectiuni = afectiuni;
    }

    public void setAfectiuni(Set<String> afectiuni) {
        this.afectiuni = afectiuni;
    }

    public Set<String> getAfectiuni() {
        return afectiuni;
    }

    @Override
    public String toString() {
        String output = "- Info client -\n";
        output += "Nume: " + this.nume + "\n";
        output += "Data nastere: " + this.data_nastere + "\n";
        if(afectiuni.size() > 0) {
            output += "Afectiuni: ";
            for (String afec : afectiuni) {
                output += afec + " ";
            }
            output += "\n";
        }
        else{
            output += "Clientul nu are afectiuni. \n";
        }

        return output;
    }

    public void adaugaAfectiune(String afectiune){
        this.afectiuni.add(afectiune);
    }
}
