package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Clanak;
import rs.raf.domaci.services.AktivnostService;
import rs.raf.domaci.services.ClanakService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clanak")
public class ClanakResources {
    @Inject
    private ClanakService clanakService;

    @GET
    @RolesAllowed({"admin","korisnik"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.clanakService.allClanci()).build();
    }

    @POST
    @RolesAllowed({"admin","korisnik"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Clanak clanak) {
        if(this.clanakService.addClanak(clanak)==null){
            return Response.status(422, "Unprocessable Entity").entity("Clanak sa tim imenom vec postoji").build();
        }else{
            return Response.ok().build();
        }
    }

    @GET
    @RolesAllowed({"admin","korisnik"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clanak find(@PathParam("id") Integer id) {
        return this.clanakService.findClanak(id);
    }

    @DELETE
    @RolesAllowed({"admin","korisnik"})
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.clanakService.deleteClanak(id);
    }

    @PUT
    @RolesAllowed({"admin","korisnik"})
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClanak(@PathParam("id") Integer id, Clanak clanak) {
        return Response.ok(this.clanakService.editClanak(id, clanak)).build();
    }
}
