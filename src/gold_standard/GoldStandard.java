package gold_standard;

import biogrid.Pair;
import java.util.Set;

/**
 * The gold standard for log likelihood scoring 
 *
 * Author: Katherine James Commenced: 17/03/09  Last edited: 17/03/09 
* 
 * @author CWMS3
 * Edited 21/10/20
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
