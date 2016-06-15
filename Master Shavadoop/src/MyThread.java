
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tanguy
 */
public class MyThread extends Thread{
    final private String name;
    final private int number;
    ArrayList<String> keys;
    
    public MyThread(String name, int number, ArrayList<String> keys){
        this.name = name;
        this.number = number;
        this.keys = keys;
    }
    
    @Override
    public void run(){
        try {
            String s;
            String[] cmdline = {"sh", "-c", "ssh trouffignac@" + name + " java -jar SLR207/Slave_Shavadoop.jar Sx " + number};
            ProcessBuilder pb = new ProcessBuilder(cmdline);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null){
                keys.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
