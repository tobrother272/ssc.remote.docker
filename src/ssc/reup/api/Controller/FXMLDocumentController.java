/*
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketOption;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ssc.reup.api.ToolSetting;

/**
 * @author inet
 */
public class FXMLDocumentController implements Initializable {
    @FXML
    private Label lbConnect;
    @FXML
    private Label lbMessage;
    private Socket socket;
    private DataOutputStream output = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (new File("log.txt").exists()) {
                PrintStream printStream = new PrintStream(new FileOutputStream("log.txt"));
                System.setOut(printStream);
                System.setErr(printStream);
            }
        } catch (Exception e) {
        }
        try {
            // Create a socket to connect to the server
            socket = new Socket(ToolSetting.getInstance().getHost(),ToolSetting.getInstance().getPort());

            //Connection successful
            lbConnect.setText("Connected. \n");
            // Create an output stream to send data to the server
            output = new DataOutputStream(socket.getOutputStream());
            //create a thread in order to read message from server continuously
            TaskReadThread task = new TaskReadThread(socket,lbMessage,ToolSetting.getInstance().getProfile());
            Thread thread = new Thread(task);
            thread.start();
        } catch (IOException ex) {
            lbConnect.setText("Cant connect server");
        }
    }
}
