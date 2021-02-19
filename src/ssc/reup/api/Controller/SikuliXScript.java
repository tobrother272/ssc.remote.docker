/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.reup.api.Controller;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import static java.awt.event.InputEvent.BUTTON2_MASK;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_V;
import java.io.File;
import java.util.Random;
import org.sikuli.script.Key;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

/**
 *
 * @author simpl
 */
public class SikuliXScript {

    private Screen screen;

    public static void Sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            //ex.printStackTrace();
        }
    }

    public SikuliXScript(Screen screen) {
        this.screen = screen;
    }

    public boolean clickImage(String imagePath, int time) {
        try {
            if (screen.exists(imagePath, time) == null) {
                return false;
            }
            screen.find(imagePath);
            screen.click();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean waitImage(String imagePath, int time) {
        try {
            if (screen.wait(imagePath, time) == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean loadAndWaitImageAppear(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                task.showMessage("Chờ " + message + currentTime + "/" + timeWait);
                if (s.exists(image, delayTime) != null) {
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean loadAndWaitImageAppearAndClick(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                task.showMessage("Chờ " + message + currentTime + "/" + timeWait);
                if (s.exists(image, delayTime) != null) {

                    int x = 0;
                    int y = 0;
                    int w = s.find(image).getW();
                    int h = s.find(image).getH();
                    if (new Random().nextInt(2) == 0) {
                        x = s.find(image).getCenter().getX() + new Random().nextInt(w / 2);
                    } else {
                        x = s.find(image).getCenter().getX() - new Random().nextInt(w / 2);
                    }
                    if (new Random().nextInt(2) == 0) {
                        y = s.find(image).getCenter().getY() + new Random().nextInt(h / 2);
                    } else {
                        y = s.find(image).getCenter().getY() - new Random().nextInt(h / 2);
                    }

                    s.click(new Location(x, y));
                    //s.find(image);
                    //s.click();
                    task.showMessage("Chờ " + 2 + "s");
                    Sleep(500 + (100 * new Random().nextInt(10)));
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean loadAndWaitImageAppearAndClickEX(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                task.showMessage("Chờ " + message + currentTime + "/" + timeWait);
                if (s.exists(image, delayTime) != null) {
                    s.find(image).click();
                    task.showMessage("Chờ " + 2 + "s");
                    Sleep(500 + (100 * new Random().nextInt(10)));
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean loadAndWaitImageAppearAnd2Click(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                task.showMessage("Chờ " + message + currentTime + "/" + timeWait);
                if (s.exists(image, delayTime) != null) {
                    s.doubleClick(image);
                    //s.find(image);
                    //s.click();
                    task.showMessage("Chờ " + 2 + "s");
                    Sleep(500 + (100 * new Random().nextInt(10)));
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean loadAndWaitTextAppearAndClick(Screen s, double timeWait, TaskReadThread task, String text, String message) {
        try {
           task.showMessage("Chờ " + message + " /" + timeWait);
           s.findText(text, timeWait);
           s.click();
           return true;
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean loadAndWaitTextAppear(Screen s, double timeWait, TaskReadThread task, String text, String message) {
        try {
           task.showMessage("Chờ " + message + " /" + timeWait);
           s.findText(text, timeWait);
           return true;
        } catch (Exception e) {
        }
        return false;
    }
    public static String loadAndGetText(double timeWait, TaskReadThread task, String text, String message) {
        try {
           Screen s=new Screen();
           task.showMessage("Chờ " + message + " /" + timeWait);
           Match m =s.findText(text, timeWait);
           s.setROI(m.x, m.y, 500, m.h);
           String textV=s.getROI().text();
           return textV;
        } catch (Exception e) {
        }
        return "";
    }
    
    
    
    public static boolean loadAndWaitTextAppearAnd3Click(Screen s, double timeWait, TaskReadThread task, String text, String message) {
        try {
           task.showMessage("Chờ " + message + " /" + timeWait);
           s.findText(text, timeWait);
           s.doubleClick();
           s.click();
           return true;
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean loadAndWaitClear(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String message) {
        try {
            if(loadAndWaitImageAppearAnd2Click(s, timeWait, delayTime, task, image, message)){
                Sleep(1000);
                s.type(Key.BACKSPACE);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean type(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String text, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                if (task != null) {
                    task.showMessage("Chờ " + message + " " + text + " - " + currentTime + "/" + timeWait);
                }
                if (s.exists(image, delayTime) != null) {
                    //s.type(image, text);
                    s.find(image).click();
                    Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable t = clipBoard.getContents(null);
                    StringSelection data = new StringSelection(text);
                    clipBoard.setContents(data, data);
                    Robot bot = new Robot();
                    bot.keyPress(KeyEvent.VK_CONTROL);
                    bot.keyPress(VK_V);
                    bot.keyRelease(VK_V);
                    bot.keyRelease(KeyEvent.VK_CONTROL);
                    Sleep(500 + (100 * new Random().nextInt(10)));
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean typeByFile(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String file, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                if (task != null) {
                    task.showMessage("Chờ " + message + " " + file + " - " + currentTime + "/" + timeWait);
                }
                if (s.exists(image, delayTime) != null) {
                    //s.type(image, text);
                    s.find(image).click();
                    Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    Transferable t = clipBoard.getContents(null);
                    StringSelection data = new StringSelection(MyFileUtils.getStringFromFile(new File(file)));
                    clipBoard.setContents(data, data);
                    Robot bot = new Robot();
                    bot.keyPress(KeyEvent.VK_CONTROL);
                    bot.keyPress(VK_V);
                    bot.keyRelease(VK_V);
                    bot.keyRelease(KeyEvent.VK_CONTROL);
                    Sleep(500 + (100 * new Random().nextInt(10)));
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean selectAll(TaskReadThread task) {
        try {
            task.showMessage("Chọn tất cả ");
            Robot bot = new Robot();
            bot.keyPress(KeyEvent.VK_CONTROL);
            bot.keyPress(VK_A);
            bot.keyRelease(VK_A);
            bot.keyRelease(KeyEvent.VK_CONTROL);
            Sleep(500 + (100 * new Random().nextInt(10)));
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean clearSelect(TaskReadThread task) {
        try {
            task.showMessage("Bỏ chọn all ");
            Robot bot = new Robot();
            bot.mousePress(BUTTON2_MASK);
            bot.mouseRelease(BUTTON2_MASK);
            Sleep(500 + (100 * new Random().nextInt(10)));
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean scroll(TaskReadThread task,Screen s) {
        try {
            task.showMessage("Scroll ");
            s.wheel(1, 8);
            Sleep(500 + (100 * new Random().nextInt(10)));
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    public static boolean loadAndWaitImageDisappear(Screen s, double timeWait, int delayTime, TaskReadThread task, String image, String message) {
        try {
            long startLoadRegPage = System.currentTimeMillis();
            long currentTime = 0;
            do {
                currentTime = (System.currentTimeMillis() - startLoadRegPage) / 1000;
                task.showMessage("Chờ " + message + currentTime + "/" + timeWait);
                if (s.exists(image, delayTime) == null) {
                    return true;
                }
            } while (currentTime < timeWait);
            if (currentTime >= timeWait) {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }

}
