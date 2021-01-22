package org.subra.aem.rjs.core.commons.utils;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author Raghava Joijode
 */
public class RJSCollectionUtils {

    private RJSCollectionUtils() {
        throw new IllegalStateException(this.getClass().getSimpleName());
    }

    public static <T> Predicate<T> distinctByKey(final Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <T> Stream<T> getStreamFromArray(T[] array) {
        return Arrays.stream(array);
    }

    public static <T> T[] toArray(List<T> list, T[] init) {
        return list.toArray(init);
    }

    public static <T> List<T> toList(T[] array) {
        return Arrays.asList(array);
    }

}