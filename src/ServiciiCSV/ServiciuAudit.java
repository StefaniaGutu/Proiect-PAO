package ServiciiCSV;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class ServiciuAudit {
    private ServiciuAudit(){}
    private static ServiciuAudit instance = null;

    public static ServiciuAudit getInstance(){
        if(instance == null){
            instance = new ServiciuAudit();
        }
        return instance;
    }

    public void scrieAudit(String metoda){
        Timestamp time = new Timestamp(System.currentTimeMillis());

        try(var out = new BufferedWriter(new FileWriter("CSVFiles/audit.csv", true))){
            out.write(metoda +","+time.toString() + "\n");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

