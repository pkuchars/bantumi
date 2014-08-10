package org.bantumi;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Buckets  extends ArrayList<Bucket>{

    public static Collector<Bucket, ?, Buckets> bucketCollector = Collectors.toCollection(() -> new Buckets());

    public static Buckets zeros(Integer count) {
        return (IntStream.range(0, count).boxed().map(i -> new Bucket(0)).collect(bucketCollector));
    }

    public Integer sumOfBeans(){
        return stream().mapToInt(bucket -> bucket.beanCount).sum();
    }

    public Boolean areAllEmpty() {
        return stream().allMatch(Bucket::isEmpty);
    }
}
