package lesson04.models;

import lesson04.hw04.core.HibernateTools;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

import static lesson04.hw04.core.HibernateTools.getSessionFactory;

public class DataBaseSQLTools implements CoursesDAO {


    @Override
    public int deleteCourseFromDBByDuration(int duration) {
        Session session;
        int rowDeleted = 0;
        try {
            session = HibernateTools.getSessionFactory().openSession();
            session.beginTransaction();

            String q = "DELETE Course WHERE duration = :param";
            rowDeleted = session.createQuery(q)
                    .setParameter("param", duration)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }


    @Override
    public void updateCoursesByID(Course course, int id) {
        Session session;
        try {
            session = HibernateTools.getSessionFactory().openSession();
            session.beginTransaction();
            Course existCourse = session.get(Course.class, id);
            System.out.println(existCourse);
            if (existCourse == null) {
                existCourse = course;
            } else {
                existCourse.setTitle(course.getTitle());
                existCourse.setDuration(course.getDuration());
            }
            session.saveOrUpdate(existCourse);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Course readCourseFromDBByID(int id) {
        Session session;
        List<Course> courseList = null;
        try {
            session = HibernateTools.getSessionFactory().openSession();
            session.beginTransaction();
            courseList = session.createQuery("from Course where id=:id", Course.class)
                    .setParameter("id", id)
                    .getResultList();
            session.getTransaction().commit();
            session.close();
            if (courseList.isEmpty()) return null;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return courseList.get(0);
    }


    @Override
    public List<Course> readCoursesFromDB(String query) {
        Session session;
        List<Course> courseList = null;
        try {
            session = HibernateTools.getSessionFactory().openSession();
            session.beginTransaction();
            courseList = session.createQuery(query, Course.class).getResultList();
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return courseList;
    }


    @Override
    public List<Course> readCoursesFromDB() {
        return readCoursesFromDB("from Course");
    }

    @Override
    public void saveCourseListToDB(List<Course> courseList) {
        Session session;
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            courseList.forEach(session::save);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveOneCourseToDB(Course course) {
        try {
            System.out.println(course);
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
