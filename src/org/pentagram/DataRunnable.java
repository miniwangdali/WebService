package org.pentagram;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


/**
 * Created by wang_ on 2016/9/26.
 */
public class DataRunnable implements Runnable {
    Socket socket = null;
    BufferedReader buffer = null;

    public DataRunnable(Socket socket) {
        this.socket = socket;
        try {
            buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        StringBuilder result = new StringBuilder();
        String content = null;
        while((content = read()) != null){
            result.append(content);
            result.append("\n");
        }
        //result.append("Get!");
        System.out.println(result);
        try {
//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write(result.toString().getBytes("utf-8"));
//            outputStream.flush();
//            outputStream.close();
            buffer.close();
            socket.close();

            DBHelper database = new DBHelper();
            String xml = generateSQLbyXML(result.toString());
            if(xml != null){
                ResultSet resultSet = database.query(xml);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String read(){
        try {
            return buffer.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateSQLbyXML(String xml){
        String sql = null;
        try{
            StringReader stringReader = new StringReader(xml);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(stringReader));
            Element root = document.getDocumentElement();
            sql = "INSERT INTO `msband`.`band_data_table` " +
                    "(`BandSerialId`, `OwnerName`, `Date`, `Time`, `HeartRate`, `AccelerationX`, `AccelerationY`, `AccelerationZ`, `FlightsAscendedToday`, `FlightsAscended`, `FlightsDescended`, `AltimeterRate`, `SteppingGain`, `SteppingLost`, `StepsAscended`, `StepsDescended`, `TotalGainToday`, `TotalGain`, `TotalLoss`, `Brightness`, `AirPressure`, `Temperature`, `CaloriesToday`, `CaloriesTotal`, `ContactState`, `MotionType`, `Pace`, `Speed`, `DistanceToday`, `TotalDistance`, `Resistance`, `AngularVelocityX`, `AngularVelocityY`, `AngularVelocityZ`, `StepToday`, `StepTotal`, `RRInterval`, `SkinTemperature`, `UVIndexLevel`) VALUES " +
                    "('009691360849', 'tester', '1970-01-01', '13:34:43', '" + root.getElementsByTagName("HeartRate").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Accelerometer").item(0).getChildNodes().item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Accelerometer").item(0).getChildNodes().item(1).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Accelerometer").item(0).getChildNodes().item(2).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("FlightsAscendedToday").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("FlightsAscended").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("FlightsDescended").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Rate").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("SteppingGain").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("SteppingLoss").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("StepsAscended").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("StepsDescended").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("TotalGainToday").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("TotalGain").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("TotalLoss").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("AmbientLight").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("AirPressure").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Temperature").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Calories").item(0).getChildNodes().item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Calories").item(0).getChildNodes().item(1).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("ContactState").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("MotionType").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Pace").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Speed").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("DistanceToday").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("TotalDistance").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Gsr").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Gyroscope").item(0).getChildNodes().item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Gyroscope").item(0).getChildNodes().item(1).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Gyroscope").item(0).getChildNodes().item(2).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Pedometer").item(0).getChildNodes().item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("Pedometer").item(0).getChildNodes().item(1).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("RRInterval").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("SkinTemperature").item(0).getTextContent() + "', " +
                    "'" + root.getElementsByTagName("UVIndexValue").item(0).getTextContent() + "');";
            System.out.println(sql);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sql;
    }
}
