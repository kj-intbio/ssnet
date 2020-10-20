/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biogrid;

import java.util.*;

/**
 *
 * @author CWMS3
 */
public class BioGRID {
    
    private Map<String, Set<Pair>> bio_htp;
    private Map<String, Set<Pair>> bio;
    private int version;
    private Set<String> allGenes;
    private Set<Pair> allPairs;
    private List<BioGenePair> goldstandard;

    public BioGRID(Map<String, Set<Pair>> bio_htp, Map<String, Set<Pair>> bio, int version, Set<String> genes, Set<Pair> pairs, List<BioGenePair> goldstandard) {
        this.bio_htp = bio_htp;
        this.bio = bio;
        this.version = version;
        this.allGenes = genes;
        this.allPairs = pairs;
        this.goldstandard = goldstandard;
    }
    

    public List<BioGenePair> getGoldstandard() {
        return goldstandard;
    }

    public void setGoldstandard(List<BioGenePair> goldstandard) {
        this.goldstandard = goldstandard;
    }
    
    public Set<Pair> getPairs() {
        return allPairs;
    }

    public void setAllGenes(Set<String> allGenes) {
        this.allGenes = allGenes;
    }

    public void setAllPairs(Set<Pair> allPairs) {
        this.allPairs = allPairs;
    }

    public void setHTPBio(Map<String, Set<Pair>> bio_htp) {
        this.bio_htp = bio_htp;
    }
    
    public void setLTPBio(Map<String, Set<Pair>> bio) {
        this.bio = bio;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<String> getGenes() {
        return allGenes;
    }

    public void addHTPDataset(String name, Set<Pair> data) {
        bio_htp.put(name, data);
    }
    
    public void addLTPDataset(String name, Set<Pair> data) {
        bio_htp.put(name, data);
    }

    public Map<String, Set<Pair>> getHTPBioGRID() {
        return bio_htp;
    }
    
    public Map<String, Set<Pair>> getBioGRID() {
        return bio;
    }
    
    public int getVersion() {
        return version;
    }

    public int numHTPDatasets() {
        return bio_htp.size();
    }
    
    public int numDatasets() {
        return bio.size();
    }
    
}
