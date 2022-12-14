package core.basesyntax;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.IntStream;


public class ArrayList<T> implements List<T> {// let`s eliminate redundant empty lines
    private static final int DEF_CAPACITY = 10;  // according to java code convention, constant names should be in upper case
    private static final Object[] EMPTY_ARRAY_DATA = {}; // according to java code convention, constant names should be in upper case
    private T[] array = (T[]) new Object[DEF_CAPACITY]; // it is better to use constructor for initialization
    private int size;
    private int cursor; // it is possible to use this variable without explicit initialization, but it is better to do it explicitly
    private static final double MULTIPLIER = 1.5; // we can use this constant instead of creating new one in grow() method

    private String errorMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    private Object[] grow() {
        int oldCapacity = array.length;
        if (oldCapacity > 0 || array != EMPTY_ARRAY_DATA) {
            int newCapacity = (int) (oldCapacity * MULTIPLIER + 1);
            this.array = Arrays.copyOf(
                    array,
                    newCapacity); // it is better to initialize variable before return
            return array;
        }
        return array; // we can just return array which we already have
        // it is better to return variable without else statement because it is more readable
    }


    public static Object[] listToArray(List<?> list) {
        if (!list.isEmpty()) {
            // it is better to use IntStream here
            return IntStream.range(0, list.size()).mapToObj(list::get).toArray();
        }
        return new Object[0]; // it is better to return empty array instead of null
    }


    @Override
    public void add(T value) {
        add(value, cursor++); // it is better to use this instead of ArrayList.this or just add(value, index)
        // we can use cursor ++ instead of int index = cursor; add(value, index); cursor ++;
        // it is better to use cursor++ instead of this
    }


    @Override
    public void add(T value, int index) {
        checkRangeAddRemove(index);
        if (size == array.length) { // it is better to use size instead of initializing new variable
            array = (T[]) grow();
        }
        System.arraycopy(array, index,
            array, index + 1,
            size - index);
        array[index] = value;
        size++;
    }

    private void checkRangeAddRemove(int index) { // it is better to use this method for checking range instead of repeating code
        if (index > size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(errorMsg(index));
        }
    }


    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            T[] tempArray = (T[]) listToArray(list);
            int newCapacity = size + tempArray.length;
            array = Arrays.copyOf(array, newCapacity);
            System.arraycopy(tempArray, 0, array, size, tempArray.length);
            size = newCapacity;
        }
    }


    @Override
    public T get(int index) {
        checkRangeGetSet(index);
        return array[index]; // it is better to return variable without else statement because it is better for understanding
    }


    @Override
    public void set(T value, int index) {
        checkRangeGetSet(index);
        array[index] = value; // it is better to return variable without else statement because it is better for understanding
    }

    private void checkRangeGetSet(int index) { // it is better to use this method for checking range instead of repeating code
        if (index >= size || index < 0) {
            throw new ArrayListIndexOutOfBoundsException(errorMsg(index));
        }
    }


    @Override
    public T remove(int index) {
        checkRangeAddRemove(index);
        final T oldValue = get(index);
        size--; // it is better to use size-- instead of new variable
        if (size > index) {
            System.arraycopy(
                    array,
                    index + 1,
                    array,
                    index,
                    size - index);
        }
        array[size] = null;
        return oldValue;
    }


    @Override
    public T remove(T element) {
        int index = 0;


        //found: // it is very bad practice to use this way of exit


        while (index < size) {
            if (Objects.equals(element, array[index])) { // it is better to use Objects.equals() instead of
                                                                // == or != for comparing objects without repeating code
                remove(index);
                return element;
            }
            index++;
        }
        throw new NoSuchElementException();

    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        /* boolean empty = true;
        for (T element : array) {
            if (element != null) {
                empty = false;
                break;
            }
        } */
        // it is better to use stream here to check if array is empty
        return Arrays.stream(array).allMatch(Objects::isNull);
    }
}
