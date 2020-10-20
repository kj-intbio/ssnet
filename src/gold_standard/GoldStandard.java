/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gold_standard;

import biogrid.Pair;
import java.util.Set;

/**
 * The gold standard for log likelihood scoring Consists of a positive pair
 * list, a negative pair list and a version number
 *
 * @author CWMS3
 */
public class GoldStandard {

    private Set<Pair> positive;
    private Set<String> genes;

    public GoldStandard(Set<Pair> pos, Set<String> genes) {
        this.positive = pos;
        this.genes = genes;
    }

    public GoldStandard(Set<Pair> pos, Set<String> genes, Set<String> diseases) {
        this.positive = pos;
        this.genes = genes;
    }

    public int numberOfNegatives() {
        int gs = genes.size();
        int ps = positive.size();

        return gs * (gs - 1) / 2 - ps;
    }

    public Set<Pair> getPositive() {
        return positive;
    }

    public void setPositive(Set<Pair> positive) {
        this.positive = positive;
    }

    public Set<String> getGenes() {
        return genes;
    }

    public void setGenes(Set<String> genes) {
        this.genes = genes;
    }

}
