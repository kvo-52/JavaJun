package gb.lesson1.hw01;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Stream;
import java.util.Scanner;

public class Main {
    public static void main (String[]arg){
        System.out.println("Добро божаловать! Мы поможем посчитать среднее значение всех четных чисел.");
        Scanner Scanner =new Scanner(System.in);
        System.out.print("Введите числа: ");
        int number=Scanner.nextInt();
        Scanner.close();

        List<Integer> numbers1 = List.of(number);
        List<Integer> numbers2 = List.of(number); // проверка списка без четных чисел

        OptionalDouble average = numbers1.stream()
                .filter(n -> n % 2 == 0)
                .mapToInt(Integer::intValue)
                .average();

        //Integer odd = numbers1.stream().filter(p -> p % 2 != 0).reduce((c1, c2) -> c1 + c2).orElse(0);

        if (average.isPresent()) {
            System.out.println("Среднее значение всех четных чисел в списке: " + average.getAsDouble());
        } else {
            System.out.println("В списке нет четных чисел.");
        }
    }
}
