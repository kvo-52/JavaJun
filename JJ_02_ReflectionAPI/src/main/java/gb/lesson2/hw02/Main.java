package gb.lesson2.hw02;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        Animal[] animals = {
                new Dog("Лися", 3),
                new Cat("Соня", 5),
                new Dog("Джек", 9),
                new Cat("Уголек", 4),
                new Cat("Барсик", 2)
        };

        Set<Class<?>> classSet = new HashSet<>();
        for (Animal a : animals) {
            classSet.add(a.getClass());
        }
        for (Class<?> clazz : classSet) {
            System.out.println("Класс: " + clazz.getName());
            System.out.println("Поля класса");
            Arrays.stream(clazz.getDeclaredFields())
                    .forEach(field -> System.out.println("\t" + field.getName()+" в классе"));
            Arrays.stream(clazz.getSuperclass().getDeclaredFields())
                    .forEach(field -> System.out.println("\t" + field.getName()+" в суперклассе"));
            System.out.println("Методы класса");
            Arrays.stream(clazz.getDeclaredMethods())
                    .forEach(field -> System.out.println("\t" + field.getName()+" в классе"));
            Arrays.stream(clazz.getSuperclass().getDeclaredMethods())
                    .forEach(field -> System.out.println("\t" + field.getName()+" в суперклассе"));
            System.out.println("-----------------------------------------------");

        }
        for (Animal a : animals) {
            System.out.println(a);
            try {
                Method makeSoundMethod = a.getClass().getMethod("makeSound");
                makeSoundMethod.invoke(a);
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {}
        }

    }
}