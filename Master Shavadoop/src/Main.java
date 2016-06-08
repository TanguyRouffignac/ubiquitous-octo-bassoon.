import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {

        @SuppressWarnings("CallToPrintStackTrace")
	public static void main(String[] args) {		
		try {
                        File file = new File("machines");
                        File output = new File("actives");
			BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
                        String name, s;
			while ((name = fbr.readLine()) != null){
				String[] cmdline = {"sh", "-c", "ssh trouffignac@" + name + " echo \"OK\""};
				Process p = new ProcessBuilder(cmdline).start();				
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((s = br.readLine()) != null)
					if (s.equals("OK"))						
						fbw.println(name);
			}
                        fbr.close();
                        fbw.close();
                        file = new File(args[0]);
			fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line;
                        int number = 0;
                        while ((line = fbr.readLine()) != null){
                            output = new File("S/S" + number);
                            fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
                            fbw.println(line);
                            fbw.close();
                            number ++;
                        }
			fbr.close();
                        ArrayList<Thread> threads = new ArrayList<>();
                        ArrayList<ArrayList<String>> keys = new ArrayList<>();
                        for(int i = 0 ; i < number ; i ++)
                            keys.add(new ArrayList<String>());
			fbr.close();
			fbw.close();
			BufferedReader fbr2 = new BufferedReader(new InputStreamReader(new FileInputStream(new File("actives"))));
			for(int i = 0 ; i < number ; i ++){
                            name = fbr2.readLine();
                            if(name == null){
                                fbr2 = new BufferedReader(new InputStreamReader(new FileInputStream(output)));
                                name = fbr2.readLine();
                            }
                            MyThread thread = new MyThread(name, i, keys.get(i));
                            threads.add(thread);
                            thread.start();
			}
			fbr.close();
			for (Thread t : threads){
				t.join();
			}
                        HashMap<String, ArrayList<Integer>> dico = new HashMap<>();
                        for(int i = 0 ; i < number ; i ++){
                            for(String key : keys.get(i)){
                                if(dico.get(key) == null){
                                    ArrayList<Integer> val = new ArrayList<>();
                                    val.add(i);
                                    dico.put(key, val);
                                }
                                else {
                                    ArrayList<Integer> val = dico.get(key);
                                    val.add(i);
                                    dico.put(key, val);
                                }
                            }
                        }
                        Iterator it = dico.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            System.out.println(pair.getKey() + " = " + pair.getValue());
                            it.remove(); // avoids a ConcurrentModificationException
                        }
                           
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                
	}
}