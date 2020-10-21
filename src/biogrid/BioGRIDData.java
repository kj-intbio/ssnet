/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biogrid;

import java.util.*;
/**
 * Created by IntelliJ IDEA.
 * User: nmrp3
 * Date: 07-Dec-2009
 * Time: 16:43:07
 * 
 * @author CWMS3
 * Last edited: 20/10/20
 */

public class BioGRIDData {
    
    private int version = 0;
    private Set<String> ids = new HashSet<String>();  //stores the dataset ids
    private Set<String> types = new HashSet<String>();  //stores the <100 interaction data types
    private List<BioGenePair> lines = new ArrayList<BioGenePair>();  //stores the BioGRID data

    public BioGRIDData() {
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public List<BioGenePair> getLines() {
        return lines;
    }

    public void setLines(List<BioGenePair> lines) {
        this.lines = lines;
    }
    
}
