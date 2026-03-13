package com.example.javaprojektwoche.util;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {
    public static<T> boolean threeWayEquals(T first, T second, T third) {
        return first.equals(second) && second.equals(third);
    }

    public static <A, B> List<Map.Entry<A, B>> zip(List<A> as, List<B> bs) {
        return IntStream.range(0, Math.min(as.size(), bs.size()))
                .mapToObj(i -> Map.entry(as.get(i), bs.get(i)))
                .collect(Collectors.toList());
    }

    public static Point getPosition(Node node) {
        Integer rowIndex = GridPane.getRowIndex(node);
        Integer colIndex = GridPane.getColumnIndex(node);
        int x =  (colIndex == null ? 0 : colIndex);
        int y =  (rowIndex == null ? 0 : rowIndex);
        return new Point(x, y);
    }

    public static String getResourcePath(String resource) {
        return Objects.requireNonNull(Utils.class.getResource(resource)).toExternalForm();
    }
}
