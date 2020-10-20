/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssnet;

import biogrid.Pair;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author CWMS3
 */
public class Integration extends BasicIntegration {
    public Integration() {
        setPadWithZeros(false);
    }

    public Map<Pair, Double> integrate(Map<String, Double> scores) {
        return doIntegration(scores, scores, Collections.<Double>reverseOrder());
    }
}
