package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Users;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsersBus {
    public Integer inserir(Users users) {
        users.setCreatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(users);
        transaction.commit();
        return users.getId();
    }

    public void alterar(Users users) {
        users.setUpdatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(users);
        transaction.commit();
    }
    
    public void excluir(Integer id) {
        Users user = selecionar(id);
        user.setDeletedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
    }

    public Users selecionar(Integer id) {
        return (Users) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Users.class, id);
    }

    public List<Users> listar() {
        return (List<Users>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Users where deleted_at IS NULL")
                .list();
    }
}
