package com.peaksoft.dao;

import com.peaksoft.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    @SuppressWarnings("uncheked")
    public List<User> listUsers() {
        return sessionFactory.openSession().createQuery("FROM User").getResultList();
    }

    @Override
    public void deleteById(int id) {
        sessionFactory.getCurrentSession().createSQLQuery(String.format("delete from users where id = %d", id)).executeUpdate();
    }

    @Override
    public User getUserById(int id) {
       return sessionFactory.getCurrentSession().get(User.class, id);

   }

    @Override
    public void update(int id, User newUser) {
        User userToBeUpdated = getUserById(id);
        userToBeUpdated.setName(newUser.getName());
        userToBeUpdated.setAge(newUser.getAge());
        userToBeUpdated.setAddress(newUser.getAddress());
        Session session = this.sessionFactory.getCurrentSession();
        session.save(userToBeUpdated);

    }
}
