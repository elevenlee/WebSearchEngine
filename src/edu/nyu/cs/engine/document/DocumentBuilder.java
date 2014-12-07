package edu.nyu.cs.engine.document;

/**
 * @author shenli
 * <p>
 * A class can implement the {@code DocumentBuilder} interface when it wants to impose a building on the objects.
 */
public interface DocumentBuilder<T> {

    /**
     * The method is called whenever the object is built. Returns an instance with specified parameter(s).
     * <p>
     * @return an instance with specified parameter(s)
     */
    public T build();
    
}
