package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.TipoUsers;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TipoUsersBus {
    public Integer inserir(TipoUsers tipousers) {
        tipousers.setCreatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(tipousers);
        transaction.commit();
        return tipousers.getId();
    }

    public void alterar(TipoUsers tipousers) {
        tipousers.setUpdatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(tipousers);
        transaction.commit();
    }

    public void excluir(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TipoUsers tipousers = selecionar(id);
        tipousers.setDeletedAt(new Date());
        Transaction transaction = session.beginTransaction();
        session.merge(tipousers);
        transaction.commit();
    }

    public TipoUsers selecionar(Integer id) {
        return (TipoUsers) HibernateUtil.getSessionFactory()
                .openSession()
                .get(TipoUsers.class, id);
    }

    public List<TipoUsers> listar() {
        return (List<TipoUsers>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from TipoUsers where deleted_at IS NULL")
                .list();
    }
}
