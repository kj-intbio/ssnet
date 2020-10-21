package ssnet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biogrid.BioGRID;
import biogrid.Pair;

/**
 *
 * @author matthew pocock
 */
public abstract class BasicIntegration
        implements IntegratedScorer {

    private double dValue;
    private BioGRID bio;
    private boolean padWithZeros;

    public double getDValue() {
        return dValue;
    }

    public void setDValue(double dValue) {
        this.dValue = dValue;
    }

    public BioGRID getBio() {
        return bio;
    }

    public void setBio(BioGRID bio) {
        this.bio = bio;
    }

    public boolean isPadWithZeros() {
        return padWithZeros;
    }

    public void setPadWithZeros(boolean padWithZeros) {
        this.padWithZeros = padWithZeros;
    }

    protected Map<Pair, Double> doIntegration(
            final Map<String, Double> lls, final Map<String, Double> ranking, final Comparator<Double> ordering) {

        List<String> datasetsRankedByScore = new ArrayList<String>(lls.keySet());
        Collections.sort(datasetsRankedByScore, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return ordering.compare(ranking.get(o1), ranking.get(o2));
            }
        });
        System.out.println("Ranked: " + datasetsRankedByScore);

        Map<Pair, Double> scores = new HashMap<Pair, Double>();

        for (Pair pair : bio.getPairs()) {
            List<Double> scoresInOrder = new ArrayList<Double>();
            for (String ds : datasetsRankedByScore) {

                if (bio.getBioGRID().get(ds).contains(pair)) {
                    Double score = lls.get(ds);
                    if (score == null) {
                        throw new NullPointerException("Null score for: " + ds);
                    }
                    scoresInOrder.add(score);
                } else {
                    if (padWithZeros) {
                        scoresInOrder.add(0.0);
                    }
                }
            }

            double finalScore = calculateScore(scoresInOrder, dValue);
            if (finalScore > 0) {
                scores.put(pair, finalScore);
            }
        }

        return scores;
    }

    public double calculateScore(List<Double> scores, double dValue) {
        double denominator;
        double LLS = 0.0;

        try {
            for (int i = 0; i < scores.size(); i++) {
                denominator = Math.pow(dValue, i);
                LLS += scores.get(i) / denominator;
            }
        } catch (NullPointerException e) {
            System.out.println(scores);
            throw e;
        }

        return LLS;
    }

}
