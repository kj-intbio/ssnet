/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssnet;

import java.util.HashMap;
import java.util.Map;
import biogrid.*;
import gold_standard.*;
import java.io.IOException;
import lls_score.*;

/**
 *
 * @author CWMS3
 */
public class Ssnet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        String bioGridFile = "inputs/BIOGRID-ORGANISM-Saccharomyces_cerevisiae_S288c-3.5.186.tab2.txt";
        double dvalue = 1.0;
        String species = "559292";//9606 = human, 559292 (V72)/4932 = yeast, 10090 = mouse
        int htp_threshold = 100;
        
        System.out.println("Extracting datasets from BioGRID...");
        BioGRIDParser bioGRIDParser = new BioGRIDParser();
        BioGRIDData bgd = bioGRIDParser.parseBioGRID(bioGridFile, species, htp_threshold);
        BioGRID bio = bioGRIDParser.buildBioGRID(bgd, htp_threshold);
        bioGRIDParser.writeLogFile(bio);
        System.out.println();
        
        IntegrationLogger iLogger = new IntegrationLogger();
      
        System.out.println("Creating LTP gold standard...");
        GSParser gs_parser = new GSParser();
        GoldStandard gs = gs_parser.parseSmalls(bio.getGoldstandard());
        System.out.println();
              
        System.out.println("Confidence scoring HTP datasets...");
        LlsScore llsScore = new LlsScore();
        llsScore.setGs(gs);
        Map<String, Double> lls_htp_gs = llsScore.scoredDataSet(bio);
        System.out.println();
        
        System.out.println("Confidence scoring LTP datasets...");
        Map<String, Double> gs_lls = new HashMap<String, Double>();
        gs_lls = llsScore.scoredGoldStandard(bio.getGoldstandard(), bio.getVersion(), lls_htp_gs);
        System.out.println();        
        System.out.println("performing weighted integration...");
        Integration li = new Integration();
        li.setBio(bio);
        li.setDValue(dvalue);
        Map<Pair, Double> lls_scores = li.integrate(gs_lls);
        iLogger.logToFile("V" + bio.getVersion() + "D"+ dvalue + "_taxon" + species + "probabilisitcFullNet.txt", lls_scores);
        System.out.println();
    }
    
}
