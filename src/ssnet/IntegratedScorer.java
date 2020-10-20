/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssnet;

import java.util.Map;
import biogrid.Pair;
/**
 *
 * @author CWMS3
 */
public interface IntegratedScorer {
        public Map<Pair, Double> integrate(Map<String, Double> scores);

}
