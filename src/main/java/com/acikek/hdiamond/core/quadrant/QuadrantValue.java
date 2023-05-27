package com.acikek.hdiamond.core.quadrant;

import com.acikek.hdiamond.core.section.QuadrantSection;

public class QuadrantValue<E extends Enum<E> & QuadrantSection<E>> {

    private E value;

    public QuadrantValue(E value) {
        this.value = value;
    }

    public E get() {
        return value;
    }

    public E scroll() {
        this.value = value.scroll(false);
        return value;
    }

    public boolean isEmpty() {
        return value.ordinal() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QuadrantValue<?> that = (QuadrantValue<?>) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public QuadrantValue<E> copy() {
        return new QuadrantValue<>(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
