/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssnet;

import biogrid.BioGRID;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nmrp3
 * Date: 01-Dec-2009
 * Time: 15:46:43
 * To change this template use File | Settings | File Templates.
 */
public interface DataSetScorer {
    public Map<String, Double> scoredDataSet(BioGRID bio);
}
