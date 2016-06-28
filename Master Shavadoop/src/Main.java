
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            File file = new File("machines");
            File output = new File("actives");
            BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
            String name, s;
            while ((name = fbr.readLine()) != null) {
                String[] cmdline = {"sh", "-c", "ssh trouffignac@" + name + " echo \"OK\""};
                Process p = new ProcessBuilder(cmdline).start();
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null) {
                    if (s.equals("OK")) {
                        fbw.println(name);
                    }
                }
            }
            fbr.close();
            fbw.close();
            file = new File(args[0]);
            fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            int number = 0;
            while ((line = fbr.readLine()) != null) {
                output = new File("S/S" + number);
                fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
                line = line.replaceAll("[^a-zA-Zéèêùàçïîûâô]", " ");
                line = line.toLowerCase();
                System.out.println(line);
                fbw.println(line);
                fbw.close();
                number++;
            }
            fbr.close();
            ArrayList<Thread> threads = new ArrayList<>();
            HashMap<Integer, String> machines = new HashMap<>();
            ArrayList<ArrayList<String>> keys = new ArrayList<>();
            for (int i = 0; i < number; i++) {
                keys.add(new ArrayList<String>());
            }
            fbr.close();
            fbw.close();
            long endTime = System.currentTimeMillis();
            System.out.println("Temps pour chercher les machines allumées : " + (endTime - startTime));
            startTime = System.currentTimeMillis();
            BufferedReader fbr2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("actives"))));
            for (int i = 0; i < number; i++) {
                name = fbr2.readLine();
                if (name == null) {
                    fbr2.close();
                    fbr2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("actives"))));
                    name = fbr2.readLine();
                }
                MyThread thread = new MyThread(name, i, keys.get(i));
                machines.put(i, name);
                threads.add(thread);
                thread.start();
            }
            fbr.close();
            for (Thread t : threads) {
                t.join();
            }
            HashMap<String, ArrayList<Integer>> dico = new HashMap<>();
            for (int i = 0; i < number; i++) {
                for (String key : keys.get(i)) {
                    if (dico.get(key) == null) {
                        ArrayList<Integer> val = new ArrayList<>();
                        val.add(i);
                        dico.put(key, val);
                    } else {
                        ArrayList<Integer> val = dico.get(key);
                        val.add(i);
                        dico.put(key, val);
                    }
                }
            }
            fbr.close();
            endTime = System.currentTimeMillis();
            System.out.println("Temps pour map : " + (endTime - startTime));
            startTime = System.currentTimeMillis();
            ArrayList<MyOtherThread> otherThreads = new ArrayList<>();
            Iterator it = dico.entrySet().iterator();
            int i = 0;
            HashMap<Integer, String> reduced = new HashMap<>();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                name = fbr2.readLine();
                if (name == null) {
                    fbr2.close();
                    fbr2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("actives"))));
                    name = fbr2.readLine();
                }
                MyOtherThread t = new MyOtherThread(name, (String) pair.getKey(), i, dico);
                otherThreads.add(t);
                reduced.put(i, name);
                t.start();
                i++;
            }
            HashMap<String, Integer> unsorted = new HashMap<>();
            for (MyOtherThread t : otherThreads) {
                t.join();
                if (t.getAnswer() != null) {
                    String[] answer = t.getAnswer().split(" ");
                    unsorted.put(answer[0], new Integer(answer[1]));
                }
            }
            endTime = System.currentTimeMillis();
            System.out.println("Temps pour reduce : " + (endTime - startTime));
            startTime = System.currentTimeMillis();
            List<Map.Entry<String, Integer>> entries = new ArrayList<>(unsorted.entrySet());
            Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(
                    Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                        if (entry1.getValue() > entry2.getValue())
                            return -1;
                        if (Objects.equals(entry1.getValue(), entry2.getValue()))
                            return 0;
                        return 1;
                }
            });
            //System.out.println(entries);
            output = new File("output");
            fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
            for(Map.Entry<String, Integer> entry : entries){
                fbw.println(entry.getKey() + " " + entry.getValue());
            }
            fbw.close();
            endTime = System.currentTimeMillis();
            System.out.println("Temps pour trier : " + (endTime - startTime));
            System.out.println("FIN");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
