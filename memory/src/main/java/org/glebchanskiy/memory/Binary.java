package org.glebchanskiy.memory;

import java.util.*;
import java.util.stream.Collectors;

public final class Binary implements Iterable<Boolean>, Comparable<Binary> {

    private static final int DEFAULT_BIT_COUNT = 1 << 4;
    private final List<Boolean> bits;

    private Binary(List<Boolean> bits) {
        this.bits = bits;
    }

    public Binary(Binary binary) {
        this.bits = binary.bits;
    }

    public int getBitCount() {
        return this.bits.size();
    }

    @Override
    public Iterator<Boolean> iterator() {
        return new BinaryIterator(this);
    }

    @Override
    public String toString() {
        return "[" + bits.stream()
                .map(b -> Boolean.TRUE.equals(b) ? "1" : "0")
                .collect(Collectors.joining()) +
                "]:[" + toDecimal(this) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Binary binary = (Binary) o;
        return Objects.equals(bits, binary.bits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bits);
    }

    /**
     * Используется для сравнения бинарных чисел.
     */
    @Override
    public int compareTo(Binary o) {
        int size = Math.min(o.getBitCount(), this.getBitCount());

        for (int i = 0; i < size; i++)
            if (this.bits.get(i) && !o.bits.get(i))
                return 1;
            else if (!this.bits.get(i) && o.bits.get(i))
                return -1;

        return 0;
    }

    /**
     * Создаёт 16-ти битное число.
     * Пример: valueOf(562) -> 0000001000110010
     */
    public static Binary valueOf(int number) {
        return toBinary(number, DEFAULT_BIT_COUNT);
    }

    /**
     * Создаёт ${bitCount} битное число.
     * Пример: valueOf(3, 4) -> 0011
     */
    public static Binary valueOf(int number, int bitCount) throws IllegalArgumentException {
        if (bitCount > 64 || bitCount < 2)
            throw new IllegalArgumentException("Wrong bit count. 64 > bitCount > 4. Received: " + bitCount);
        return toBinary(number, bitCount);
    }

    private static Binary toBinary(int number, int bitCount) {
        List<Boolean> direct = new ArrayList<>();

        while (number != 0) {
            direct.add(number % 2 == 1);
            number /= 2;
        }

        while (direct.size() < bitCount)
            direct.add(false);

        Collections.reverse(direct);
        return new Binary(direct);
    }

    private static Integer toDecimal(Binary binary) {
        int decimal = 0;
        int power = binary.getBitCount();
        for (Boolean bit : binary) {
            power--;
            if (Boolean.TRUE.equals(bit))
                decimal += Math.pow(2, power);
        }
        return decimal;
    }

    private static class BinaryIterator implements Iterator<Boolean> {
        public BinaryIterator(Binary binary) {
            this.binary = binary;
        }
        private final Binary binary;
        private int position;

        @Override
        public boolean hasNext() {
            return position < binary.getBitCount();
        }

        @Override
        public Boolean next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return binary.bits.get(position++);
        }
    }

}

