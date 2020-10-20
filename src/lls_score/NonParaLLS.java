/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lls_score;

import biogrid.*;
import gold_standard.GoldStandard;
import java.util.Set;
/*----------------------------------------------------------------*\
|                         Class NonParaLLS.java                    |
|        	    Calculate the LSS for a dataset                |
|                                                                  |
|                        Author: Olly Shaw                         |
|                        Author: Jen Hallinan                      |
|                      Author: Katherine James                     |
|                       Commenced: 07/08/07                        |
|                      Last edited: 15/07/20                       |
\*----------------------------------------------------------------*/
public class NonParaLLS {

    //**********Method to score the dataset****************
    //this is olly lls code adapted for the pipeline
    public double logScore(GoldStandard gs, Set<Pair> data) {


        //get the goldstandards
        Set<Pair> gsPairs = gs.getPositive();

        int truePosData = pairsInGoldStandard(gsPairs, data);//true positives
        int totalGenesGs = gs.getGenes().size();//size of kegg
        int truePosGs = gsPairs.size();//number pos pairs
        int possiblePairsGs = (totalGenesGs * (totalGenesGs - 1)) / 2;
        int trueNegGs = gs.numberOfNegatives();//number negative pairs
        int falsePosData = pairsNotInGoldStandard(gs, data);//false positives

        //calculate lls
        double PLE = 1.0 * truePosData / data.size();
        double notPLE = 1.0 * falsePosData / data.size();
        double PL = 1.0 * truePosGs / possiblePairsGs;
        double notPL = 1.0 * trueNegGs / possiblePairsGs;

        double PLE_over_notPLE = PLE / notPLE;

        double PL_over_notPL = PL / notPL;//priors

        double LS = PLE_over_notPLE / PL_over_notPL;
        double LLS = Math.log(LS);

        return LLS;//the score for the dataset
    }
    //END OF LOGSCORE METHOD

    //**********Method to count the number of pairs present in the GS********
    private static int pairsInGoldStandard(Set<Pair> goldList, Set<Pair> data) {

        int counter = 0;
        for (Pair pair : data)//the test list
        {
            if (goldList.contains(pair))//compare to the gold standard
            {
                counter++;
            }
        }
        return counter;
    }

    private static int pairsNotInGoldStandard(GoldStandard gs, Set<Pair> data)
    {
        int counter = 0;
        for(Pair pair : data)
        {
            if(!gs.getPositive().contains(pair)
                && gs.getGenes().contains(pair.getOrf1())
                && gs.getGenes().contains(pair.getOrf2()))
            {
                counter++;
            }
        }
        return counter;
    }
}
