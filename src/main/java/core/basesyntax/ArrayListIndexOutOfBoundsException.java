package core.basesyntax;

public class ArrayListIndexOutOfBoundsException extends RuntimeException { // it is better to inherit from Exception because RuntimeException is unchecked
    public ArrayListIndexOutOfBoundsException(String message) {
        super(message);
    }
}
