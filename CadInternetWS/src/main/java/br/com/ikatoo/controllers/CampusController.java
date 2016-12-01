package br.com.ikatoo.controllers;

import br.com.ikatoo.business.CampusBus;
import br.com.ikatoo.models.Campus;
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

@Path("campus")
public class CampusController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Campus> lstCampus() {
        try {
            CampusBus campusBus = new CampusBus();
            return campusBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(CampusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Campus getCampus(@PathParam("id") Integer id){
        try {
            CampusBus campusBus = new CampusBus();
            return campusBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(CampusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(Campus campus) {
        try {
            CampusBus campusBus = new CampusBus();
            campusBus.inserir(campus);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(CampusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Campus campus) {
        try {
//            campus.setCampus(campus);
//
//            CampusBus chamadoBus = new CampusBus();
//            chamadoBus.alterar(campus);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(CampusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") Integer id) {
        try {
            CampusBus campusBus = new CampusBus();
            campusBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(CampusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("{id}/")
    public Response concluir(@PathParam("id") Integer id) {
        try {
//            CampusBus campusBus = new CampusBus();
//
//            Campus c = campusBus.selecionar(id);
//            c.setStatus(Status.FECHADO);
//
//            campusBus.alterar(c);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(CampusController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
}
}
