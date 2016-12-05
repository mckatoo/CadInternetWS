package br.com.ikatoo.controllers;

import br.com.ikatoo.business.UsuarioTipoBus;
import br.com.ikatoo.models.UsuarioTipo;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

@Path("usuariotipo/")
public class UsuarioTipoController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/")
    public List<UsuarioTipo> lstUsuarioTipo() {
        try {
            UsuarioTipoBus usuariotipoBus = new UsuarioTipoBus();
            return usuariotipoBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTipoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public UsuarioTipo getUsuarioTipo(@PathParam("id") Integer id){
        try {
            UsuarioTipoBus usuariotipoBus = new UsuarioTipoBus();
            return usuariotipoBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTipoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
    public Response create(UsuarioTipo usuariotipo) {
        try {
            UsuarioTipoBus usuariotipoBus = new UsuarioTipoBus();
            usuariotipoBus.inserir(usuariotipo);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTipoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
    public Response update(UsuarioTipo usuariotipo) {
        try {
            UsuarioTipoBus chamadoBus = new UsuarioTipoBus();
            chamadoBus.alterar(usuariotipo);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTipoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") Integer id) {
        try {
            UsuarioTipoBus usuariotipoBus = new UsuarioTipoBus();
            usuariotipoBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioTipoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
