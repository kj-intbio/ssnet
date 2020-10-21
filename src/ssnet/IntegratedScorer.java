package ssnet;

import java.util.Map;
import biogrid.Pair;

/**
 *
 * @author matthew pocock
 */
public interface IntegratedScorer {

    public Map<Pair, Double> integrate(Map<String, Double> scores);

}
