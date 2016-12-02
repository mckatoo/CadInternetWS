package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Campus;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CampusBus {
    public Integer inserir(Campus campus) {
        campus.setCreatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(campus);
        transaction.commit();
        return campus.getId();
    }

    public void alterar(Campus campus) {
        campus.setUpdatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(campus);
        transaction.commit();
    }

    public void excluir(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Campus campus = selecionar(id);
        campus.setDeletedAt(new Date());
        Transaction transaction = session.beginTransaction();
        session.merge(campus);
        transaction.commit();
    }

    public Campus selecionar(Integer id) {
        return (Campus) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Campus.class, id);
    }

    public List<Campus> listar() {
        return (List<Campus>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Campus where deleted_at IS NULL")
                .list();
    }
}
