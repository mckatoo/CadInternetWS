package br.com.ikatoo.ws;

import br.com.ikatoo.controllers.CampusJpaController;
import br.com.ikatoo.models.Campus;
import br.com.ikatoo.util.ServletUtil;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("campus")
public class CampusWS {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PU");
        CampusJpaController campusJpa = new CampusJpaController(emf);
        List<Campus> lstCampus = campusJpa.findCampusEntities();
        
        for(Campus campus : lstCampus){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(lstCampus);
            ServletUtil.writeJSON(resp, json);
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getCampus(@PathParam("id") Integer id){
        return "campus " + id;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(Campus campus) {
        System.out.println(campus.toString());
        return Response.status(Response.Status.OK).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Campus campus) {
        System.out.println(campus.toString());
        return Response.status(Response.Status.OK).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        System.out.println("Deletando ID: " + id);
        return Response.status(Response.Status.OK).build();
    }
}
