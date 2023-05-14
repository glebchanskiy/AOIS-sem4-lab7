package org.glebchanskiy.memory;

import org.glebchanskiy.memory.util.SortOrder;

import java.util.*;

public class Memory {
    private static final int DEFAULT_POOL_SIZE = 1 << 4;
    private final List<Binary> entry;

    public Memory() {
        this(DEFAULT_POOL_SIZE);
    }

    /**
     * Инициализация памяти "нулями".
     */
    public Memory(int poolSize) {
        this.entry = new ArrayList<>();
        for (int i = 0; i < poolSize; i++)
            this.entry.add(Binary.valueOf(0));
    }

    /**
     * Инициализация памяти списком значений.
     */
    public Memory(List<Binary> initialState) {
        this.entry = new ArrayList<>(initialState);
    }

    /**
     * Поиск ближайшего снизу значения.
     */
    public Binary nearestBelow(Binary binary) {
        return findMax(findSelection(binary, true));
    }

    /**
     * Поиск ближайшего сверху значения.
     */
    public Binary nearestAbove(Binary binary) {
        return findMin(findSelection(binary, false));
    }

    /**
     * Делает выборку всех чисел больше (меньше) переданной величины.
     */
    private List<Binary> findSelection(Binary binary, boolean lessSelection) {
        List<Binary> selection = new ArrayList<>();
        for (Binary bin : this.entry) {
            if (lessSelection) {
                if (binary.compareTo(bin) > 0)
                    selection.add(bin);
            }
            else {
                if (binary.compareTo(bin) < 0)
                    selection.add(bin);
            }
        }
        return selection;
    }

    /**
     * Поиск максимального число в выборке.
     */
    private Binary findMax(List<Binary> selection) {
        if (selection.isEmpty())
            return null;

        Binary max = selection.get(0);
        for (Binary bin : selection) {
            if (max.compareTo(bin) < 0)
                max = bin;
        }
        return max;
    }

    /**
     * Поиск минимального числа в выборке.
     */
    private Binary findMin(List<Binary> selection) {
        if (selection.isEmpty())
            return null;

        Binary min = selection.get(0);
        for (Binary bin : selection) {
            if (min.compareTo(bin) > 0)
                min = bin;
        }
        return min;
    }

    /**
     * Упорядоченная выборка (сортировка).
     * Заключается в пошаговом поиске максимального или
     * минимального значения в наборе чисел, отсеянных после прохождения
     * предыдущего этапа сортировки.
     */
    public List<Binary> orderedSelection(SortOrder sortOrder) {
        List<Binary> selection = new ArrayList<>(this.entry);
        List<Binary> result = new ArrayList<>();

        while (!selection.isEmpty()) {
            Binary target = null;
            if (sortOrder == SortOrder.DIRECT)
                target = findMin(selection);
            else
                target = findMax(selection);

            result.add(target);
            selection.remove(target);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int i = 0;
        for (Binary bin : this.entry) {
            output.append(String.format("[%#x] ", i)).append(bin.toString()).append('\n');
            i++;
        }
        return "Memory{\n" + output + '}';
    }
}



//    public void sort(SortOrder sortOrder) {
//        quickSort(0, this.entry.size()-1, sortOrder == SortOrder.REVERSE);
//    }
//
//    private void quickSort(int low, int high, boolean reversed) {
//        int l = low;
//        int h = high;
//        Binary mid = this.entry.get((l + h) / 2);
//        do {
//            if (reversed) {
//                while (this.entry.get(l).compareTo(mid) > 0)
//                    l++;
//                while (this.entry.get(h).compareTo(mid) < 0)
//                    h--;
//            } else {
//                while (this.entry.get(l).compareTo(mid) < 0)
//                    l++;
//                while (this.entry.get(h).compareTo(mid) > 0)
//                    h--;
//            }
//            if (l <= h) {
//                Binary temp;
//                temp = this.entry.get(l);
//                this.entry.set(l, this.entry.get(h));
//                this.entry.set(h, temp);
//                l++;
//                h--;
//            }
//        } while (l <= h);
//        if (low < h)
//            quickSort(low, h, reversed);
//        if (l < high)
//            quickSort(l, high, reversed);
//    }