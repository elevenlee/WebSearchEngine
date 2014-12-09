package edu.nyu.cs.engine.document;

import java.io.Serializable;

/**
 * @author shenli
 * <p>
 * The {@code SearchDocument} class represents the basic information of searching documents. Conceptually, it 
 * is the root of the basic document implementation, and provides the primary access to the document's data.
 * <p>
 * Note: {@code SearchDocument} objects are mutable; their value could be changed after they are created. Thus, 
 * {@code SearchDocument} objects are not thread-safe. If multiple threads access a {@code SearchDocument} 
 * instance concurrently, and at least one of the threads modifies it structurally, it must be synchronized 
 * externally.
 */
public class SearchDocument implements Serializable {
    private static final long serialVersionUID = -8692782292790157310L;
    
    private final int id;
    private final String title;
    private String url;
    private float pageRank;
    private int numberOfViews;
    
    /**
     * Initializes a newly created {@code SearchDocument} object with given document id, title and default 
     * document url, page rank as well as number of views value so that it records basic search document 
     * information.
     * <p>
     * @param id the document id
     * @param title the document title
     */
    public SearchDocument(int id, String title) {
        this(id, title, "", 0.0f, 0);
    }
    
    /**
     * Initializes a newly created {@code SearchDocument} object with given document id, title, url, page rank 
     * as well as number of views so that it records basic search document information.
     * <p>
     * @param id the document id
     * @param title the document title
     * @param url the document url
     * @param pageRank the document page rank
     * @param numberOfViews the document number of views 
     */
    public SearchDocument(int id, String title, String url, float pageRank, int numberOfViews) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.pageRank = pageRank;
        this.numberOfViews = numberOfViews;
    }

    /**
     * Returns the document id.
     * <p>
     * @return the document id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the document title.
     * <p>
     * @return the document title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the document url.
     * <p>
     * @return the document url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Sets this {@code SearchDocument}'s document url with the given {@code url}.
     * <p>
     * @param url the document url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the document page rank.
     * <p>
     * @return the document page rank
     */
    public float getPageRank() {
        return pageRank;
    }
    
    /**
     * Sets this {@code SearchDocument}'s document page rank with the given {@code pageRank}.
     * <p>
     * @param pageRank the document page rank to set
     */
    public void setPageRank(float pageRank) {
        this.pageRank = pageRank;
    }

    /**
     * Returns the number of views.
     * <p>
     * @return the number of views
     */
    public int getNumberOfViews() {
        return numberOfViews;
    }
    
    /**
     * Sets this {@code SearchDocument}'s document number of views with the given {@code numberOfViews}.
     * <p>
     * @param numberOfViews the document number of views to set
     */
    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
    }
    
    /**
     * Compares the specified object with this {@code SearchDocument} for equality. Returns true if and only 
     * if the specified object is also a {@code SearchDocument} object, both objects have the same document 
     * id, title, url, page rank as well as number of views.
     * <p>
     * This implementation first checks if the specified object is this {@code SearchDocument}. If so, it 
     * returns true; if not, it checks if the specified object is a {@code SearchDocument} object. If not, it 
     * returns false; if so, it iterates over both {@code SearchDocument} objects, comparing corresponding 
     * fields. If any comparison returns false, this method returns false. Otherwise it returns true when the 
     * iterations complete. 
     * <p>
     * @param o the object to be compared for equality with this {@code SearchDocument} object
     * @return true if the specified object is equal to this {@code SearchDocument} object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SearchDocument)) {
            return false;
        }
        SearchDocument bd = (SearchDocument) o;
        return id == bd.id
                && title.equals(bd.title)
                && url.equals(bd.url)
                && Float.compare(pageRank, bd.pageRank) == 0
                && numberOfViews == bd.numberOfViews;
    }
    
    /**
     * Returns the hash code value for this {@code SearchDocument} object.
     * <p>
     * @return the hash code value for this {@code SearchDocument} object
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;
        result = result * prime + id;
        result = result * prime + title.hashCode();
        result = result * prime + url.hashCode();
        result = result * prime + Float.floatToIntBits(pageRank);
        result = result * prime + numberOfViews;
        return result;
    }
    
    /**
     * Returns the string representation of this {@code SearchDocument} object. The string consists of document 
     * id, title, url, page rank as well as number of views in order.
     * <p>
     * @return string comprising the all basic search document information
     */
    @Override
    public String toString() {
        return String.format(
                "BasicDocument={id: %d, title: %s, url: %s, page rank: %.3f, number of views: %d}", 
                id, title, url, pageRank, numberOfViews);
    }
    
}
