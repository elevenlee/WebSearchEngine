package edu.nyu.cs.engine.query.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.nyu.cs.engine.query.SearchQuery;

/**
 * @author shenli
 * <p>
 * The {@code PhraseQuery} based implementation of the {@link edu.nyu.cs.engine.query.SearchQuery} interface.
 * This implementation represents phrase tokens list which collected based on the raw search query of splitting 
 * around the whitespace and treats all word tokens around double quote (") as a single phrase. And each phrase
 * is also a list of single word tokens which separate by whitespace.
 * <p>
 * The raw search query "foo and \"bar or\" baz", for example, yields the following phrase token results: 
 * {@literal [["foo"], ["and"], ["bar", "or"], ["baz"]]}.
 * <p>
 * Note: {@code PhraseQuery} objects are mutable; their value could be changed after they are created. Thus, 
 * {@code PhraseQuery} objects are not thread-safe. If multiple threads access a {@code PhraseQuery} instance 
 * concurrently, and at least one of the threads modifies it structurally, it must be synchronized externally.
 */
public class PhraseQuery implements SearchQuery<List<String>> {
    private final String query;
    private final List<List<String>> phrases = new ArrayList<>();
    
    /**
     * Initializes a newly created {@code PhraseQuery} object so that it records phrase tokens list which 
     * collected based on the raw search query.
     * <p>
     * @param query the raw search query
     */
    public PhraseQuery(String query) {
        this.query = query;
    }
    
    /**
     * Returns the raw search query.
     * <p>
     * @return the raw search query
     */
    @Override
    public String getQuery() {
        return query;
    }

    /**
     * Returns an unmodifiable view of the phrase token list. This method allows modules to provide users 
     * with "read-only" access to internal lists. Query operations on the returned list "read through" to the 
     * specified list, and attempts to modify the returned list, whether direct or via its iterator, result 
     * in an {@link java.lang.UnsupportedOperationException}.
     * <p>
     * @return an unmodifiable view of the specified list. If no phrase token have been collected, returns an 
     * empty list
     */
    @Override
    public List<List<String>> getTokens() {
        return Collections.unmodifiableList(phrases);
    }

    /**
     * Separate the raw search query by whitespace and treats all word tokens around double quote (") as a 
     * single phrase. This method also collect phrase tokens into the list.
     */
    @Override
    public void processQuery() {
        if (query == null) {
            return;
        }
        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(query);
        int pos = 0;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (pos != start) {
                addToken(query.substring(pos, start));
            }
            String phrase = matcher.group();
            addPhrase(phrase.substring(1, phrase.length() - 1));
            pos = end;
        }
        if (pos < query.length()) {
            addToken(query.substring(pos, query.length()));
        }
    }
    
    /**
     * Wrap the word token as a list includes single element and add it to the phrases list.
     * <p>
     * @param token the word token to be added 
     */
    private void addToken(String token) {
        Scanner scanner = new Scanner(token);
        while (scanner.hasNext()) {
            phrases.add(Arrays.asList(scanner.next()));
        }
        scanner.close();
    }
    
    /**
     * Separate the phrase to word tokens by whitespace and add them to the phrases list as a single list. If 
     * the {@code phrase} is empty (""), then ignore it.
     * <p>
     * @param phrase the phrase to be added
     */
    private void addPhrase(String phrase) {
        if (phrase.length() < 2) {
            // empty phrase "", ignore it
            return;
        }
        List<String> tokens = new ArrayList<>();
        Scanner scanner = new Scanner(phrase);
        while (scanner.hasNext()) {
            tokens.add(scanner.next());
        }
        scanner.close();
        phrases.add(Collections.unmodifiableList(tokens));
    }
    
    /**
     * Returns the string representation of this {@code PhraseQuery} object. The string consists of raw search 
     * query, and phrase tokens collected by the raw query.
     * <p>
     * @return string comprising the raw query as well as phrase tokens
     */
    @Override
    public String toString() {
        return String.format(
                "PhraseQuery={query: %s, phrases: %s}", query, phrases);
    }

}
