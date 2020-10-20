/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lls_score;

import biogrid.*;
import gold_standard.GoldStandard;
import ssnet.DataSetScorer;

import java.io.*;
import java.util.*;

/*--------------------------------------------------------------------*\
|                        Class LlsScore.java                              |
|               Class which takes the gold standard                    |
|      scores each datasets confidence using the Lee LLS method        |
|																	   |
|                    Author: Katherine James						   |
|                      Commenced: 28/01/08                             |
|                    Last Edited: 15/07/20                             |
\*--------------------------------------------------------------------*/
public class LlsScore implements DataSetScorer {

    private GoldStandard gs;

    public LlsScore() {
    }

    public LlsScore(GoldStandard gs) {
        this.gs = gs;
    }

    public GoldStandard getGs() {
        return gs;
    }

    public void setGs(GoldStandard gs) {
        this.gs = gs;
    }

    public Map<String, Double> scoredDataSet(BioGRID bio) {
        System.out.println("getting lls...");
        Map<String, Double> dataSetToScore = new HashMap<String, Double>();
        double highScore = 0.0;//to find the highest score

        for (String dataSet : bio.getHTPBioGRID().keySet()) {//iterate through the datasets

            System.out.println("calculating for " + dataSet + "...");
            Double lls = new NonParaLLS().logScore(gs, bio.getHTPBioGRID().get(dataSet));//score

            if (lls > 0 && !lls.isNaN())
            {
                System.out.println(lls + " is the LLS for " + dataSet);
                //write to file
                dataSetToScore.put(dataSet, lls);

                if (lls > highScore && !lls.isInfinite()) {
                    highScore = lls;//to keep track of the highest score
                }
            } else {
                System.out.println(lls + " is the LLS for " + dataSet);
                System.out.println(dataSet + " will not be used for integration :(");
                //we don't use the negative scores
            }
        }

        double finalHighScore = Math.ceil(highScore + 1);//round up the highscore
        System.out.println("scoring done");
        System.out.println("highest score is " + highScore);
        System.out.println("high score to be used " + finalHighScore);

        //replace Infinity scores with finalHighScore
        for(String dataSet: new HashSet<String>(dataSetToScore.keySet()))
        {
            if (dataSetToScore.get(dataSet).isInfinite())
            {
                 dataSetToScore.put(dataSet, finalHighScore);
            }
        }

        String outFile = "V" + bio.getVersion() + "LLS.txt";
        System.out.println("writing to " + outFile);

        //write to file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(outFile));

            for (String s : dataSetToScore.keySet()) {
                out.write(s + "\t" + dataSetToScore.get(s) + "\n");
            }
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Somethings gone horribly wrong, check your file path");
        }
        return dataSetToScore;//the confidence scores
    //END OF GETSCORES
    }
    
    
    
    public Map<String, Double> scoredGoldStandard(List<BioGenePair> gs, int version, Map<String, Double> htp_lls) {
    
        Map<String, Double> gsToScore = new HashMap<String, Double>();
        double highScore = 0.0;
        
        Set<String> dataTypes = new HashSet<String>();
        
        for(BioGenePair p: gs){
            dataTypes.add(p.getType());
        }
        
        Map<String, List<BioGenePair>> genePairByType = new HashMap<String, List<BioGenePair>>();
        for (String type : dataTypes) {
            genePairByType.put(type, new ArrayList<BioGenePair>());
        }
        for (BioGenePair bgp : gs) {
            genePairByType.get(bgp.getType()).add(bgp);
        }

        Map<String, Set<Pair>> datasets = new HashMap<String, Set<Pair>>();
        for (String type : genePairByType.keySet()) {
            List<BioGenePair> pairList = genePairByType.get(type);
            if (!pairList.isEmpty()) {
                Set<Pair> pairs = new HashSet<Pair>();
                for (BioGenePair pgp : pairList) {
                    pairs.add(new Pair(pgp.getOrf1(), pgp.getOrf2()));
                }
                datasets.put(type, pairs);
            }
        }

        System.out.println("Number of gold standard datasets: " + datasets.size());
        
        //now we need to score them one by one
        
        for(String s: datasets.keySet()){
                    
            System.out.println("Scoring dataset: " + s);
            Set<Pair> gs_dataset = new HashSet<Pair>();
            
            gs_dataset = datasets.get(s);

            Set<Pair> gs_goldstandard  = new HashSet<Pair>();
            
            for(String t: datasets.keySet()){
                if(!s.equals(t)){
                   gs_goldstandard.addAll(datasets.get(t));
                }
                
            }
            
            Set<String> allGenes = new HashSet<String>();//all the possible genes
            Set<Pair> posPairs = new HashSet<Pair>();
        
        for (Pair bgp : gs_goldstandard) {
            allGenes.add(bgp.getOrf1());
            allGenes.add(bgp.getOrf2());
            
            posPairs.add(new Pair(bgp.getOrf1(), bgp.getOrf2()));
        }

        int numGenes = allGenes.size();
        System.out.println("number of gs genes " + numGenes);
        System.out.println("actual number of gs pairs is " + posPairs.size());

        GoldStandard temp_GS = new GoldStandard(posPairs, allGenes);
          
        Double lls = new NonParaLLS().logScore(temp_GS, gs_dataset);//score

            if (lls > 0 && !lls.isNaN())
            {
                System.out.println(lls + " is the LLS for " + s);
                //write to file
                gsToScore.put(s, lls);

                if (lls > highScore && !lls.isInfinite()) {
                    highScore = lls;//to keep track of the highest score
                }
            } else {
                System.out.println(lls + " is the LLS for " + s);
                System.out.println(s + " will not be used for integration :(");
                //we don't use the negative scores
            }
        }

        double finalHighScore = Math.ceil(highScore + 1);//round up the highscore
        System.out.println("scoring done");
        System.out.println("highest score is " + highScore);
        System.out.println("high score to be used " + finalHighScore);

        //replace Infinity scores with finalHighScore
        for(String dataSet: new HashSet<String>(gsToScore.keySet()))
        {
            if (gsToScore.get(dataSet).isInfinite())
            {
                 gsToScore.put(dataSet, finalHighScore);
            }
        }

        String outFile = "V" + version + "GSLLS.txt";
        System.out.println("writing to " + outFile);

        //write to file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(outFile));

            for (String s : gsToScore.keySet()) {
                out.write(s + "\t" + gsToScore.get(s) + "\n");
            }
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Somethings gone horribly wrong, check your file path");
        }
          
        gsToScore.putAll(htp_lls);
       
        return gsToScore;
    }
}


