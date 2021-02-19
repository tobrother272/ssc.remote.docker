
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import ssc.reup.api.Controller.MyFileUtils;

/**
 * @author inet
 */
public class SscReupApi extends Application {

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
        ToolSetting.getInstance().setPort(Integer.parseInt(getParameters().getNamed().get("port")));

        ToolSetting.getInstance().setProfile(getParameters().getNamed().get("profile"));

        ToolSetting.getInstance().setHost(getParameters().getNamed().get("host"));
        
        ToolSetting.getInstance().setName(getParameters().getNamed().get("name"));

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

    /**
     * @param args the command line arguments
     */
    static void main(String[] args) {
    

        launch(args);
    }

}
