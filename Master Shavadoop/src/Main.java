import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Main {

        @SuppressWarnings("CallToPrintStackTrace")
	public static void main(String[] args) {		
		try {
			File file = new File(args[0]);
			File output = new File("Responding");
			BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
			String line;
			String s;
			/*while ((name = fbr.readLine()) != null){
				String[] cmdline = {"sh", "-c", "ssh trouffignac@" + name + " echo \"OK\""};
				Process p = new ProcessBuilder(cmdline).start();				
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				while ((s = br.readLine()) != null)
					if (s.equals("OK"))						
						fbw.println(name);
			}*/
                        while ((line = fbr.readLine()) != null){
                            System.out.println(line);
                        }
			fbr.close();
			fbw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                
	}
}