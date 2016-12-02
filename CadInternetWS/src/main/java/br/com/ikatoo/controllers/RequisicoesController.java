package br.com.ikatoo.controllers;

import br.com.ikatoo.business.RequisicoesBus;
import br.com.ikatoo.models.Requisicoes;
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

@Path("requisicoes")
public class RequisicoesController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Requisicoes> lstRequisicoes() {
        try {
            RequisicoesBus requisicoesBus = new RequisicoesBus();
            return requisicoesBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(RequisicoesController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Requisicoes getRequisicoes(@PathParam("id") Integer id){
        try {
            RequisicoesBus requisicoesBus = new RequisicoesBus();
            return requisicoesBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(RequisicoesController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(Requisicoes requisicoes) {
        try {
            RequisicoesBus requisicoesBus = new RequisicoesBus();
            requisicoesBus.inserir(requisicoes);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(RequisicoesController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Requisicoes requisicoes) {
        try {
            RequisicoesBus chamadoBus = new RequisicoesBus();
            chamadoBus.alterar(requisicoes);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(RequisicoesController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") Integer id) {
        try {
            RequisicoesBus requisicoesBus = new RequisicoesBus();
            requisicoesBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(RequisicoesController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
