package co.inventorsoft.academy.collections.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

/**
 * Task: design collection which represents a range. It needs to support all number based classes.
 * It should be based on collection framework’s Set interface. Float and double ranges should work with one
 * digit precision after comma. Custom types can be added via implementation of Comparable.
 */

public class Range<T extends Comparable<T>> implements Set<T> {
    private final SortedSet<T> rangeElements = new TreeSet<>();

    @Override
    public int size() {
        return rangeElements.size();
    }

    @Override
    public boolean isEmpty() {
        return rangeElements.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return rangeElements.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return rangeElements.iterator();
    }

    @Override
    public Object[] toArray() {
        return rangeElements.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return rangeElements.toArray(a);
    }

    @Override
    public boolean add(T t) {
        return rangeElements.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return rangeElements.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return rangeElements.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return rangeElements.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return rangeElements.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return rangeElements.removeAll(c);
    }

    @Override
    public void clear() {
        rangeElements.clear();
    }

    // static method to create a range of numbers with a default increment
    public static <T extends Number & Comparable<T>> Range<T> of(T start, T end) {
        return of(start, end, getIncrementFunction(start));
    }

    //method to create a range of elements with a custom increment using addRange method
    public static <T extends Comparable<T>> Range<T> of(T start, T end, Function<T, T> increment) {
        Range<T> range = new Range<>();
        range.addRange(start, end, increment);
        return range;
    }

    //static method to return function for incrementing the range depending on the type of the range
    public static <T extends Number> Function<T, T> getIncrementFunction(T element) {
        if(element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        if (element instanceof Integer) {
            return integer -> (T) Integer.valueOf((Integer) integer + 1); // Unchecked cast 'java.lang.Integer' to T?
        } else if (element instanceof Double) {
            return aDouble -> (T) Double.valueOf((Double) aDouble + 0.1);
        } else if (element instanceof Float) {
            return aFloat -> (T) Float.valueOf((Float) aFloat + 0.1f);
        } else if (element instanceof Long) {
            return aLong -> (T) Long.valueOf((Long) aLong + 1);
        } else if (element instanceof Short) {
            return aShort -> (T) Short.valueOf((short) ((Short) aShort + 1));
        } else if (element instanceof Byte) {
            return aByte -> (T) Byte.valueOf((byte) ((Byte) aByte + 1));
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }
    
    // method to add a range of elements to the set with a custom increment
    public void addRange(T start, T end, Function<T, T> increment) {
        Objects.requireNonNull(start, "Start element cannot be null");
        Objects.requireNonNull(end, "End element cannot be null");
        Objects.requireNonNull(increment, "Increment function cannot be null");

        if(start.equals(end)) {
            return;
        }

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Start element cannot be greater than end element");
        }

        T element = start;
        while (element.compareTo(end) <= 0) {
            rangeElements.add(element);
            element = increment.apply(element);
        }
    }
}