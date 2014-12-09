package edu.nyu.cs.engine.document;

/**
 * @author shenli
 * <p>
 * The {@code ScoredDocument} class represents the searching documents with score which evaluated by specific 
 * ranker type. Conceptually, it is the root of the scored documentation implementation, and provides the 
 * primary access to the scored document's data.
 * <p>
 * {@code ScoredDocument} are constant; their value could not be changed after they are created. Because 
 * {@code ScoredDocument} objects are immutable they could be shared.
 */
public final class ScoredDocument implements Comparable<ScoredDocument> {
    private final SearchDocument document;
    private final double score;
    private volatile int hashCode;
    
    public ScoredDocument(SearchDocument document, double score) {
        this.document = document;
        this.score = score;
    }

    /**
     * Returns the basic document.
     * <p>
     * @return the basic document
     */
    public SearchDocument getDocument() {
        return document;
    }

    /**
     * Returns the document score.
     * <p>
     * @return the document score
     */
    public double getScore() {
        return score;
    }
    
    /**
     * Compares the specified object with this {@code ScoredDocument} for equality. Returns true if and only 
     * if the specified object is also a {@code ScoredDocument} object, both objects have the same basic 
     * document and score.
     * <p>
     * This implementation first checks if the specified object is this {@code ScoredDocument}. If so, it 
     * returns true; if not, it checks if the specified object is a {@code ScoredDocument} object. If not, it 
     * returns false; if so, it iterates over both {@code ScoredDocument} objects, comparing corresponding 
     * fields. If any comparison returns false, this method returns false. Otherwise it returns true when the 
     * iterations complete. 
     * <p>
     * @param o the object to be compared for equality with this {@code ScoredDocument} object
     * @return true if the specified object is equal to this {@code ScoredDocument} object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ScoredDocument)) {
            return false;
        }
        ScoredDocument sd = (ScoredDocument) o;
        return Double.compare(score, sd.score) == 0;
    }
    
    /**
     * Returns the hash code value for this {@code ScoredDocument} object.
     * <p>
     * @return the hash code value for this {@code ScoredDocument} object
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            final int prime = 31;
            result = 17;
            result = result * prime + 
                    (int) (Double.doubleToLongBits(score) ^ (Double.doubleToLongBits(score) >>> 32));
            hashCode = result;
        }
        return result;
    }
    
    /**
     * Returns the string representation of this {@code ScoredDocument} object. The string consists of basic 
     * document and document score in order.
     * <p>
     * @return string comprising the scored document information
     */
    @Override
    public String toString() {
        return String.format(
                "ScoredDocument={document: %s, score: %.3f}", document, score);
    }

    /**
     * Compares the scored documents represented by two {@code ScoredDocument} objects. Returns a negative 
     * integer, zero, or a positive integer as this object is less than, equal to, or greater than the 
     * specified object.
     * <p>
     * @param sd the {@code ScoredDocument} to be compared.
     * @return the value 0 if the scored document represented by the argument is equal to the scored document
     * represented by this {@code ScoredDocument}; a value less than 0 if the scored document of this 
     * {@code ScoredDocument} is less than the scored document represented by the argument; and a value greater 
     * than 0 if the scored document of this {@code ScoredDocument} is greater than the scored document 
     * represented by the argument.
     */
    @Override
    public int compareTo(ScoredDocument sd) {
        return Double.compare(score, sd.score);
    }
    
}
