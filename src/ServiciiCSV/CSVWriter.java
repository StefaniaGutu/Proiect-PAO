package ServiciiCSV;

import Persoana.Client;
import Persoana.Doctor;
import Programare.ProgramareCuAsigurare;
import Programare.ProgramareCuPlata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private CSVWriter(){}
    private static CSVWriter instance = null;

    public static CSVWriter getInstance(){
        if(instance == null){
            instance = new CSVWriter();
        }
        return instance;
    }

    public void scrieCSVFile(Doctor doctor){
        try(var out = new BufferedWriter(new FileWriter("CSVFiles/doctori.csv", true))){
            out.write("\n" +doctor.convertToCSV());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void scrieCSVFile(Client client){
        try(var out = new BufferedWriter(new FileWriter("CSVFiles/clienti.csv", true))){
            out.write("\n" + client.convertToCSV());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void scrieCSVFile(ProgramareCuPlata programareCuPlata){
        try(var out = new BufferedWriter(new FileWriter("CSVFiles/programariCuPlata.csv", true))){
            out.write("\n" + programareCuPlata.convertToCSV());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void scrieCSVFile(ProgramareCuAsigurare programareCuAsigurare){
        try(var out = new BufferedWriter(new FileWriter("CSVFiles/programariCuAsigurare.csv", true))){
            out.write("\n" + programareCuAsigurare.convertToCSV());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
