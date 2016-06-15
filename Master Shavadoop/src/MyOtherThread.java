
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tanguy
 */
public class MyOtherThread extends Thread{
    final private String name;
    final private String key;
    final private int number;
    final HashMap<String, ArrayList<Integer>> map;
    
    public MyOtherThread(String name, String key, int number, HashMap<String, ArrayList<Integer>> map){
        this.name = name;
        this.key = key;
        this.number = number;
        this.map = map;
    }
    
    @Override
    public void run(){
        try {
            String s;
            String command = "ssh trouffignac@" + name + " java -jar SLR207/Slave_Shavadoop.jar UMx " + key + " " + number + " " + map.get(key).size();
            for(int i = 0 ; i < map.get(key).size() ; i ++)
                command = command + " " + map.get(key).get(i);
            String[] cmdline = {"sh", "-c", command};
            ProcessBuilder pb = new ProcessBuilder(cmdline);
            Process p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null){
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
