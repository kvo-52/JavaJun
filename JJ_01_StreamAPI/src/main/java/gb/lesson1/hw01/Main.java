package gb.lesson1.hw01;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;

/* Напишите программу, которая использует Stream API для обработки списка чисел.
 *  Программа должна вывести на экран среднее значение всех четных чисел в списке. */

public class Main {
    public static void main(String[] args) {
        // Генерация массива
        Random rnd = new Random();
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < 8; i++) numberList.add(rnd.nextInt(10));

        //Вывод исходного массива
        System.out.print("Исходный массив: ");
        printlnList(numberList);

        //Вывод четных чисел (для наглядности)
        System.out.print("Четные числа массива: ");
        printlnList(numberList.stream()
                .filter(i -> i % 2 == 0)
                .collect(Collectors.toList()));

        //Среднее арифметическое четных чисел
        System.out.println("Среднее арифметическое четных чисел: " + getAverageEvenNumbers(numberList));

    }
    /**
     * Подсчет среднего занчения всех четных чисел в массиве
     *
     * @param numberList
     * @return вернет среднее арифметическое всех четных чисел в массиве, 0.0 если четных нет
     * или null, если если в качестве аргумента пришел null или пустой список
     */
    public static Double getAverageEvenNumbers(List<Integer> numberList) {
        if (numberList==null || numberList.isEmpty())return null;
        return numberList.stream().mapToInt(Integer.class::cast)
                .filter(i -> i % 2 == 0)
                .average().orElse(0);
    }

    /**
     * Вывод массива на печать
     *
     * @param numberList
     * @param <E>
     */
    public static <E> void printList(List<E> numberList) {
        if (numberList == null || numberList.isEmpty()) {
            System.out.print("[]");
            return;
        }

        System.out.print("[");
        numberList.stream()
                .limit(numberList.size() - 1)
                .forEach(e -> System.out.print(e + ", "));
        numberList.stream()
                .skip(numberList.size() - 1)
                .forEach(e -> System.out.print(e + "]"));
    }

    /**
     * Вывод массива на печать в виде [E0, E1, E2, ... En] с замыкающим \n
     *
     * @param numberList
     * @param <E>
     */
    public static <E> void printlnList(List<E> numberList) {
        printList(numberList);
        System.out.println();
    }

}
