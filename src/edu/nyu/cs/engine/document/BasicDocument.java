package edu.nyu.cs.engine.document;

/**
 * @author shenli
 * <p>
 * The {@code BasicDocument} class represents the basic information of searching documents. Conceptually, it 
 * is the root of the basic implementation, and provides the primary access to the document's data.
 * <p>
 * {@code BasicDocument} are constant; their value could not be changed after they are created. Because 
 * {@code BasicDocument} objects are immutable they could be shared.
 */
public class BasicDocument {
    private final int id;
    private final String title;
    private final String url;
    private final float pageRank;
    private final int numberOfViews;
    private volatile int hashCode;
    
    /**
     * @author shenli
     * <p>
     * The {@code BasicDocumentBuilder} class represents building basic document information
     * <p>
     * {@code BasicDocumentBuilder} objects are not constant; their values can be changed after they are
     * created. The {@code BasicDocumentBuilder} object is not thread-safe. To use it concurrently, user 
     * must surround each method invocation with external synchronization of the users' choosing.
     */
    public static class BasicDocumentBuilder implements DocumentBuilder<BasicDocument> {
        private final int id;
        private String title = "";
        private String url = "";
        private float pageRank = 0.0f;
        private int numberOfViews = 0;
        
        /**
         * Initializes a newly created {@code BasicDocumentBuilder} object so that it records basic document 
         * information.
         * <p>
         * @param id the document id
         */
        public BasicDocumentBuilder(int id) {
            this.id = id;
        }
        
        /**
         * Returns this {@code BasicDocumentBuilder} with specified document title.
         * <p>
         * @param title the document title
         * @return this {@code BasicDocumentBuilder} with specified document title
         */
        public BasicDocumentBuilder title(String title) {
            this.title = title;
            return this;
        }
        
        /**
         * Returns this {@code BasicDocumentBuilder} with specified document url.
         * <p>
         * @param url the document url
         * @return this {@code BasicDocumentBuilder} with specified document url
         */
        public BasicDocumentBuilder url(String url) {
            this.url = url;
            return this;
        }
        
        /**
         * Returns this {@code BasicDocumentBuilder} with specified page rank.
         * <p>
         * @param pageRank the page rank
         * @return this {@code BasicDocumentBuilder} with specified page rank
         */
        public BasicDocumentBuilder pageRank(float pageRank) {
            this.pageRank = pageRank;
            return this;
        }
        
        /**
         * Returns this {@code BasicDocumentBuilder} with specified number of views.
         * <p>
         * @param numberOfViews the number of views
         * @return this {@code BasicDocumentBuilder} with specified number of views
         */
        public BasicDocumentBuilder numberOfViews(int numberOfViews) {
            this.numberOfViews = numberOfViews;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public BasicDocument build() {
            return new BasicDocument(this);
        }
        
    }
    
    /**
     * Initializes a newly created {@code BasicDocument} object so that it records basic document information.
     * <p>
     * @param builder the builder pattern object
     */
    private BasicDocument(BasicDocumentBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.url = builder.url;
        this.pageRank = builder.pageRank;
        this.numberOfViews = builder.numberOfViews;
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
     * Returns the document page rank.
     * <p>
     * @return the document page rank
     */
    public float getPageRank() {
        return pageRank;
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
     * Compares the specified object with this {@code BasicDocument} for equality. Returns true if and only 
     * if the specified object is also a {@code BasicDocument} object, both objects have the same document id, 
     * title, url, page rank as well as number of views.
     * <p>
     * This implementation first checks if the specified object is this {@code BasicDocument}. If so, it 
     * returns true; if not, it checks if the specified object is a {@code BasicDocument} object. If not, it 
     * returns false; if so, it iterates over both {@code BasicDocument} objects, comparing corresponding 
     * fields. If any comparison returns false, this method returns false. Otherwise it returns true when the 
     * iterations complete. 
     * <p>
     * @param o the object to be compared for equality with this {@code BasicDocument} object
     * @return true if the specified object is equal to this {@code BasicDocument} object
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BasicDocument)) {
            return false;
        }
        BasicDocument bd = (BasicDocument) o;
        return id == bd.id
                && title.equals(bd.title)
                && url.equals(bd.url)
                && Float.compare(pageRank, bd.pageRank) == 0
                && numberOfViews == bd.numberOfViews;
    }
    
    /**
     * Returns the hash code value for this {@code BasicDocument} object.
     * <p>
     * @return the hash code value for this {@code BasicDocument} object
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            final int prime = 31;
            result = 17;
            result = result * prime + id;
            result = result * prime + title.hashCode();
            result = result * prime + url.hashCode();
            result = result * prime + Float.floatToIntBits(pageRank);
            result = result * prime + numberOfViews;
            hashCode = result;
        }
        return result;
    }
    
    /**
     * Returns the string representation of this {@code BasicDocument} object. The string consists of document 
     * id, title, url, page rank as well as number of views in order.
     * <p>
     * @return string comprising the all basic document information
     */
    @Override
    public String toString() {
        return String.format(
                "BasicDocument={id: %s, title: %s, url: %s, page rank: %s, number of views: %s}", 
                id, title, url, pageRank, numberOfViews);
    }
    
}
