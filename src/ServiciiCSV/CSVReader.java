package ServiciiCSV;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private CSVReader(){}
    private static CSVReader instance = null;

    public static CSVReader getInstance(){
        if(instance == null){
            instance = new CSVReader();
        }
        return instance;
    }

    public List<String[]> readCSVFile(String file){
        List<String[]> list = new ArrayList<>();
        try(var in = new BufferedReader(new FileReader(file))){
            String line = "";

            while((line = in.readLine()) != null){
                String[] fields = line.replaceAll(" ", "").split(",");
                list.add(fields);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return list;
    }
}
