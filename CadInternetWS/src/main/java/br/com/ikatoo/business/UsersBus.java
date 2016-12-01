package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Users;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsersBus {
    public Integer inserir(Users users) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(users);
        t.commit();
        return users.getId();
    }

    public void alterar(Users users) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.merge(users);
        t.commit();
    }

    public void excluir(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Users c = selecionar(id);
        Transaction t = s.beginTransaction();
        s.delete(c);
        t.commit();
    }

    public Users selecionar(Integer id) {
        return (Users) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Users.class, id);
    }

    public List<Users> listar() {
        return (List<Users>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Users")
                .list();
    }
}
