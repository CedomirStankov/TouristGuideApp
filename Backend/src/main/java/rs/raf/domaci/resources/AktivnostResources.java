package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.services.AktivnostService;
import rs.raf.domaci.services.DestinationService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/aktivnost")
public class AktivnostResources {
    @Inject
    private AktivnostService aktivnostService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.aktivnostService.allAktivnosti()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Aktivnost aktivnost) {
        if(this.aktivnostService.addAktivnost(aktivnost)==null){
            return Response.status(422, "Unprocessable Entity").entity("Aktivnost sa tim imenom vec postoji").build();
        }else{
            return Response.ok().build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aktivnost find(@PathParam("id") Integer id) {
        return this.aktivnostService.findAktivnost(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.aktivnostService.deleteAktivnost(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAktivnost(@PathParam("id") Integer id, Aktivnost aktivnost) {
        this.aktivnostService.editAktivnost(id, aktivnost);
        return Response.ok().build();
    }
}
