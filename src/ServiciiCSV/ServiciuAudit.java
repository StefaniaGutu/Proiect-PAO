package ServiciiCSV;

import DatabaseConfig.DatabaseConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public void adaugaAuditDB(String metoda) throws SQLException {
        Timestamp time = new Timestamp(System.currentTimeMillis());

        Connection connection = DatabaseConfig.getConnection();

        String query = "insert into audit values(null,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        //preparedStatement.setInt(1, 10);
        preparedStatement.setString(1,metoda);
        preparedStatement.setTimestamp(2, time);

        preparedStatement.executeUpdate();
    }
}

