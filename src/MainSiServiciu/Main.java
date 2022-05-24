package MainSiServiciu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner console  = new Scanner(System.in);
        System.out.print("Varianta dorita (1 - CSV, 2 - DB): ");

        int var = console.nextInt();

        if (var == 1) { //s-a ales varianta CSV
            ServiciuMainCSV serviciu = new ServiciuMainCSV();

            serviciu.incarcaCSV();

            serviciu.afisareMeniu();

            int optiune = 1;
            while (optiune != 12) {
                System.out.print("Optiune: ");
                optiune = console.nextInt();

                switch (optiune) {
                    case 0 -> serviciu.afisareCabinet();
                    case 1 -> serviciu.adaugaClient();
                    case 2 -> serviciu.adaugaDoctor();
                    case 3 -> serviciu.adaugaProgramare();
                    case 4 -> serviciu.stergeClient();
                    case 5 -> serviciu.stergeDoctor();
                    case 6 -> serviciu.stergeProgramare();
                    case 7 -> serviciu.modificaProgramare();
                    case 8 -> serviciu.adaugaAfectiune();
                    case 9 -> serviciu.afisareIstoric();
                    case 10 -> serviciu.afisarePacientiCuAfectiune();
                    case 11 -> serviciu.setareAdresa();
                    case 12 -> System.out.print("EXIT! \n");
                    default -> System.out.print("Ati introdus o optiune invalida! \n\n");
                }
            }
        } else if (var == 2) { //s-a ales varianta BD
            ServiciuMainDB serviciu = new ServiciuMainDB();

            serviciu.incarcaDB();
            serviciu.afisareMeniu();

            int optiune = 1;
            while (optiune != 13) {
                System.out.print("Optiune: ");
                optiune = console.nextInt();
                try {
                    switch (optiune) {
                        case 0 -> serviciu.afisareCabinet();
                        case 1 -> serviciu.adaugaClient();
                        case 2 -> serviciu.adaugaDoctor();
                        case 3 -> serviciu.adaugaProgramare();
                        case 4 -> serviciu.stergeClient();
                        case 5 -> serviciu.stergeDoctor();
                        case 6 -> serviciu.stergeProgramare();
                        case 7 -> serviciu.modificaProgramare();
                        case 8 -> serviciu.modificaSpecialitate();
                        case 9 -> serviciu.adaugaAfectiune();
                        case 10 -> serviciu.afisareIstoric();
                        case 11 -> serviciu.afisarePacientiCuAfectiune();
                        case 12 -> serviciu.setareAdresa();
                        case 13 -> System.out.print("EXIT! \n");
                        default -> System.out.print("Ati introdus o optiune invalida! \n\n");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            System.out.print("Ati introdus o varianta invalida! \n\n");
        }
    }
}
