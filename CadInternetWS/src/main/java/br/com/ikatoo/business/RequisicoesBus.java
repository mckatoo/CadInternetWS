package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.Requisicoes;
//import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RequisicoesBus {
    public Integer inserir(Requisicoes requisicoes) {
//        requisicoes.setNome("5");

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.save(requisicoes);
        t.commit();
        return requisicoes.getId();
    }

    public void alterar(Requisicoes requisicoes) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();
        s.merge(requisicoes);
        t.commit();
    }

    public void excluir(Integer id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Requisicoes c = selecionar(id);

        Transaction t = s.beginTransaction();
        s.delete(c);
        t.commit();
    }

    public Requisicoes selecionar(Integer id) {
        return (Requisicoes) HibernateUtil.getSessionFactory()
                .openSession()
                .get(Requisicoes.class, id);
    }

    public List<Requisicoes> listar() {
        return (List<Requisicoes>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from Requisicoes")
                .list();
    }
}
