package org.glebchanskiy;

import org.glebchanskiy.memory.Binary;
import org.glebchanskiy.memory.Memory;
import org.glebchanskiy.memory.util.SortOrder;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        lab7task1();
        lab7task2();
    }

    private static void lab7task1() {
        List<Binary> state = List.of(
                Binary.valueOf(134),
                Binary.valueOf(135),
                Binary.valueOf(562),
                Binary.valueOf(637),
                Binary.valueOf(352),
                Binary.valueOf(982),
                Binary.valueOf(123),
                Binary.valueOf(1)
        );

        Memory memory = new Memory(state);
        System.out.println("____".repeat(15));
        System.out.println("TASK1 [Поиск ближайшего сверху (снизу) значения]");
        System.out.println();

        System.out.println("initial state: " + memory);
        System.out.println();

        Binary searchFor = Binary.valueOf(562);

        System.out.println("try find above for:\n" + searchFor);
        Binary result1 = memory.nearestAbove(searchFor);
        System.out.println("result:\n" + result1);

        System.out.println();

        System.out.println("try find below for:\n" + searchFor);
        Binary result2 = memory.nearestBelow(searchFor);
        System.out.println("result:\n" + result2);

        System.out.println();

        Binary one = Binary.valueOf(1);
        System.out.println("try find below (below last) for:\n" + one);
        Binary result3 = memory.nearestBelow(one);
        System.out.println("result:\n" + result3);
    }

    private static void lab7task2() {
        List<Binary> state = List.of(
                Binary.valueOf(5),
                Binary.valueOf(34),
                Binary.valueOf(562),
                Binary.valueOf(1234),
                Binary.valueOf(352),
                Binary.valueOf(982),
                Binary.valueOf(11),
                Binary.valueOf(1)
        );

        Memory memory = new Memory(state);
        System.out.println("____".repeat(15));
        System.out.println("TASK2 [Упорядоченная выборка (сортировка)]\n");

        System.out.println("initial state: " + memory);



        System.out.println("\ndirect sort: ");
        List<Binary> selectionDirect = memory.orderedSelection(SortOrder.DIRECT);

        for (Binary binary : selectionDirect) {
            System.out.println(binary);
        }


        System.out.println("\nreversed sort: ");

        List<Binary> selectionReverse = memory.orderedSelection(SortOrder.REVERSE);

        for (Binary binary : selectionReverse) {
            System.out.println(binary);
        }
    }
}