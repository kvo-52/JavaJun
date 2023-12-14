package gb.lesson3.task01;

import java.io.*;

public class Program {
    /**
     * Задача 1
     * ========
     * <p>
     * Создайте класс UserData с полями String name, int age, transient String password.
     * Поле password должно быть отмечено ключевым словом transient.
     * Реализуйте интерфейс Serializable в вашем классе.
     * В методе main создайте экземпляр класса UserData и инициализируйте его данными.
     * Сериализуйте этот объект в файл, используя ObjectOutputStream в сочетании с FileOutputStream
     * <p>
     * Задача 2
     * ========
     * <p>
     * Десериализуйте объект из ранее созданного файла обратно в объект Java,
     * используя ObjectInputStream в сочетании с FileInputStream.
     * Выведите данные десериализованного объекта UserData.
     * Сравните данные до сериализации и после десериализации, особенно обратите внимание на поле,
     * помеченное как transient.
     * Обсудите, почему это поле не было сохранено после десериализации.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UserData user1 = new UserData("Алексей", 50, "54321");
        UserData user2 = new UserData("Николай", 37, "12345");

        try(FileOutputStream fileOutputStream = new FileOutputStream("userdata.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
            objectOutputStream.writeObject(user1);
            System.out.println("Объект User1 сериализован.");
            objectOutputStream.writeObject(user2);
            System.out.println("Объект User2 сериализован.");
        }

        try(FileInputStream fileInputStream = new FileInputStream("userdata.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            user1 = (UserData)objectInputStream.readObject();
            System.out.println("Объект User1 десериализован.");
            user2 = (UserData)objectInputStream.readObject();
            System.out.println("Объект User2 десериализован.");
        }

        System.out.println("\nОбъект User1:");
        System.out.println("Имя: " + user1.getName());
        System.out.println("Возраст: " + user1.getAge());
        System.out.println("Пароль (должен быть null, так как transient): " + user1.getPassword());

        System.out.println("\nОбъект User2:");
        System.out.println("Имя: " + user2.getName());
        System.out.println("Возраст: " + user2.getAge());
        System.out.println("Пароль (должен быть null, так как transient): " + user2.getPassword());


    }
}
