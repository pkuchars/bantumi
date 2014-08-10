package org.bantumi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class Bucket {
    public final Integer beanCount;

    public Bucket(Integer beanCount) {
        this.beanCount = beanCount;
    }

    public Bucket withBeansAdded(int beansAdded) {
        return new Bucket(this.beanCount + beansAdded);
    }

    public Boolean isEmpty(){
        return beanCount == 0;
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
        return "Bucket{" +
                "beanCount=" + beanCount +
                '}';
    }
}
