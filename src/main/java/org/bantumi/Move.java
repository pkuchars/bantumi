package org.bantumi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Move {
    private final Integer bucketIndex;

    public Move(Integer bucketIndex) {
        this.bucketIndex = bucketIndex;
    }

    public Integer getBucketIndex() {
        return bucketIndex;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "Move{" +
                "i=" + bucketIndex +
                '}';
    }
}
