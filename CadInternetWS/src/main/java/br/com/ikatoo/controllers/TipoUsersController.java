package br.com.ikatoo.controllers;

import br.com.ikatoo.business.TipoUsersBus;
import br.com.ikatoo.models.TipoUsers;
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

@Path("tipousers")
public class TipoUsersController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<TipoUsers> lstTipoUsers() {
        try {
            TipoUsersBus tipousersBus = new TipoUsersBus();
            return tipousersBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(TipoUsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public TipoUsers getTipoUsers(@PathParam("id") Integer id){
        try {
            TipoUsersBus tipousersBus = new TipoUsersBus();
            return tipousersBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(TipoUsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(TipoUsers tipousers) {
        try {
            TipoUsersBus tipousersBus = new TipoUsersBus();
            tipousersBus.inserir(tipousers);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoUsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(TipoUsers tipousers) {
        try {
            TipoUsersBus chamadoBus = new TipoUsersBus();
            chamadoBus.alterar(tipousers);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoUsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") Integer id) {
        try {
            TipoUsersBus tipousersBus = new TipoUsersBus();
            tipousersBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(TipoUsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
