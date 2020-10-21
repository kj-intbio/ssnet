package ssnet;

import biogrid.Pair;

import java.io.*;
import java.util.Map;

/**
 *
 * @author Matthew Pocock
 */
public class IntegrationLogger {

    public void logToFile(String fileName, Map<Pair, Double> intScores) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fileName))));

        for (Map.Entry<Pair, Double> row : intScores.entrySet()) {
            pw.println(row.getKey().getOrf1() + "\t" + row.getKey().getOrf2() + "\t" + row.getValue());
        }

        pw.flush();
        pw.close();
    }
}
