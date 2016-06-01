import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {		
		try {
			File file = new File("Machines");
			File output = new File("Responding");
			BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
			String name;
			String s;
			ArrayList<Thread> threads = new ArrayList<Thread>();
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
			fbr = new BufferedReader(new InputStreamReader(new FileInputStream(output)));
			while ((name = fbr.readLine()) != null){
				 MyThread thread = new MyThread(name);
				 threads.add(thread);
				 thread.start();
			}
			fbr.close();
			for (Thread t : threads){
				t.join();
			}
			System.out.println("Tout est fini");
		} catch (Exception e) {}
	}
}