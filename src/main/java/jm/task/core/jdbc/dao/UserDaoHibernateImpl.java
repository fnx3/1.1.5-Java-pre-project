package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();
    
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.getCurrentSession() ) {

            session.beginTransaction();

            String hql = "create table if not exists Users ("
                    + "id bigint auto_increment primary key,"
                    + "name varchar(50) not null,"
                    + "lastname varchar(50) not null,"
                    + "age int" +
                    ")";

            Query<?> query = session.createSQLQuery(hql);

            query.executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.getCurrentSession() ) {

            session.beginTransaction();

            session.createSQLQuery("DROP TABLE if exists Users").executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession() ) {

            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
            try (Session session = sessionFactory.getCurrentSession() ) {

                session.beginTransaction();

                User user = session.get(User.class, id);

                if (user != null) {
                    session.delete(user);
                }

                session.getTransaction().commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                session.getTransaction().rollback();
            }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.getCurrentSession() ) {

            session.beginTransaction();
            String hql = "FROM User";

            Query<User> query = session.createQuery(hql, User.class);
            List<User> usersList = query.getResultList();

            session.getTransaction().commit();

            return usersList;
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return null;
        }

    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession() ) {

            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
