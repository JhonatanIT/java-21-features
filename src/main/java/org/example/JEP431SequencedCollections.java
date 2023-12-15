package org.example;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;


/**
 * interface SequencedCollection<E> extends Collection<E> {
 *     // new method
 *     SequencedCollection<E> reversed();
 *     // methods promoted from Deque
 *     void addFirst(E);
 *     void addLast(E);
 *     E getFirst();
 *     E getLast();
 *     E removeFirst();
 *     E removeLast();
 * }
 */
public class JEP431SequencedCollections {

    public static void main(String[] args) {

        var list = List.of(1,2,3,4);

        list.getFirst();
        list.getLast();
        list.reversed();

        IntStream.rangeClosed(1, list.size()).forEach(i -> System.out.println(STR."Value: \{list.get(list.size() - i)}"));
        list.reversed().forEach(i -> System.out.println(STR."Value: \{i}"));

//        //Map not implements SequencedCollection
//        var map = Map.of("key1", "value1", "key2", "value2");
//        map.firstEntry();

        //LinkedHashMap implements SequencedCollection (HashMap doesn't work)
        var map = new LinkedHashMap<>();
        map.put( "key1", "value1" );
        map.put( "key2", "value2" );

        System.out.println(map);
        System.out.println(map.firstEntry());
        map.putFirst("key0", "value0");
        System.out.println(map);
        map.pollFirstEntry();       //Delete first element
        System.out.println(map);
    }
}
