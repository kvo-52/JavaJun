package lesson04.hw04.core;

import lesson04.models.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTools {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(Course.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and the SessionFactory
        sessionFactory.close();
        sessionFactory=null;
    }

    public static Session openSession() {
        return getSessionFactory().openSession();
    }
}
