package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    JFrame frame;
    JPanel panel;
    JButton button1;
    JButton button2;
//    JTextField textField1;
//    JTextField textField2;
    String city;
    String lon;
    String lat;
    String postcode;
    String jsonData;
    JSONObject object1;
    JSONObject object2;
    String url1;
    final PlaceholderTextField textField1;
    final PlaceholderTextField textField2;
    JTextField outputText;
        String numberAsString;


    //set gui
    public Gui(int frameWidth,int frameHeight){
        //set up gui

        Main main = new Main();





        frame = new JFrame();
        frame.setSize(frameWidth,frameHeight);

        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        //city
        button1 = new JButton("submit");
        button1.setBounds(250,250,100,50);
        panel.add(button1);

        textField1 = new PlaceholderTextField("");
        textField1.setBounds(100,250,100,50);
        textField1.setPlaceholder("City");
        panel.add(textField1);


        //postcode
        button2 = new JButton("submit");
        button2.setBounds(250,150,100,50);
        panel.add(button2);

        textField2 = new PlaceholderTextField("");
        textField2.setPlaceholder("Postcode");
        textField2.setBounds(100,150,100,50);
        panel.add(textField2);

        outputText = new JTextField("");
        outputText.setBounds(70,50,300,50);
        panel.add(outputText);




        frame.setVisible(true);

        //button to fetch api specific to cities
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(textField.getText());
                city = textField1.getText();
                url1 = "http://api.openweathermap.org/data/2.5/weather?q=" + city + ",uk&APPID=f5ec4958320d2bcd3186ed8d573f2951";
                jsonData = main.getApi(url1);
//                System.out.println(jsonData);
                parseDescript();
                parseTemp();

                //display information
                outputText.setText(parseDescript() + " and " + parseTemp());
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postcode = textField2.getText();
                url1 = "http://api.postcodes.io/postcodes/"+ postcode;

                //get postcode api
                jsonData = main.getApi(url1);
//                System.out.println(jsonData);
                parseCoordinates();
                url1 = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&APPID=f5ec4958320d2bcd3186ed8d573f2951";

                //get weather api
                jsonData = main.getApi(url1);
                System.out.println(jsonData);
                parseDescript();
                parseTemp();

                //display information
                outputText.setText(parseDescript() +  " and " + parseTemp());





            }
        });


    }
    public String getCity(){
        return city;
    }


    public String getJsonData(){
        return jsonData;
    }

    //retrieves description of weather in city
    public String parseDescript(){
        object1 = new JSONObject(getJsonData());
        JSONArray weather = object1.getJSONArray("weather");
        JSONObject object2 = weather.getJSONObject(0);
        String Description = object2.optString("description");
        System.out.println(Description);
        return Description;
    }
    //retrieves the temperature
    public String parseTemp(){
        object1 = new JSONObject(getJsonData());
        JSONObject object2 = object1.getJSONObject("main");
        int  temp = object2.optInt("temp");
        System.out.println(temp);
        temp = temp - 273;
        numberAsString = String.valueOf(temp);



        return numberAsString;


    }
    //retrieves the coordinates
    public void parseCoordinates(){
        object1 = new JSONObject(getJsonData());
        JSONObject object2 = object1.getJSONObject("result");
//        System.out.println(object2);
        lon = object2.optString("longitude");
        lat = object2.optString("latitude");
//        System.out.println("longitude: " + lon + " and latitude: " + lat );


    }



    public static void main(String[] args) {
        Gui gui = new Gui(500,500);


    }


}
