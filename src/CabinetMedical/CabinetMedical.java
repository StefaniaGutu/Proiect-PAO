package CabinetMedical;

import Persoana.Client;
import Persoana.Doctor;
import Programare.Programare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CabinetMedical {
    Set<Doctor> doctori;
    Set<Client> clienti;
    List<Programare> programari;
    AdresaCabinet adresa;

    public CabinetMedical(){
        doctori = new HashSet<Doctor>();
        clienti = new HashSet<Client>();
        programari = new ArrayList<Programare>();
        adresa = new AdresaCabinet();
    }

    public CabinetMedical(Set<Doctor> doctori, Set<Client> clienti, List<Programare> programari, AdresaCabinet adresa){
        this.doctori = doctori;
        this.clienti = clienti;
        this.programari = programari;
        this.adresa = adresa;
    }

    public void setDoctori(Set<Doctor> doctori) {
        this.doctori = doctori;
    }

    public void setClienti(Set<Client> clienti) {
        this.clienti = clienti;
    }

    public void setProgramari(List<Programare> programari) {
        this.programari = programari;
    }

    public void setAdresa(AdresaCabinet adresa) {
        this.adresa = adresa;
    }

    public Set<Doctor> getDoctori() {
        return doctori;
    }

    public Set<Client> getClienti() {
        return clienti;
    }

    public List<Programare> getProgramari() {
        return programari;
    }

    public AdresaCabinet getAdresa() {
        return adresa;
    }

    @Override
    public String toString(){
        String output = "-- Detalii CABINET --\n";
        if(doctori.size() == 0){
            output += "Nu exista doctori inca. \n";
        }
        for(Doctor doctor : doctori) {
            output += doctor.toString() + "\n";
        }
        if(clienti.size() == 0){
            output += "Nu exista clienti inca. \n";
        }
        for(Client client : clienti){
            output += client.toString() + "\n";
        }
        int i = 1;
        for(Programare programare : programari) {

            output += i + "." + "\n" + programare.toString() + "\n";
            i++;
        }
        if(adresa != null) {
            output += "Adresa cabinet: \n" + this.adresa.toString() + "\n\n";
        }
        else{
            output += "Nu exista inca o adresa pentru cabinet. \n\n";
        }
        return output;
    }

    public void adaugaClient(Client client_nou){
        clienti.add(client_nou);
    }

    public void adaugaDoctor(Doctor doctor_nou){
        doctori.add(doctor_nou);
    }

    public void adaugaProgramare(Programare programare){
        programari.add(programare);
    }

    public boolean existaClient(String nume_client){
        for(Client client : clienti){
            if(nume_client.equals(client.getNume())){
                return true;
            }
        }
        return  false;
    }

    public boolean existaDoctor(String nume_doctor){
        for(Doctor doctor : doctori){
            if(nume_doctor.equals(doctor.getNume())){
                return true;
            }
        }
        return  false;
    }

    public void stergeClient(String nume_client) {
        for(Client client : clienti){
            if(nume_client.equals(client.getNume())){
                 clienti.remove(client);
                 break;
            }
        }

        List<Programare> copie_programari = new ArrayList<Programare>(programari);
        for(Programare prog : copie_programari){
            if(nume_client.equals(prog.getClient().getNume())){
                programari.remove(prog);
            }
        }
    }

    public void stergeDoctor(String nume_doctor) {
        for(Doctor doctor : doctori){
            if(nume_doctor.equals(doctor.getNume())){
                doctori.remove(doctor);
                break;
            }
        }

        List<Programare> copie_programari = new ArrayList<Programare>(programari);
        for(Programare prog : copie_programari){
            if(nume_doctor.equals(prog.getDoctor().getNume())){
                programari.remove(prog);
            }
        }
    }

    public Client gasesteClient(String nume_client){
        for(Client client : clienti){
            if(nume_client.equals(client.getNume())){
                return client;
            }
        }
        return null;
    }

    public Doctor gasesteDoctor(String nume_doctor){
        for(Doctor doctor : doctori){
            if(nume_doctor.equals(doctor.getNume())){
                return doctor;
            }
        }
        return null;
    }

    public void adaugaAfectiune(String nume_client, String afectiune){
        for(Client client : clienti){
            if(nume_client.equals(client.getNume())){
                client.adaugaAfectiune(afectiune);
                break;
            }
        }
    }

    public void afisarePacientiCuAfectiune(String afectiune){
        for(Client client : clienti){
            if(client.getAfectiuni().contains(afectiune)){
                System.out.print(client.getNume() + "\n");
            }
        }
        System.out.print("\n");
    }

    public List<Programare> programariClient(String nume_client){
        List<Programare> prog = new ArrayList<Programare>();

        for(Programare p : programari){
            if(nume_client.equals(p.getClient().getNume())){
                prog.add(p);
            }
        }
        return prog;
    }

    public Programare cautaProgramare(Doctor doctor, Client client, Date data, String ora){
        for(Programare p : programari){
            if(doctor == p.getDoctor() && client == p.getClient() && data.compareTo(p.getData()) == 0 && ora.equals(p.getOra())){
                return p;
            }
        }
        return null;
    }

    public void stergeProgramare(Programare p){
        programari.remove(p);
    }

    public void modificaProgramare(Programare p){
        Scanner console = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print("Data noua: ");
        String data = console.nextLine();
        Date d = new Date();
        try {
            d = sdf.parse(data);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        System.out.print("Ora noua: ");
        String ora = console.nextLine();

        p.setData(d);
        p.setOra(ora);
    }
}
