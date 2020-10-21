package gold_standard;

import java.io.*;
import java.util.*;
import biogrid.*;

/**
 * Class to extract create the gold standard for ssNET integration
 *
 * Based on KeggParser (Katherine James & Matthew Pocock) and BioSystemsParser
 * (Katherine James)
 *
 * @author CWMS3 Edited 21/10/20
 */
public class GSParser {

    public GoldStandard parseSmalls(List<BioGenePair> smalls) throws IOException {

        Set<String> allGenes = new HashSet<String>();//all the possible genes
        Set<Pair> posPairs = new HashSet<Pair>();

        for (BioGenePair bgp : smalls) {
            allGenes.add(bgp.getOrf1());
            allGenes.add(bgp.getOrf2());
            posPairs.add(new Pair(bgp.getOrf1(), bgp.getOrf2()));
        }

        int numGenes = allGenes.size();
        int possPairs = ((numGenes * numGenes) - numGenes) / 2;
        System.out.println("number of genes " + numGenes);
        System.out.println("possible pairs " + possPairs);
        System.out.println("getting the gold standard pairs...");
        System.out.println("actual number of pairs is " + posPairs.size());

        return new GoldStandard(posPairs, allGenes);

    }

}
