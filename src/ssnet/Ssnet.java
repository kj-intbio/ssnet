package ssnet;

import java.util.HashMap;
import java.util.Map;
import biogrid.*;
import gold_standard.*;
import java.io.IOException;
import lls_score.*;

/**
 * Main network build for ssNET
 *
 * @author Katherine James Last edited 21/10/20
 */
public class Ssnet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        if (args.length < 4) {
            System.out.println("Usage: ssnet <infile> <D value> <taxon ID> <HTP threshold>");
            System.exit(1);
        }

        String bioGridFile = args[0];
        double dvalue = Double.parseDouble(args[1]);
        String species = args[2];//9606 = human, 559292 (V72)/4932 = yeast, 10090 = mouse
        int htp_threshold = Integer.parseInt(args[3]);

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
        iLogger.logToFile("V" + bio.getVersion() + "D" + dvalue + "_taxon" + species + "probabilisitc_ssnet.txt", lls_scores);
        System.out.println();
    }

}
