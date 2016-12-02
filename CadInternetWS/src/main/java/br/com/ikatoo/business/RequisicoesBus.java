package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Requisicoes;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RequisicoesBus {
    public Integer inserir(Requisicoes requisicoes) {
        requisicoes.setCreatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(requisicoes);
        transaction.commit();
        return requisicoes.getId();
    }

    public void alterar(Requisicoes requisicoes) {
        requisicoes.setUpdatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(requisicoes);
        transaction.commit();
    }

    public void excluir(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Requisicoes requisicoes = selecionar(id);
        requisicoes.setDeletedAt(new Date());
        Transaction transaction = session.beginTransaction();
        session.merge(requisicoes);
        transaction.commit();
    }

    public Requisicoes selecionar(Integer id) {
        return (Requisicoes) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Requisicoes.class, id);
    }

    public List<Requisicoes> listar() {
        return (List<Requisicoes>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Requisicoes where deleted_at IS NULL")
                .list();
    }
}
