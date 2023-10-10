package co.inventorsoft.academy.collections.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Task: design collection which represents a range. It needs to support all number based classes.
 * It should be based on collection framework’s Set interface. Float and double ranges should work with one
 * digit precision after comma. Custom types can be added via implementation of Comparable.
 */


public final class ImmutableRange<T extends Comparable<T>> implements Set<T> {
    private final T min;
    private final T max;
    private final Function<T, T> increment;
    private final int size;

    // constructor to create a range with a custom increment
    private ImmutableRange(T start, T end, Function<T, T> increment) {
        Objects.requireNonNull(start, "Start element cannot be null");
        Objects.requireNonNull(end, "End element cannot be null");
        Objects.requireNonNull(increment, "Increment function cannot be null");
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("Start element cannot be greater than end element");
        }

        // if start and end elements are equal, then range is empty
        if(start.equals(end)) {
            this.min = null;
            this.max = null;
            this.increment = null;
            size = 0;
            return;
        }

        this.min = start;
        this.max = end;
        this.increment = makeDeepCopy(increment);
        size = calculateSize();
    }

    private Function<T, T> makeDeepCopy(Function<T, T> original) {
        throw new UnsupportedOperationException("makeDeepCopy operation is not supported");
    }


    //method to create a range of elements with a custom increment using addRange method
    public static <T extends Comparable<T>> ImmutableRange<T> of(T start, T end, Function<T, T> increment) {
        return new ImmutableRange<>(start, end, increment);
    }

    public static <T extends Number & Comparable<T>> ImmutableRange<T> of(T start, T end) {
        return of(start, end, getIncrementFunction(start));
    }


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

    @Override
    public int size() {
        return size;
    }

    // calculate size of the range using the iterator
    private int calculateSize() {
        int size = 0;
        for (T ignored : this) {
            size++;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    // find the element in the range using compareTo method
    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }

        if (o == null) {
            throw new NullPointerException("Element cannot be null");
        }

        T element = (T) o;
        return element.compareTo(min) >= 0 && element.compareTo(max) <= 0;
    }


    // iterator to iterate over the range
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private T element = min;

            @Override
            public boolean hasNext() {
                return element.compareTo(max) <= 0;
            }

            @Override
            public T next() {
                T currentElement = element;
                element = increment.apply(element);
                return currentElement;
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("toArray operation is not supported");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("toArray operation is not supported");
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException("Add operation is not supported");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Remove operation is not supported");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("addAll operation is not supported");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("RemoveAll operation is not supported");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("containsAll operation is not supported");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll operation is not supported");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear operation is not supported");

    }
}

