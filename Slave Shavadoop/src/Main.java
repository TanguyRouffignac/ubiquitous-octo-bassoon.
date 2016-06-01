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
			File file = new File("../S/S" + args[0]);
			BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        File output = new File("../Um/Um" + args[0]);
			PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
			String line = fbr.readLine();
                        String str[] = line.split(" ");
                        for(String s : str){
                            fbw.println(s + " 1");
                        }
			fbr.close();
                        fbw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                
	}
}