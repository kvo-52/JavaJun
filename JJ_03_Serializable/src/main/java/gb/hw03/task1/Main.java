package gb.hw03.task1;

import java.io.*;

public class Main {

    /**
     * Задача 1
     * ========
     Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
     Обеспечьте поддержку сериализации для этого класса. Создайте объект класса Student и инициализируйте его данными.
     Сериализуйте этот объект в файл. Десериализуйте объект обратно в программу из файла.
     Выведите все поля объекта, включая GPA, и обсудите, почему значение GPA не было сохранено/восстановлено.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student user1 = new Student("Katy", 17, 5.0);
        Student user2 = new Student("Nik", 19, 4.8);
        Student user3 = new Student("Djak", 18, 3.8);

        try(FileOutputStream fileOutputStream = new FileOutputStream("HW3_task_1.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(user1);
            System.out.println("\nОбъект User1 сериализован.");
            objectOutputStream.writeObject(user2);
            System.out.println("Объект User2 сериализован.");
            objectOutputStream.writeObject(user3);
            System.out.println("Объект User3 сериализован.");
        }

        try(FileInputStream fileInputStream = new FileInputStream("HW3_task_1.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            user1 = (Student)objectInputStream.readObject();
            System.out.println("\nОбъект User1 десериализован.");
            user2 = (Student)objectInputStream.readObject();
            System.out.println("Объект User2 десериализован.");
            user3 = (Student)objectInputStream.readObject();
            System.out.println("Объект User3 десериализован.");
        }

        System.out.println("\nОбъект User1:");
        System.out.println("Имя:          " + user1.getName());
        System.out.println("Возраст:      " + user1.getAge());
        System.out.println("Средний балл: " + user1.getGPA());

        System.out.println("\nОбъект User2:");
        System.out.println("Имя:          " + user2.getName());
        System.out.println("Возраст:      " + user2.getAge());
        System.out.println("Средний балл: " + user2.getGPA());

        System.out.println("\nОбъект User3:");
        System.out.println("Имя:          " + user3.getName());
        System.out.println("Возраст:      " + user3.getAge());
        System.out.println("Средний балл: " + user3.getGPA());


    }
}