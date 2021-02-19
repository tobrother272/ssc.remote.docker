/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;
import static ssc.reup.api.Controller.SikuliXScript.Sleep;
import ssc.reup.api.ToolSetting;

/**
 *
 * @author topman garbuja,
 *
 * It is used to get input from server simultaneously
 */
public class TaskReadThread implements Runnable {

    //private variables
    Socket socket;
    Label lbMessage;

    public void showMessage(String message) {
        Platform.runLater(() -> {
            //display the message in the textarea
            lbMessage.setText(message);
        });
    }
    DataInputStream input;
    DataOutputStream output;
    private String profile;
    Screen s=new Screen();
    //constructor
    public TaskReadThread(Socket socket, Label lbMessage, String profile) {
        this.socket = socket;
        this.lbMessage = lbMessage;
        this.profile = profile;
    }

    @Override
    public void run() {
        try {
            output = new DataOutputStream(socket.getOutputStream());
            sendMessageToServer("#connected#"+ToolSetting.getInstance().getName());
        } catch (Exception e) {
        }
        while (true) {
            try {
                input = new DataInputStream(socket.getInputStream());
                String message = input.readUTF();
                extractAction(message.replaceAll("#" + ToolSetting.getInstance().getName() + "#", ""));
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }
    private String currentMessageId = "";

    public void extractAction(String query) {
        String pureMessage = query.split("id=")[0];
        currentMessageId = "#" + query.split("id=")[1] + "#";
        String message = pureMessage.split("\\|")[0];
        double time = Double.parseDouble(pureMessage.split("\\|")[1]);
        try {
            if (message.startsWith("#LOAD#")) {
                sendMessageToServer("Nhận lệnh load " + message.replaceAll("#LOAD#", ""));
                showMessage("load " + message.replaceAll("#LOAD#", ""));
                ProfileManager.openFireFoxNewTab(profile + "\\firefox", message.replaceAll("#LOAD#", ""), "");
            } else if (message.startsWith("#CLICK#")) {
                sendMessageToServer("Nhận lệnh click " + message.replaceAll("#CLICK#", ""));
            } else if (message.startsWith("#WAIT#")) {
                sendMessageToServer("Nhận lệnh chờ " + message.replaceAll("#WAIT#", ""));
                String hinh = message.replaceAll("#WAIT#", "");
                showMessage("Chờ " + hinh);
                if (SikuliXScript.loadAndWaitImageAppear(s, time, 2, this, hinh, message)) {
                    sendMessageToServer(currentMessageId + "continue true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + "continue fail " + hinh);
                }
            } else if (message.startsWith("#WAITANDCLICK#")) {
                String hinh = message.replaceAll("#WAITANDCLICK#", "");
                showMessage("Chờ & click " + hinh);
                sendMessageToServer("Nhận lệnh #WAITANDCLICK# " + hinh);
                if (SikuliXScript.loadAndWaitImageAppearAndClick(s, time, 2, this, hinh, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            } else if (message.startsWith("#WAITANDCLICKEX#")) {
                String hinh = message.replaceAll("#WAITANDCLICKEX#", "");
                showMessage("Chờ & click " + hinh);
                sendMessageToServer("Nhận lệnh #WAITANDCLICKEX# " + hinh);
                if (SikuliXScript.loadAndWaitImageAppearAndClickEX(s, time, 2, this, hinh, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            } else if (message.startsWith("#TYPE#")) {
                String text = message.replaceAll("#TYPE#", "").split("#FILE#")[1];
                sendMessageToServer("Nhận lệnh #TYPE# " + text);
                String hinh = message.replaceAll("#TYPE#", "").split("#FILE#")[0];
                showMessage("Chờ nhập " + hinh);
                if (SikuliXScript.typeByFile(s, time, 2, this, hinh, text, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            } else if (message.startsWith("#TYPEKEY#")) {
                try {
                    String text = message.replaceAll("#TYPEKEY#", "");
                    sendMessageToServer("Nhận lệnh #TYPEKEY# " + text);
                    showMessage("Nhập " + text);
                    if (text.equals("down")) {
                        s.type(Key.DOWN);
                    } else if (text.equals("enter")) {
                        s.type(Key.ENTER);
                    } else if (text.equals("page_down")) {
                        s.type(Key.PAGE_DOWN);
                    }
                    int timeS = (int) time * 1000;
                    Sleep(timeS);
                    sendMessageToServer(currentMessageId + " true ");
                } catch (Exception e) {
                    
                }
            } else if (message.startsWith("#CLEAR#")) {
                String image = message.replaceAll("#CLEAR#", "");
                sendMessageToServer("Nhận lệnh #CLEAR# " + image);
                showMessage("Clear " + image);
                if (SikuliXScript.loadAndWaitClear(s, time, 2, this, image, message)) {
                    sendMessageToServer(currentMessageId + " true " + image);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + image);
                }
            } else if (message.startsWith("#WAITAND2CLICK#")) {
                String hinh = message.replaceAll("#WAITAND2CLICK#", "");
                sendMessageToServer("2Click " + hinh);
                showMessage("2Click " + hinh);
                if (SikuliXScript.loadAndWaitImageAppearAnd2Click(s, time, 2, this, hinh, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            }else if (message.startsWith("#WAITAND3CLICKTEXT#")) {
                String hinh = message.replaceAll("#WAITAND3CLICKTEXT#", "");
                sendMessageToServer("3Click text" + hinh);
                showMessage("3Click text" + hinh);
                if (SikuliXScript.loadAndWaitTextAppearAnd3Click(s, time,  this, hinh, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            }else if (message.startsWith("#WAITANDCLICKTEXT#")) {
                String hinh = message.replaceAll("#WAITANDCLICKTEXT#", "");
                sendMessageToServer("Click text" + hinh);
                showMessage("Click text" + hinh);
                if (SikuliXScript.loadAndWaitTextAppearAndClick(s, time,this, hinh, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            }else if (message.startsWith("#WAITTEXT#")) {
                String hinh = message.replaceAll("#WAITTEXT#", "");
                sendMessageToServer("Wait text" + hinh);
                showMessage("Wait text " + hinh);
                if (SikuliXScript.loadAndWaitTextAppear(s, time,this, hinh, message)) {
                    sendMessageToServer(currentMessageId + " true " + hinh);
                } else {
                    sendMessageToServer(currentMessageId + " fail " + hinh);
                }
            }else if (message.startsWith("#GETTEXT#")) {
                String hinh = message.replaceAll("#GETTEXT#", "");
                sendMessageToServer("Wait get text" + hinh);
                showMessage("Wait get text" + hinh);
                sendMessageToServer(currentMessageId + "text=" + SikuliXScript.loadAndGetText(time,this, hinh, message));
            }else if (message.startsWith("#SELECTALL#")) {
                sendMessageToServer("Nhận lệnh chọn tất cả");
                SikuliXScript.selectAll(this);
                sendMessageToServer("continue true ");
                //browser.goBack();
            } else if(message.startsWith("#CLEARSELECT#")){
                sendMessageToServer("Nhận lệnh bỏ chọn tất cả");
                SikuliXScript.clearSelect(this);
                sendMessageToServer("continue true ");        
            }  else if(message.startsWith("#SCROLL#")){
                sendMessageToServer("Nhận lệnh scroll");
                SikuliXScript.scroll(this,s);
                sendMessageToServer("continue true ");        
            } 
            else if (message.startsWith("#BACK#")) {
                sendMessageToServer("Nhận lệnh back");
                //browser.goBack();
            } else if (message.equals("exit")) {
                socket.close();
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadURl(String url) {
        //browser.loadURL(url);
    }

    public void sendMessageToServer(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (Exception e) {
        }
    }

}
