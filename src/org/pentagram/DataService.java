package org.pentagram;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang_ on 2016/9/26.
 */
public class DataService {
    public static List<Socket> socketList = null;

    public static void main(String[] args) {

        //String sql = "INSERT INTO `msband`.`band_data_table` (`BandSerialID`, `OwnerName`) VALUES ('009691360849', 'Waqid');";
        socketList = new ArrayList<Socket>();
        //DBHelper database = new DBHelper();

        try{

            //ResultSet result = database.query(sql);
            //result.close();
            ServerSocket service = new ServerSocket(12306);
            while(true){
                Socket socket = service.accept();
                socketList.add(socket);
                new Thread(new DataRunnable(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
