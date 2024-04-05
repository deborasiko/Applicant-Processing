package org.example;

/**
 * Custom exception for the case when CSV line is not in valid format.
 */
public class InvalidCSVLineException extends Exception{
    /**
     * Constructor without parameter.
     */
    public InvalidCSVLineException() {
    }

    /**
     * Constructor with message as parameter.
     * @param message
     */
    public InvalidCSVLineException(String message) {
        super(message);
    }
}
