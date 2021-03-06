
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            if (args[0].equals("Sx")) {
                File file = new File("/cal/homes/trouffignac/SLR207/S/S" + args[1]);
                BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                File output = new File("/cal/homes/trouffignac/SLR207/UM/UM" + args[1]);
                PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
                String line = fbr.readLine();
                String str[] = line.split(" ");
                ArrayList<String> keys = new ArrayList<>();
                String[] forbidden = {"je", "tu", "il", "elle", "on", "nous", "vous", "ils", "elles", "le", "la", "l", "lui", "les", "nous",
                                    "vous", "leur", "eux", "à", "dans", "par", "pour", "en", "vers", "avec", "de", "sans", "sous", "ou",
                                    "des", "aux", "pas", "est", "que", "ii", "dont", "et", "e", "mayotted", "ne", "qui", "tous", "tout",
                                    "ses", "du", "d", "sa", "qu", "au", "lorsqu", "a", "n", "un", "toute", "contre", "ni", "aucun", "se",
                                    "ce", "ont", "s", "si", "sont", "iii", "son", "une", "leurs", "aucune", "celui", "cette", "ci", "hors",
                                    "rien", "serait", "où", "sur", "soient", "sauf", "ayant", "être", "même", "tel"};
                for (String s : str) {
                        if(!(Arrays.asList(forbidden).contains(s))){
                        fbw.println(s + " 1");
                        int found = 0;
                        for (String k : keys) {
                            if (k.equals(s)) {
                                found = 1;
                                break;
                            }
                        }                    
                        if (found == 0) {
                            keys.add(s);
                            System.out.println(s);
                        }
                    }
                }
                fbr.close();
                fbw.close();
            }
            if (args[0].equals("UMx")) {
                int count = 0;
                String key = args[1];
                File output = new File("/cal/homes/trouffignac/SLR207/SM/SM" + args[2]);
                PrintWriter fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
                for (int i = 0; i < Integer.parseInt(args[3]); i++) {
                    File file = new File("/cal/homes/trouffignac/SLR207/UM/UM" + args[4 + i]);
                    BufferedReader fbr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                    String s;
                    while ((s = fbr.readLine()) != null) {
                        String[] line = s.split(" ");
                        if (line[0].equals(key)) {
                            count ++;
                            fbw.println(s);
                        }
                    }
                    fbr.close();
                }
                fbw.close();
                output = new File("/cal/homes/trouffignac/SLR207/RM/RM" + args[2]);
                fbw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output))));
                fbw.println(key + " " + count);
                System.out.println(key + " " + count);
                fbw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
