package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Korisnik;
import rs.raf.domaci.entities.Post;
import rs.raf.domaci.services.DestinationService;
import rs.raf.domaci.services.PostService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/destinations")
public class DestinationResource {
    @Inject
    private DestinationService destinationService;

    @GET
    @RolesAllowed({"admin","korisnik"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.destinationService.allDestinations()).build();
    }

    @POST
    @RolesAllowed({"admin","korisnik"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Destinacija destinacija) {
        if(this.destinationService.addDestination(destinacija)==null){
            return Response.status(422, "Unprocessable Entity").entity("Destinacija sa tim imenom vec postoji").build();
        }else{
            return Response.ok().build();
        }
    }

    @GET
    @RolesAllowed({"admin","korisnik"})
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destinacija find(@PathParam("id") Integer id) {
        return this.destinationService.findDestination(id);
    }

    @DELETE
    @RolesAllowed({"admin","korisnik"})
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        int br = this.destinationService.deleteDestination(id);
        if(br==1){
            return Response.ok().build();
        }else{
            return Response.status(422, "Unprocessable Entity").entity("Postoji bar jedan clanak sa ovom destinacijom, pa nije moguce obrisati destinaciju").build();
        }
    }

    @PUT
    @RolesAllowed({"admin","korisnik"})
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDestination(@PathParam("id") Integer id, Destinacija destinacija) {
        int br = this.destinationService.editDestination(id, destinacija);
        if(br==1){
            return Response.ok().build();
        }else{
            return Response.status(422, "Unprocessable Entity").entity("Destinacija sa tim imenom vec postoji").build();
        }

    }
}
