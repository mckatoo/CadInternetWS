package br.com.ikatoo.business;

import br.com.ikatoo.infra.HibernateUtil;
import br.com.ikatoo.models.UsuarioTipo;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioTipoBus {
    public Integer inserir(UsuarioTipo usuariotipo) {
        usuariotipo.setCreatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(usuariotipo);
        transaction.commit();
        return usuariotipo.getId();
    }

    public void alterar(UsuarioTipo usuariotipo) {
        usuariotipo.setUpdatedAt(new Date());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(usuariotipo);
        transaction.commit();
    }

    public void excluir(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        UsuarioTipo usuariotipo = selecionar(id);
        usuariotipo.setDeletedAt(new Date());
        Transaction transaction = session.beginTransaction();
        session.merge(usuariotipo);
        transaction.commit();
    }

    public UsuarioTipo selecionar(Integer id) {
        return (UsuarioTipo) HibernateUtil.getSessionFactory()
                .openSession()
                .get(UsuarioTipo.class, id);
    }

    public List<UsuarioTipo> listar() {
        return (List<UsuarioTipo>) HibernateUtil.getSessionFactory()
                .openSession()
                .createQuery("from UsuarioTipo where deleted_at IS NULL")
                .list();
    }
}
