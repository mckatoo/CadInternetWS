package br.com.ikatoo.controllers;

import br.com.ikatoo.business.StatusBus;
import br.com.ikatoo.models.Status;
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

@Path("status/")
public class StatusController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/")
    public List<Status> lstStatus() {
        try {
            StatusBus statusBus = new StatusBus();
            return statusBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Status getStatus(@PathParam("id") Integer id){
        try {
            StatusBus statusBus = new StatusBus();
            return statusBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
    public Response create(Status status) {
        try {
            StatusBus statusBus = new StatusBus();
            statusBus.inserir(status);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
    public Response update(Status status) {
        try {
            StatusBus chamadoBus = new StatusBus();
            chamadoBus.alterar(status);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") Integer id) {
        try {
            StatusBus statusBus = new StatusBus();
            statusBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(StatusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
