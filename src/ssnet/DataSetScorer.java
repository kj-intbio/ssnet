package ssnet;

import biogrid.BioGRID;

import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: nmrp3 Date: 01-Dec-2009 Time: 15:46:43
 */
public interface DataSetScorer {

    public Map<String, Double> scoredDataSet(BioGRID bio);
}
