/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biogrid;

/**
 *
 * @author CWMS3
 */
public class Pair {
    
    private String orf1;
    private String orf2;
    private double score;
    private String dataset;

    //**********Constructor for a Pair of orfs******************
    public Pair(String orf1, String orf2) {
        this.orf1 = orf1;
        this.orf2 = orf2;
    }
    //END OF ORF PAIR CONSRUDTOR

    //**********Constructor for a dataset to score pair*********
    public Pair(String dataset, double score) {
        this.dataset = dataset;
        this.score = score;
    }
    //END OF DATASET SCORE CONSTRUCTOR



    public String getDataset() {
        return dataset;
    }

    public double getScore() {
        return score;
    }

    //overrides toString
    @Override
    public String toString() {
        return orf1 + ":" + orf2;
    }

    public String getOrf1() {
        return orf1;
    }

    public void setOrf1(String orf1) {
        this.orf1 = orf1;
    }

    public String getOrf2() {
        return orf2;
    }

    public void setOrf2(String orf2) {
        this.orf2 = orf2;
    }

    //overrides hashCode
    @Override
    public int hashCode() {
        return orf1.hashCode() + orf2.hashCode();
    }

    //overrides equals
    @Override
    public boolean equals(Object anotherPair) {
        if (anotherPair == null) {
            return false;
        }
        if (!(anotherPair instanceof Pair)) {
            return false;
        }
        Pair anotherPairCast = (Pair) anotherPair;

        //check if the pair is equal in either direction
        return orf1.equals(anotherPairCast.getOrf1()) && orf2.equals(anotherPairCast.getOrf2()) || orf1.equals(anotherPairCast.getOrf2()) && orf2.equals(anotherPairCast.getOrf1());

    }
    
    
}
