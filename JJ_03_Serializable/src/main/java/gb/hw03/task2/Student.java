package gb.hw03.task2;



import java.io.*;

public class Student implements Externalizable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private double gpa;
    private boolean isDone;

    //endregion

    public Student() {
    }

    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
        isDone = false;
    }

    //region Externalizable implementation

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
        out.writeDouble(gpa);

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
        gpa = in.readDouble();
    }

    //endregion

    /**
     * Получить статус студента
     *
     * @return статус студента
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Изменить статус студента
     *
     * @param done новый статус студента
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getGpa() {
        return gpa;
    }
}