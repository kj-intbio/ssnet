package ssnet;

import biogrid.Pair;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author matthew pocock
 */
public class Integration extends BasicIntegration {

    public Integration() {
        setPadWithZeros(false);
    }

    public Map<Pair, Double> integrate(Map<String, Double> scores) {
        return doIntegration(scores, scores, Collections.<Double>reverseOrder());
    }
}
