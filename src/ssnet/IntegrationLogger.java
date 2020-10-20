/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssnet;

import biogrid.Pair;

import java.io.*;
import java.util.Map;
/**
 *
 * @author CWMS3
 */
public class IntegrationLogger {
    public void logToFile(String fileName, Map<Pair, Double> intScores) throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(fileName))));

        for(Map.Entry<Pair, Double> row : intScores.entrySet())
        {
            pw.println(row.getKey().getOrf1() + "\t" + row.getKey().getOrf2() + "\t" + row.getValue());
        }

        pw.flush();
        pw.close();
    }
}
