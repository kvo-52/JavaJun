package lesson04.hw04;

import lesson04.models.Course;
import lesson04.models.CoursesDAO;
import lesson04.models.DataBaseSQLTools;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;


 /*Создайте базу данных (например, SchoolDB).
        В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
        Настройте Hibernate для работы с вашей базой данных.
        Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
        Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
        Убедитесь, что каждая операция выполняется в отдельной транзакции.*/

public class StudentApp {
    private static final String DELIMITER = "\n****************************************************\n";
    public static void main(String[] args) {

         try(SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")

                .addAnnotatedClass(Course.class)
                .buildSessionFactory()){

            // Создание сессии
            Session session = sessionFactory.getCurrentSession();

            // Начало транзакции
            session.beginTransaction();

            //объект для работы с DB
            CoursesDAO dao = new DataBaseSQLTools();
            //Создаем исходный список для записи в базу
            List<Course> courseList = getCourseListStatic();
             //запись всего списка в базу в одной транзакции
             dao.saveCourseListToDB(courseList);

             //запись одной строки в базу
             Course addCourse = new Course(12, "Основы бухучета", 9);
             System.out.println(DELIMITER+"Запись одной строки в базу "+addCourse);
             dao.saveOneCourseToDB(addCourse);


             //чтение всей таблицы из базы
             System.out.println(DELIMITER+"Чтение всей таблицы из базы");
             List<Course> courseListRead = dao.readCoursesFromDB();
             printCourses(courseListRead);

             //чтение одного элемента по id
             int id = 236;
             System.out.println(DELIMITER+"чтение одного элемента по id=" + id);
             Course course = dao.readCourseFromDBByID(id);
             System.out.println(course);


             //обновление записи по id
             id = 895;
             System.out.println(DELIMITER+"обновление записи по id=" + id);
             course = new Course("Привлечение инвестиций", 4);
             System.out.println("до Update: " + dao.readCourseFromDBByID(id));
             dao.updateCoursesByID(course, id);
             System.out.println("после Update: " + dao.readCourseFromDBByID(id));

             //удаление записи по полю duration
             int duration=4;
             System.out.println(DELIMITER+"Удаление запси по полю duration = "+duration);
             System.out.printf("удалено %s записей\n",dao.deleteCourseFromDBByDuration(duration));
             printCourses(dao.readCoursesFromDB("from Course"));


            session.delete(courseList);
            System.out.println("Object delete successfully");

            session.getTransaction().commit();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /** просто вывод в консоль
     * @param courseListRead список курсов
     */
    private static void printCourses(List<Course> courseListRead) {
        if (courseListRead != null && !courseListRead.isEmpty()) {
            courseListRead.forEach(System.out::println);
        } else {
            System.out.println("Запрос вернул NULL");
        }
    }


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
}
