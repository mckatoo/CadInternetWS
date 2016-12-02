package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Status;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StatusBus {
    public Integer inserir(Status status) {
        status.setCreatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(status);
        transaction.commit();
        return status.getId();
    }

    public void alterar(Status status) {
        status.setUpdatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(status);
        transaction.commit();
    }

    public void excluir(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Status status = selecionar(id);
        status.setDeletedAt(new Date());
        Transaction transaction = session.beginTransaction();
        session.merge(status);
        transaction.commit();
    }

    public Status selecionar(Integer id) {
        return (Status) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Status.class, id);
    }

    public List<Status> listar() {
        return (List<Status>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Status where deleted_at IS NULL")
                .list();
    }
}
