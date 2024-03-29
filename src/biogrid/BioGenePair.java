package biogrid;

/**
 * Simple class which contains information about a single
 * BioGRID interaction: orf1, orf2, author, pubmed and type
 * 
 * Author: Katherine James
 * Author Matthew Pocock
 * Commenced: 26/09/07 Last edited: 16/03/09 
 * 
 * @author CWMS3
 * Last edited: 21/10/20
 */
public class BioGenePair {
    
    private String orf1;
    private String orf2;
    private String author;
    private String exp;
    private String type;

    public BioGenePair(String orf1, String orf2, String author, String exp, String type) {
        this.orf1 = orf1;
        this.orf2 = orf2;
        this.author = author;
        this.exp = exp;
        this.type = type;
    }

    public String getOrf1() {
        return orf1;
    }

    public String getOrf2() {
        return orf2;
    }

    public String getAuthor() {
        return author;
    }

    public String getExp() {
        return exp;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return author + "." + exp;//this used to add type but has been removed due to mixed types
    }

    @Override
    public boolean equals(Object p) {
        if (p == null) {
            return false;
        }
        if (!(p instanceof BioGenePair)) {
            return false;
        }
        BioGenePair p2 = (BioGenePair) p;
        return orf1.equals(p2.getOrf1()) && orf2.equals(p2.getOrf2()) || orf1.equals(p2.getOrf2()) && orf2.equals(p2.getOrf1());
    }

    @Override
    public int hashCode() {
        return orf1.hashCode() + orf2.hashCode();   //To change body of overridden methods use File | Settings | File Templates.
    }
    
}
