package MainSiServiciu;

import CabinetMedical.CabinetMedical;
import CabinetMedical.AdresaCabinet;
import Persoana.Client;
import Persoana.Doctor;
import Programare.Programare;
import Programare.ProgramareCuPlata;
import Programare.ProgramareCuAsigurare;
import ServiciiCSV.CSVReader;
import ServiciiCSV.CSVWriter;
import ServiciiCSV.ServiciuAudit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiciuMain {
    final private CabinetMedical cabinet;
    final private Scanner console;
    CSVReader csvReader;
    CSVWriter csvWriter;
    ServiciuAudit serviciuAudit;

    public ServiciuMain(){
        this.cabinet = new CabinetMedical();
        this.console = new Scanner(System.in);
        this.csvReader = CSVReader.getInstance();
        this.csvWriter = CSVWriter.getInstance();
        this.serviciuAudit = ServiciuAudit.getInstance();
    }

    //metoda pentru a incarca fisierele CSV
    public void incarcaCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // incarcare clienti
        String file = "CSVFiles/clienti.csv";
        List<String[]> list = csvReader.readCSVFile(file);
        for(String[] line : list){
            int nr_afectiuni = Integer.parseInt(line[2]);
            Set<String> afectiuni = new HashSet<String>();
            for(var j = 1; j <= nr_afectiuni; j++){
                afectiuni.add(line[j+2]);
            }
            Client client_nou = new Client(line[0], line[1], afectiuni);
            this.cabinet.adaugaClient(client_nou);
        }

        // incarcare doctori
        file = "CSVFiles/doctori.csv";
        list = csvReader.readCSVFile(file);
        for(String[] line : list){
            Doctor doctor_nou = new Doctor(line[0], line[1], line[2]);
            this.cabinet.adaugaDoctor(doctor_nou);
        }

        // incarcare programari cu asigurare
        file = "CSVFiles/programariCuAsigurare.csv";
        list = csvReader.readCSVFile(file);
        for(String[] line : list){
            Date d = new Date();
            try {
                d = sdf.parse(line[2]);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            ProgramareCuAsigurare prog_noua = new ProgramareCuAsigurare(
                    this.cabinet.gasesteDoctor(line[0]), this.cabinet.gasesteClient(line[1]), d, line[3],
                    Integer.parseInt(line[4]), line[5], line[6]);
            this.cabinet.adaugaProgramare(prog_noua);
        }

        // incarcare programari cu plata
        file = "CSVFiles/programariCuPlata.csv";
        list = csvReader.readCSVFile(file);
        for(String[] line : list){
            Date d = new Date();
            try {
                d = sdf.parse(line[2]);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            ProgramareCuPlata prog_noua = new ProgramareCuPlata(
                    this.cabinet.gasesteDoctor(line[0]), this.cabinet.gasesteClient(line[1]), d, line[3],
                    Integer.parseInt(line[4]), Integer.parseInt(line[5]), line[6]);
            this.cabinet.adaugaProgramare(prog_noua);
        }

        // incarcare adresa
        file = "CSVFiles/adresa.csv";
        list = csvReader.readCSVFile(file);
        String[] line = list.get(0);
        AdresaCabinet adresa = new AdresaCabinet(line[0], line[1], Integer.parseInt(line[2]));
        this.cabinet.setAdresa(adresa);
    }

    // metoda pentru a afisa meniul cu actiuni
    public void afisareMeniu(){
        String meniu = "~~~~~~~ MENIU - Cabinet Medical ~~~~~~~" + "\n";
        meniu += "0. Afisare detalii cabinet" + "\n";
        meniu += "1. Adaugare client" + "\n";
        meniu += "2. Adaugare doctor" + "\n";
        meniu += "3. Adaugare programare" + "\n";
        meniu += "4. Stergere client" + "\n";
        meniu += "5. Stergere doctor" + "\n";
        meniu += "6. Stergere programare" + "\n";
        meniu += "7. Modificare data si ora programare" + "\n";
        meniu += "8. Adaugare afectiune pentru un pacient" + "\n";
        meniu += "9. Afisare istoric pacient" + "\n";
        meniu += "10. Afisare toti pacientii cu o afectiune data" + "\n";
        meniu += "11. Setare adresa cabinet" + "\n";
        meniu += "12. EXIT" + "\n";

        System.out.println(meniu);
    }

    public void afisareCabinet(){
        System.out.print(cabinet.toString());

        serviciuAudit.scrieAudit("afisareCabinet");
    }

    // metoda pentru a adauga un client
    public void adaugaClient() {
        System.out.print("Nume client: ");
        String nume = console.nextLine();

        System.out.print("Data nastere: ");
        String data = console.nextLine();

        Set<String> afectiuni = new HashSet<String>();

        System.out.print("Nr afectiuni cunoscute: ");
        int nr_afectiuni = console.nextInt();

        String afec;
        afec = console.nextLine();
        for(int i = 1; i <= nr_afectiuni; i++){
            System.out.print("Afectiunea "+i+": ");
            afec = console.nextLine();
            afectiuni.add(afec);
        }

        Client client_nou = new Client(nume, data, afectiuni);
        this.cabinet.adaugaClient(client_nou);
        System.out.print("Clientul a fost adaugat cu succes! \n\n");

        csvWriter.scrieCSVFile(client_nou);
        serviciuAudit.scrieAudit("adaugaClient");
    }

    //metoda pentru a adauga un doctor
    public void adaugaDoctor() {
        System.out.print("Nume doctor: ");
        String nume = console.nextLine();

        System.out.print("Data nastere: ");
        String data = console.nextLine();

        System.out.print("Specialitate: ");
        String specialitate = console.nextLine();

        Doctor doctor_nou = new Doctor(nume, data, specialitate);
        this.cabinet.adaugaDoctor(doctor_nou);
        System.out.print("Doctorul a fost adaugat cu succes! \n\n");

        csvWriter.scrieCSVFile(doctor_nou);
        serviciuAudit.scrieAudit("adaugaDoctor");
    }

    //metoda pentru a adauga o programare
    public void adaugaProgramare() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Nume doctor: ");
        String nume_doctor = console.nextLine();

        boolean verif = cabinet.existaDoctor(nume_doctor);
        Doctor doctor = cabinet.gasesteDoctor(nume_doctor);

        if(verif) {
            System.out.print("Nume client: ");
            String nume_client = console.nextLine();

            verif = cabinet.existaClient(nume_client);
            Client client = cabinet.gasesteClient(nume_client);

            if(verif) {
                System.out.print("Data programare: ");
                String data = console.nextLine();
                Date d = new Date();
                try {
                    d = sdf.parse(data);
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                System.out.print("Ora programare: ");
                String ora = console.nextLine();

                System.out.print("Nr cabinet: ");
                int nr_cabinet = console.nextInt();

                System.out.print("Tipul programarii (1 - Cu plata; 2 - Cu asigurare): ");
                int tip = console.nextInt();

                if(tip == 1){
                    System.out.print("Cost: ");
                    int cost = console.nextInt();

                    String aux = console.nextLine();
                    System.out.print("Modalitate plata: ");
                    String modalitate = console.nextLine();

                    ProgramareCuPlata prog_noua = new ProgramareCuPlata(doctor, client, d, ora, nr_cabinet, cost, modalitate);
                    this.cabinet.adaugaProgramare(prog_noua);
                    csvWriter.scrieCSVFile(prog_noua);
                }
                else {
                    String aux = console.nextLine();
                    System.out.print("Cod asigurare: ");
                    String cod = console.nextLine();

                    //aux = console.nextLine();
                    System.out.print("Tip asigurare: ");
                    String tip_asigurare = console.nextLine();

                    ProgramareCuAsigurare prog_noua = new ProgramareCuAsigurare(doctor, client, d, ora, nr_cabinet, cod, tip_asigurare);
                    this.cabinet.adaugaProgramare(prog_noua);
                    csvWriter.scrieCSVFile(prog_noua);
                }

                System.out.print("\n");
                System.out.print("Programarea a fost adaugata cu succes! \n\n");
            }
            else{
                System.out.print("Nu exista niciun client cu numele dat! \n\n");
            }
        }
        else{
            System.out.print("Nu exista niciun doctor cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("adaugaProgramare");
    }

    //metoda pentru a sterge un client
    public void stergeClient(){
        System.out.print("Nume client: ");
        String nume_client = console.nextLine();

        boolean verif = cabinet.existaClient(nume_client);
        if(verif){
            cabinet.stergeClient(nume_client);
            System.out.print("Clientul a fost sters cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun client cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("stergeClient");
    }

    //metoda pentru a sterge un doctor
    public void stergeDoctor(){
        System.out.print("Nume doctor: ");
        String nume_doctor = console.nextLine();

        boolean verif = cabinet.existaDoctor(nume_doctor);
        if(verif){
            cabinet.stergeDoctor(nume_doctor);
            System.out.print("Doctorul a fost sters cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun doctor cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("stergeDoctor");
    }

    //metoda pentru a sterge o programare
    public void stergeProgramare(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Nume doctor: ");
        String nume_doctor = console.nextLine();

        boolean verif = cabinet.existaDoctor(nume_doctor);
        Doctor doctor = cabinet.gasesteDoctor(nume_doctor);

        if(verif) {
            System.out.print("Nume client: ");
            String nume_client = console.nextLine();

            verif = cabinet.existaClient(nume_client);
            Client client = cabinet.gasesteClient(nume_client);

            if(verif) {
                System.out.print("Data programare: ");
                String data = console.nextLine();
                Date d = new Date();
                try {
                    d = sdf.parse(data);
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                System.out.print("Ora programare: ");
                String ora = console.nextLine();

                Programare prog = cabinet.cautaProgramare(cabinet.gasesteDoctor(nume_doctor), cabinet.gasesteClient(nume_client), d, ora);

                if(prog != null) {
                    this.cabinet.stergeProgramare(prog);
                    System.out.print("\n");
                    System.out.print("Programarea a fost stearsa cu succes! \n\n");
                }
                else{
                    System.out.print("Programarea nu a fost gasita! \n\n");
                }
            }
            else{
                System.out.print("Nu exista niciun client cu numele dat! \n\n");
            }
        }
        else{
            System.out.print("Nu exista niciun doctor cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("stergeProgramare");
    }

    //metoda pentru a adauga o afectiune
    public void adaugaAfectiune(){
        System.out.print("Nume client: ");
        String nume_client = console.nextLine();

        boolean verif = cabinet.existaClient(nume_client);
        if(verif){
            System.out.print("Afectiune noua: ");
            String afectiune = console.nextLine();
            cabinet.adaugaAfectiune(nume_client, afectiune);
            System.out.print("Afectiunea a fost adaugata cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun client cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("adaugaAfectiune");
    }

    //metoda pentru a afisa istoricul unui pacient
    public void afisareIstoric(){
        System.out.print("Nume client: ");
        String nume_client = console.nextLine();

        boolean verif = cabinet.existaClient(nume_client);
        if(verif){
            System.out.print("\nAfectiuni: ");
            for(String afec : cabinet.gasesteClient(nume_client).getAfectiuni()){
                System.out.print(afec + " ");
            }

            System.out.print("\n\nProgramari: \n");
            for(Programare prog : cabinet.programariClient(nume_client)){
                System.out.print(prog + "\n");
            }
        }
        else{
            System.out.print("Nu exista niciun client cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("afisareIstoric");
    }

    //metoda pentru a afisa toti pacientii cu o afectiune data
    public void afisarePacientiCuAfectiune(){
        System.out.print("Afectiune: ");
        String afectiune = console.nextLine();

        cabinet.afisarePacientiCuAfectiune(afectiune);

        serviciuAudit.scrieAudit("afisarePacientiCuAfectiune");
    }

    //metoda pentru a modifica o programare
    public void modificaProgramare(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.print("Nume doctor: ");
        String nume_doctor = console.nextLine();

        boolean verif = cabinet.existaDoctor(nume_doctor);
        Doctor doctor = cabinet.gasesteDoctor(nume_doctor);

        if(verif) {
            System.out.print("Nume client: ");
            String nume_client = console.nextLine();

            verif = cabinet.existaClient(nume_client);
            Client client = cabinet.gasesteClient(nume_client);

            if(verif) {
                System.out.print("Data programare: ");
                String data = console.nextLine();
                Date d = new Date();
                try {
                    d = sdf.parse(data);
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                System.out.print("Ora programare: ");
                String ora = console.nextLine();

                Programare prog = cabinet.cautaProgramare(cabinet.gasesteDoctor(nume_doctor), cabinet.gasesteClient(nume_client), d, ora);

                if(prog != null) {
                    this.cabinet.modificaProgramare(prog);
                    System.out.print("\n");
                    System.out.print("Programarea a fost modificata cu succes! \n\n");
                }
                else{
                    System.out.print("Programarea nu a fost gasita! \n\n");
                }
            }
            else{
                System.out.print("Nu exista niciun client cu numele dat! \n\n");
            }
        }
        else{
            System.out.print("Nu exista niciun doctor cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("modificaProgramare");
    }

    //metoda pentru a seta adresa cabinetului
    public void setareAdresa(){
        System.out.print("Oras: ");
        String oras = console.nextLine();

        System.out.print("Strada: ");
        String strada = console.nextLine();

        System.out.print("Numar: ");
        Integer nr = console.nextInt();
        System.out.print("\n");

        cabinet.getAdresa().setOras(oras);
        cabinet.getAdresa().setStrada(strada);
        cabinet.getAdresa().setNumar(nr);

        serviciuAudit.scrieAudit("setareAdresa");
    }
}