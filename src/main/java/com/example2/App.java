package com.example2;

import com.example2.Models.Category;
import com.example2.Utils.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        int action = 0;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("0. Exit");
            System.out.println("1. Add");
            System.out.println("2. Show all");
            System.out.println("3. Edit");
            System.out.println("4. Delete");
            System.out.print("->_");
            action = in.nextInt();
            in.nextLine();

            switch (action) {
                case 1:
                    AddCategory();
                    break;
                case 2:
                    ShowCategories();
                    break;
                case 3:
                    EditCategory();
                    break;
                case 4:
                    DeleteCategory();
                    break;
            }
        } while (action != 0);

        in.close();
    }

    private static void AddCategory() {
        Scanner in = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            Category category = new Category();
            System.out.print("Enter name: ");
            category.setName(in.nextLine());

            System.out.print("Enter photo: ");
            category.setImage(in.nextLine());

            category.setDateCreated(Calendar.getInstance().getTime());

            context.save(category);
            context.getTransaction().commit();
        }
    }

    private static void ShowCategories() {
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            List<Category> list = context.createQuery("from Category", Category.class).getResultList();

            for (Category category : list) {
                System.out.println("Category: " + category);
            }

            context.getTransaction().commit();
        }
    }

    private static void EditCategory() {
        Scanner in = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            System.out.print("Enter the Category ID to edit: ");
            int categoryId = in.nextInt();
            in.nextLine();

            Category category = context.get(Category.class, categoryId);

            if (category != null) {
                System.out.print("Enter new name: ");
                category.setName(in.nextLine());

                System.out.print("Enter new photo: ");
                category.setImage(in.nextLine());

                context.update(category);
                System.out.println("Category updated successfully.");
            } else {
                System.out.println("Category with ID " + categoryId + " not found.");
            }

            context.getTransaction().commit();
        }
    }

    private static void DeleteCategory() {
        Scanner in = new Scanner(System.in);
        SessionFactory sf = HibernateUtil.getSessionFactory();

        try (Session context = sf.openSession()) {
            context.beginTransaction();

            System.out.print("Enter the Category ID to delete: ");
            int categoryId = in.nextInt();
            in.nextLine();
            Category category = context.get(Category.class, categoryId);

            if (category != null) {
                context.delete(category);
                System.out.println("Category deleted successfully.");
            } else {
                System.out.println("Category with ID " + categoryId + " not found.");
            }

            context.getTransaction().commit();
        }
    }

}
