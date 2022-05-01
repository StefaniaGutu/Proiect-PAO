package MainSiServiciu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServiciuMain serviciu = new ServiciuMain();

        serviciu.incarcaCSV();

        serviciu.afisareMeniu();

        Scanner console  = new Scanner(System.in);

        int optiune = 1;
        while (optiune != 12){
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
    }
}
