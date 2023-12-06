package gb.lesson2.hw02;

abstract class Animal {
    public String name;
    public int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+" {" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
