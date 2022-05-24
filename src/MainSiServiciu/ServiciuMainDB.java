package MainSiServiciu;

import CabinetMedical.CabinetMedical;
import DatabaseConfig.DatabaseConfig;
import Persoana.Client;
import Persoana.Doctor;
import Programare.Programare;
import Programare.ProgramareCuAsigurare;
import Programare.ProgramareCuPlata;
import ServiciiCSV.ServiciuAudit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiciuMainDB {
    final private CabinetMedical cabinet;
    final private Scanner console;
    ServiciuAudit serviciuAudit;
    final private Connection connection;

    public ServiciuMainDB(){
        this.cabinet = new CabinetMedical();
        this.console = new Scanner(System.in);
        DatabaseConfig db = new DatabaseConfig();
        this.connection = db.getConnection();
        this.serviciuAudit = ServiciuAudit.getInstance();
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
        meniu += "8. Modificare specialitate doctor" + "\n";
        meniu += "9. Adaugare afectiune pentru un pacient" + "\n";
        meniu += "10. Afisare istoric pacient" + "\n";
        meniu += "11. Afisare toti pacientii cu o afectiune data" + "\n";
        meniu += "12. Setare adresa cabinet" + "\n";
        meniu += "13. EXIT" + "\n";

        System.out.println(meniu);
    }

    //metoda pentru a incarca datele din baza de date
    public void incarcaDB() throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // incarcare clienti
        String query = "select * from clienti";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet res = preparedStatement.executeQuery();

        while (res.next()) {
            String nume = res.getString("nume");
            String data = res.getString("data_nastere");
//            Date d = new Date();
//            try {
//                d = sdf.parse(data);
//            } catch (ParseException e) {
//
//                e.printStackTrace();
//            }

            String afectiuni = res.getString("afectiuni");

            Set<String> afec  = new HashSet<String>();
            for(String s : afectiuni.split(",")){
                afec.add(s);
            }

            Client client_nou = new Client(nume, data, afec);
            this.cabinet.adaugaClient(client_nou);

        }

        // incarcare doctori
        String query2 = "select * from doctori";

        preparedStatement = connection.prepareStatement(query2);
        res = preparedStatement.executeQuery();

        while (res.next()) {
            String nume = res.getString("nume");
            String data = res.getString("data_nastere");

            String specialitate = res.getString("specialitate");

            Doctor doctor_nou = new Doctor(nume, data, specialitate);
            this.cabinet.adaugaDoctor(doctor_nou);
        }

        // incarcare programari cu asigurare
        String query3 = "select * from programari_cu_asigurare";

        preparedStatement = connection.prepareStatement(query3);
        res = preparedStatement.executeQuery();

        while (res.next()) {
            String nume_doctor = res.getString("nume_doctor");
            String nume_client = res.getString("nume_client");
            String data = res.getString("data");
            Date d = new Date();
            try {
                d = sdf.parse(data);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            String ora = res.getString("ora");
            int nr_cabinet = res.getInt("nr_cabinet");
            String cod_asigurare = res.getString("cod_asigurare");
            String tip = res.getString("tip");

            ProgramareCuAsigurare prog_noua = new ProgramareCuAsigurare(
                    this.cabinet.gasesteDoctor(nume_doctor), this.cabinet.gasesteClient(nume_client), d, ora,
                    nr_cabinet, cod_asigurare, tip);
            this.cabinet.adaugaProgramare(prog_noua);
        }

        // incarcare programari cu plata
        String query4 = "select * from programari_cu_plata";

        preparedStatement = connection.prepareStatement(query4);
        res = preparedStatement.executeQuery();

        while (res.next()) {
            String nume_doctor = res.getString("nume_doctor");
            String nume_client = res.getString("nume_client");
            String data = res.getString("data");
            Date d = new Date();
            try {
                d = sdf.parse(data);
            } catch (ParseException e) {

                e.printStackTrace();
            }
            String ora = res.getString("ora");
            int nr_cabinet = res.getInt("nr_cabinet");
            int cost = res.getInt("cost");
            String modalitate_plata = res.getString("modalitate_plata");

            ProgramareCuPlata prog_noua = new ProgramareCuPlata(
                    this.cabinet.gasesteDoctor(nume_doctor), this.cabinet.gasesteClient(nume_client), d, ora,
                    nr_cabinet, cost, modalitate_plata);
            this.cabinet.adaugaProgramare(prog_noua);
        }
    }

    public void afisareCabinet() throws SQLException {
        System.out.print(cabinet.toString());

        serviciuAudit.adaugaAuditDB("afisareCabinet");
    }

    // metoda pentru a adauga un client
    public void adaugaClient() throws SQLException {
        System.out.print("Nume client: ");
        String nume = console.nextLine();

        System.out.print("Data nastere: ");
        String data = console.nextLine();

        Set<String> afectiuni = new HashSet<String>();

        System.out.print("Nr afectiuni cunoscute: ");
        int nr_afectiuni = console.nextInt();

        String afec;
        String afectiuni_join = "";
        afec = console.nextLine();
        for(int i = 1; i <= nr_afectiuni; i++){
            System.out.print("Afectiunea "+i+": ");
            afec = console.nextLine();
            afectiuni.add(afec);
            afectiuni_join = afectiuni_join + "," + afec;
        }

        Client client_nou = new Client(nume, data, afectiuni);
        this.cabinet.adaugaClient(client_nou);
        System.out.print("Clientul a fost adaugat cu succes! \n\n");

        String query = "insert into clienti values(null,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        //preparedStatement.setInt(1, 10);
        preparedStatement.setString(1,nume);
        preparedStatement.setString(2, data);
        preparedStatement.setString(3, afectiuni_join.substring(1));

        preparedStatement.executeUpdate();

        serviciuAudit.adaugaAuditDB("adaugaClient");
    }

    //metoda pentru a adauga un doctor
    public void adaugaDoctor() throws SQLException {
        System.out.print("Nume doctor: ");
        String nume = console.nextLine();

        System.out.print("Data nastere: ");
        String data = console.nextLine();

        System.out.print("Specialitate: ");
        String specialitate = console.nextLine();

        Doctor doctor_nou = new Doctor(nume, data, specialitate);
        this.cabinet.adaugaDoctor(doctor_nou);
        System.out.print("Doctorul a fost adaugat cu succes! \n\n");

        String query = "insert into doctori values(null,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        //preparedStatement.setInt(1, 10);
        preparedStatement.setString(1,nume);
        preparedStatement.setString(2, data);
        preparedStatement.setString(3, specialitate);

        preparedStatement.executeUpdate();
        serviciuAudit.adaugaAuditDB("adaugaDoctor");
    }

    //metoda pentru a adauga o programare
    public void adaugaProgramare() throws SQLException {
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
            //int id_client = gasesteIdClient(nume_client);

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

                    String query = "insert into programari_cu_plata values(null,?,?,?,?,?,?,?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);


                    preparedStatement.setString(1,nume_doctor);
                    preparedStatement.setString(2, nume_client);
                    preparedStatement.setString(3, data);
                    preparedStatement.setString(4, ora);
                    preparedStatement.setInt(5, nr_cabinet);
                    preparedStatement.setInt(6, cost);
                    preparedStatement.setString(7, modalitate);

                    preparedStatement.executeUpdate();
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

                    String query = "insert into programari_cu_asigurare values(null,?,?,?,?,?,?,?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);


                    preparedStatement.setString(1,nume_doctor);
                    preparedStatement.setString(2, nume_client);
                    preparedStatement.setString(3, data);
                    preparedStatement.setString(4, ora);
                    preparedStatement.setInt(5, nr_cabinet);
                    preparedStatement.setString(6, cod);
                    preparedStatement.setString(7, tip_asigurare);

                    preparedStatement.executeUpdate();
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

        serviciuAudit.adaugaAuditDB("adaugaProgramare");
    }

    //metoda pentru a sterge un client
    public void stergeClient() throws SQLException {
        System.out.print("Nume client: ");
        String nume_client = console.nextLine();

        boolean verif = cabinet.existaClient(nume_client);
        if(verif){
            cabinet.stergeClient(nume_client);

            String query = "delete from clienti where nume = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nume_client);

            preparedStatement.executeUpdate();

            //stergem si toate programarile acestui client
            String query2 = "delete from programari_cu_plata where nume_client = ?";

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, nume_client);

            preparedStatement.executeUpdate();

            String query3 = "delete from programari_cu_asigurare where nume_client = ?";

            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.setString(1, nume_client);

            preparedStatement.executeUpdate();

            System.out.print("Clientul a fost sters cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun client cu numele dat! \n\n");
        }

        serviciuAudit.adaugaAuditDB("stergeClient");
    }

    //metoda pentru a sterge un doctor
    public void stergeDoctor() throws SQLException {
        System.out.print("Nume doctor: ");
        String nume_doctor = console.nextLine();

        boolean verif = cabinet.existaDoctor(nume_doctor);
        if(verif){
            cabinet.stergeDoctor(nume_doctor);

            String query = "delete from doctori where nume = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nume_doctor);

            preparedStatement.executeUpdate();

            //stergem si toate programarile acestui client
            String query2 = "delete from programari_cu_plata where nume_doctor = ?";

            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, nume_doctor);

            preparedStatement.executeUpdate();

            String query3 = "delete from programari_cu_asigurare where nume_doctor = ?";

            preparedStatement = connection.prepareStatement(query3);
            preparedStatement.setString(1, nume_doctor);

            preparedStatement.executeUpdate();
            System.out.print("Doctorul a fost sters cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun doctor cu numele dat! \n\n");
        }

        serviciuAudit.adaugaAuditDB("stergeDoctor");
    }

    //metoda pentru a sterge o programare
    public void stergeProgramare() throws SQLException {
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

                    //stergem programarea si din baza de date
                    if(prog instanceof ProgramareCuAsigurare){
                        String query = "delete from programari_cu_asigurare where nume_doctor = ? and nume_client = ? and data = ? and ora = ?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nume_doctor);
                        preparedStatement.setString(2, nume_client);
                        preparedStatement.setString(3, data);
                        preparedStatement.setString(4, ora);

                        preparedStatement.executeUpdate();
                    } else if (prog instanceof ProgramareCuPlata) {
                        String query = "delete from programari_cu_plata where nume_doctor = ? and nume_client = ? and data = ? and ora = ?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, nume_doctor);
                        preparedStatement.setString(2, nume_client);
                        preparedStatement.setString(3, data);
                        preparedStatement.setString(4, ora);

                        preparedStatement.executeUpdate();
                    }

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

        serviciuAudit.adaugaAuditDB("stergeProgramare");
    }

    //metoda pentru a modifica o programare
    public void modificaProgramare() throws SQLException {
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
                    Scanner console = new Scanner(System.in);
                    System.out.print("Data noua: ");
                    String data_noua = console.nextLine();
                    Date d_noua = new Date();
                    try {
                        d_noua = sdf.parse(data_noua);
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }

                    System.out.print("Ora noua: ");
                    String ora_noua = console.nextLine();

                    prog.setData(d_noua);
                    prog.setOra(ora_noua);

                    //modificam si in baza de date
                    if(prog instanceof ProgramareCuAsigurare){
                        String query = "update programari_cu_asigurare set data = ?, ora = ? where nume_doctor = ? and nume_client = ? and data = ? and ora = ?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, data_noua);
                        preparedStatement.setString(2, ora_noua);
                        preparedStatement.setString(3, nume_doctor);
                        preparedStatement.setString(4, nume_client);
                        preparedStatement.setString(5, data);
                        preparedStatement.setString(6, ora);

                        preparedStatement.executeUpdate();
                    } else if (prog instanceof ProgramareCuPlata) {
                        String query = "update programari_cu_plata set data = ?, ora = ? where nume_doctor = ? and nume_client = ? and data = ? and ora = ?";

                        PreparedStatement preparedStatement = connection.prepareStatement(query);
                        preparedStatement.setString(1, data_noua);
                        preparedStatement.setString(2, ora_noua);
                        preparedStatement.setString(3, nume_doctor);
                        preparedStatement.setString(4, nume_client);
                        preparedStatement.setString(5, data);
                        preparedStatement.setString(6, ora);

                        preparedStatement.executeUpdate();
                    }

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

        serviciuAudit.adaugaAuditDB("modificaProgramare");
    }

    //metoda pentru a modifica specialitatea unui doctor
    public void modificaSpecialitate() throws SQLException {
        System.out.print("Nume doctor: ");
        String nume_doctor = console.nextLine();

        boolean verif = cabinet.existaDoctor(nume_doctor);
        if(verif){
            Doctor doctor = cabinet.gasesteDoctor(nume_doctor);
            System.out.println("Specialitate actuala: " + doctor.getSpecialitate());

            System.out.print("Noua specialitate: ");
            String spec_noua = console.nextLine();

            doctor.setSpecialitate(spec_noua);

            //actualizam si in baza de date
            String query = "update doctori set specialitate = ? where nume = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, spec_noua);
            preparedStatement.setString(2, nume_doctor);

            preparedStatement.executeUpdate();

            System.out.print("Modificarea a fost efectuata cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun doctor cu numele dat! \n\n");
        }

        serviciuAudit.adaugaAuditDB("modificaSpecialitate");
    }

    //metoda pentru a adauga o afectiune
    public void adaugaAfectiune() throws SQLException {
        System.out.print("Nume client: ");
        String nume_client = console.nextLine();

        boolean verif = cabinet.existaClient(nume_client);
        if(verif){
            System.out.print("Afectiune noua: ");
            String afectiune = console.nextLine();
            cabinet.adaugaAfectiune(nume_client, afectiune);

            Client client = cabinet.gasesteClient(nume_client);

            //adaugam afectiunea si in baza de date
            String afectiuni_join = "";
            for(String afec : client.getAfectiuni()){
                afectiuni_join = afectiuni_join + "," + afec;
            }

            String query = "update clienti set afectiuni = ? where nume = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, afectiuni_join.substring(1));
            preparedStatement.setString(2, nume_client);

            preparedStatement.executeUpdate();

            System.out.print("Afectiunea a fost adaugata cu succes! \n\n");
        }
        else{
            System.out.print("Nu exista niciun client cu numele dat! \n\n");
        }

        serviciuAudit.scrieAudit("adaugaAfectiune");
    }

    //metoda pentru a afisa istoricul unui pacient
    public void afisareIstoric() throws SQLException {
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

        serviciuAudit.adaugaAuditDB("afisareIstoric");
    }

    //metoda pentru a afisa toti pacientii cu o afectiune data
    public void afisarePacientiCuAfectiune() throws SQLException {
        System.out.print("Afectiune: ");
        String afectiune = console.nextLine();

        cabinet.afisarePacientiCuAfectiune(afectiune);

        serviciuAudit.adaugaAuditDB("afisarePacientiCuAfectiune");
    }

    //metoda pentru a seta adresa cabinetului
    public void setareAdresa() throws SQLException {
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

        serviciuAudit.adaugaAuditDB("setareAdresa");
    }
}
