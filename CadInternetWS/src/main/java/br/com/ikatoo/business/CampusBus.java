package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Campus;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CampusBus {
    public Integer inserir(Campus campus) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(campus);
        t.commit();
        return campus.getId();
    }

    public void alterar(Campus campus) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.merge(campus);
        t.commit();
    }

    public void excluir(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Campus c = selecionar(id);
        Transaction t = s.beginTransaction();
        s.delete(c);
        t.commit();
    }

    public Campus selecionar(Integer id) {
        return (Campus) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Campus.class, id);
    }

    public List<Campus> listar() {
        return (List<Campus>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Campus")
                .list();
    }
}
