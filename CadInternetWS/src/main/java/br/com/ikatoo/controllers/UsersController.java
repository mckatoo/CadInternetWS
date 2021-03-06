package br.com.ikatoo.controllers;

import br.com.ikatoo.business.UsersBus;
import br.com.ikatoo.models.Users;
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

@Path("users/")
public class UsersController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/")
    public List<Users> lstUsers() {
        try {
            UsersBus usersBus = new UsersBus();
            return usersBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Users getUsers(@PathParam("id") Integer id){
        try {
            UsersBus usersBus = new UsersBus();
            return usersBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
    public Response create(Users users) {
        try {
            UsersBus usersBus = new UsersBus();
            usersBus.inserir(users);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
//    @Path("/")
    public Response update(Users users) {
        try {
            UsersBus chamadoBus = new UsersBus();
            chamadoBus.alterar(users);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") Integer id) {
        try {
            UsersBus usersBus = new UsersBus();
            usersBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
