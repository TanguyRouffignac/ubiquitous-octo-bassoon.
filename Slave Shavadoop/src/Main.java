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

        @SuppressWarnings("CallToPrintStackTrace")
	public static void main(String[] args) {		
		try {
			File file = new File("/cal/homes/trouffignac/SLR207/S/S" + args[0]);
			BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        File output = new File("/cal/homes/trouffignac/SLR207/UM/UM" + args[0]);
			PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
			String line = fbr.readLine();
                        String str[] = line.split(" ");
                        ArrayList<String> keys = new ArrayList<>();
                        for(String s : str){
                            fbw.println(s + " 1");
                            int found = 0;
                            for(String k : keys){
                                if (k.equals(s)){
                                    found = 1;
                                    break;
                                }
                            }
                            if (found == 0){
                                keys.add(s);
                                System.out.println(s);
                            }
                        }
			fbr.close();
                        fbw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                
	}
}