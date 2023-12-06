package gb.lesson2.hw02;

public class Dog extends Animal{
    public Dog(String name, int age) {
        super(name, age);
    }

    public void makeGaf(){
        System.out.println("Гав-гав!");
    }
}
