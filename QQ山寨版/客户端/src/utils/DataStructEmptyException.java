package utils;

public class DataStructEmptyException extends RuntimeException{
    public DataStructEmptyException() { }

    public DataStructEmptyException(String message) {
        super(message);
    }
}
