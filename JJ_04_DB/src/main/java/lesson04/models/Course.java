package lesson04.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



@Entity
@Table(name = "Courses")
public class Course {

    /**
     * Возвращает статичный список Courses
     *
     * @return List of Courses
     */
    private static List<Course> getCourseListStatic() {
        List<Course> courseList = new ArrayList<>(10);
        courseList.add(new Course("Основы программирования", 1));
        courseList.add(new Course("Web-разработка", 6));
        courseList.add(new Course("Базы данных", 2));
        courseList.add(new Course("Мобильная разработка", 12));
        courseList.add(new Course("Data Science", 5));
        courseList.add(new Course("Архитектура ПО", 6));
        courseList.add(new Course("Сборка проекта", 7));
        courseList.add(new Course("DevOps", 5));
        courseList.add(new Course("Машинное обучение", 2));
        courseList.add(new Course("Кибербезобасность", 1));
        return courseList;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;

    public Course(){

    }


    public Course( String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public Course(int id,String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }



    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        this.title = title;
        return null;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getDuration() {
        return 0;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }



}
