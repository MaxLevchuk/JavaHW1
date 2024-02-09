package com.example2.Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.example2.Models.Category;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration conf = new Configuration().configure();
            conf.addAnnotatedClass(Category.class);
            sessionFactory = conf.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
