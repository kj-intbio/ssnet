/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biogrid;

import java.io.*;
import java.util.*;

/**
 *
 * @author CWMS3
 */
public class BioGRIDParser {
    
    public BioGRIDData parseBioGRID(String bgfilename, String species, int threshold) throws IOException {//yeast
        File bioFile = new File(bgfilename);
        System.out.println(bgfilename);
        BufferedReader in = new BufferedReader(new FileReader(bioFile));
        String line;
        System.out.println("biogrid file loaded...  parsing...");

        int version = 0;
        String[] findVersion = bgfilename.split("\\.");
        version = Integer.parseInt(findVersion[2]);//the version number
        System.out.println("Version Number = " + version);

                while ((line = in.readLine()) != null)//find the start of the data
        {
            String[] findStart = line.split("\t");

            if (findStart[0].equals("#BioGRID Interaction ID")) {//the line before the data starts - INTERACTOR_A/#BioGRID Interaction ID
                System.out.println(findStart[0]);
                break;
            }
        }

        if (version == 0) {
            System.out.println("version not found");
            System.exit(1);
        }

        Set<String> ids = new HashSet<String>();  //stores the dataset ids
        Set<String> types = new HashSet<String>();  //stores the <100 interaction data types
        List<BioGenePair> lines = new ArrayList<BioGenePair>();  //stores the BioGRID data
        Set<String> pubmeds = new HashSet<String>();

        while ((line = in.readLine()) != null) {
            //System.out.println("line");
            String[] splitline = line.split("\t");
            String orf1 = splitline[7];//for yeast use 0 and 1, or 2/3/ 7/8 later from 66
            String orf2 = splitline[8];
            String authorLong = splitline[13];//7/13
            String[] splitAuthor = authorLong.split(" ");//remove the initials and et al.
            String author = splitAuthor[0];//the authors surname
            String exp = splitline[14]; //pubmed id 8/14
            pubmeds.add(exp);
            String type = splitline[11]; //experiment type 6/11
            String correctName = type.replaceAll("\\s", "_");
            String org1 = splitline[15];//9 and 10 /15and 16
            String org2 = splitline[16];
            if (org1.equals(species) && org2.equals(species)) {//check if the data is for the correct organism

                BioGenePair tmp = new BioGenePair(orf1, orf2, author, exp, correctName);

                ids.add(exp); // all pubmed ids

                types.add(correctName); // all evidence types

                if (!orf1.equals(orf2)) //check for self interactions
                {
                    lines.add(tmp); //each pair is added to the list
                }
            }

        }//now all the data is in lines


        BioGRIDData bgd = new BioGRIDData();
        bgd.setIds(ids);
        bgd.setLines(lines);
        bgd.setTypes(types);
        bgd.setVersion(version);

        return bgd;
    }

    public BioGRID buildBioGRID(BioGRIDData bgd, int threshold)
            throws IOException {
        Map<String, List<BioGenePair>> genePairByID = new HashMap<String, List<BioGenePair>>();
        for (String id : bgd.getIds()) {
            genePairByID.put(id, new ArrayList<BioGenePair>());
        }

        //get a list of all the pairs, group by experiments
        Set<Pair> allPairs = new HashSet<Pair>();//all the pairs
        for (BioGenePair pair : bgd.getLines()) {
            Pair temp = new Pair(pair.getOrf1(), pair.getOrf2());
            allPairs.add(temp);//all the pairs
            genePairByID.get(pair.getExp()).add(pair);
        }

        Map<String, Set<Pair>> datasets_htp = new HashMap<String, Set<Pair>>(); //the dataset names
        Map<String, Set<Pair>> datasets_ltp = new HashMap<String, Set<Pair>>(); //the dataset names
        List<BioGenePair> smalls = new ArrayList<BioGenePair>(); //the small datasets

        for (String id : genePairByID.keySet()) {
            List<BioGenePair> pairList = genePairByID.get(id);
            Set<Pair> pairs = new HashSet<Pair>();

            for (BioGenePair pgp : pairList) {
                pairs.add(new Pair(pgp.getOrf1(), pgp.getOrf2()));
            }

            if (pairs.size() >= threshold) {
                String dsName = pairList.get(0).getInfo();
                
                datasets_htp.put(dsName, pairs);
            } else {
                smalls.addAll(pairList);
            }
        }
        
        System.out.println("Number of htp datasets: " + datasets_htp.size());

        Map<String, List<BioGenePair>> genePairByType = new HashMap<String, List<BioGenePair>>();
        for (String type : bgd.getTypes()) {
            genePairByType.put(type, new ArrayList<BioGenePair>());
        }
        for (BioGenePair bgp : smalls) {
            genePairByType.get(bgp.getType()).add(bgp);
        }

        for (String type : genePairByType.keySet()) {
            List<BioGenePair> pairList = genePairByType.get(type);
            if (!pairList.isEmpty()) {
                Set<Pair> pairs = new HashSet<Pair>();
                for (BioGenePair pgp : pairList) {
                    pairs.add(new Pair(pgp.getOrf1(), pgp.getOrf2()));
                }
                datasets_ltp.put(type, pairs);
            }
        }

        System.out.println("Number of datasets: " + datasets_ltp.size());

        Set<String> allGenes = new HashSet<String>();
        for (Pair p : allPairs) { //get a list of all the genes
            allGenes.add(p.getOrf1());
            allGenes.add(p.getOrf2());
        }

        return new BioGRID(datasets_htp, datasets_ltp, bgd.getVersion(), allGenes, allPairs, smalls);
    }

    public void writeLogFile(BioGRID bio)
            throws IOException {
        PrintWriter out = null;
        try {
            String outFileName = "V" + bio.getVersion() + "ExtractionData.txt";
            System.out.println("writing extraction summary to " + outFileName);//summarise the extraction data
            out = new PrintWriter(new BufferedWriter(new FileWriter(outFileName)));
            out.println("Number of Datasets = " + bio.numDatasets());

            for (String s : bio.getHTPBioGRID().keySet()) {
                out.println(s + "\t" + bio.getHTPBioGRID().get(s).size());
            }
            for (String s : bio.getLTPBioGRID().keySet()) {
                out.println(s + "\t" + bio.getLTPBioGRID().get(s).size());
            }

            out.println();
            out.println("There are " + bio.getPairs().size() + " unique pairs");

            out.flush();
            out.close();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
    
}
