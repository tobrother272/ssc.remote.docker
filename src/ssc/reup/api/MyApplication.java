/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ssc.reup.api.Controller.MyFileUtils;

/**
 *
 * @author PC
 */
public class MyApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
       try {
            MyFileUtils.createFile("log.txt");
            if (new File("log.txt").exists()) {
                PrintStream printStream = new PrintStream(new FileOutputStream("log.txt"));
                System.setOut(printStream);
                System.setErr(printStream);
            }
        } catch (Exception e) {
        }
        for (String string : getParameters().getRaw()) {
            System.out.println("string " + string);
            if (string.startsWith("port:")) {
                ToolSetting.getInstance().setPort(Integer.parseInt(string.replaceAll("port:", "")));
            } else if (string.startsWith("profile:")) {
                ToolSetting.getInstance().setProfile(string.replaceAll("profile:", ""));
            } else if (string.startsWith("useragent:")) {
                ToolSetting.getInstance().setProfile(string.replaceAll("useragent:", ""));
            } else if (string.startsWith("proxy:")) {
                ToolSetting.getInstance().setProfile(string.replaceAll("proxy:", ""));
            } else if (string.startsWith("host:")) {
                ToolSetting.getInstance().setHost(string.replaceAll("host:", ""));
            }
        }
       
        stage.setTitle("SSC Account Manager " + ToolSetting.getInstance().getHost() + ":" + ToolSetting.getInstance().getPort());
        Parent root = FXMLLoader.load(getClass().getResource("/ssc/reup/api/Fxml/FXMLMain.fxml"));
        //String uri = Paths.get(System.getProperty("user.dir") + "\\assets\\Styles.css").toUri().toString();
        //root.getStylesheets().add(uri);
        //stage.initStyle(StageStyle.TRANSPARENT);
        Scene scene = new Scene(root);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        scene.getWindow().centerOnScreen();
    }
    
}
