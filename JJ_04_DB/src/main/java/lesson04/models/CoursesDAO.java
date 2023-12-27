package lesson04.models;

import lesson04.models.Course;

import java.util.List;

public interface CoursesDAO {
    /**
     * Удаляет записи из таблицы c выборкой по полю duration
     *
     * @param duration длительность
     * @return число удаленных записей(строк)
     */
    int deleteCourseFromDBByDuration(int duration);

    /**
     * Обновление записи в БД по id
     * если такой записи нет то создается новая
     *
     * @param course сущность с данными для обновления
     * @param id     id
     */
    void updateCoursesByID(Course course, int id);

    /**
     * Чтение одной строки(записи) из БД по id
     *
     * @param id
     * @return
     */
    Course readCourseFromDBByID(int id);

    /**
     * Чтение нескольких строк из БД по запросу
     *
     * @param query запрос HQL
     * @return
     */
    List<Course> readCoursesFromDB(String query);

    /**
     * Получить всю таблицу из БД
     *
     * @return
     */
    List<Course> readCoursesFromDB();

    /**
     * Запись списка курсов в базу
     *
     * @param courseList список курсов List
     */
    void saveCourseListToDB(List<Course> courseList);

    /**
     * Единичная запись в базу
     *
     * @param course сущность Курс
     */
    void saveOneCourseToDB(Course course);
}
