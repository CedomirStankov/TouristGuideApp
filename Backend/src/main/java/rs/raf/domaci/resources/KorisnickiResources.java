package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Clanak;
import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.services.ClanakService;
import rs.raf.domaci.services.DestinationService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/korisnik")
public class KorisnickiResources {

    @Inject
    private DestinationService destinationService;
    @Inject
    private ClanakService clanakService;

    @GET
    @Path("/sviclanci")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allClanci() {
        return Response.ok(this.clanakService.allClanci()).build();
    }

    @GET
    @Path("/najcitaniji")
    @Produces(MediaType.APPLICATION_JSON)
    public Response najcitanijiClanci() {
        return Response.ok(this.clanakService.najcitanijiClanci()).build();
    }

    @GET
    @Path("/clanciaktivnosti/{nazivAktivnosti}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clanciAktivnosti(@PathParam("nazivAktivnosti") String nazivAktivnosti) {
        return Response.ok(this.clanakService.clanciAktivnosti(nazivAktivnosti)).build();
    }

    @GET
    @Path("/clanak/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Clanak findClanak(@PathParam("id") Integer id) {
        return this.clanakService.findClanak(id);
    }

    @PUT
    @Path("/clanak/povecajbrojposeta/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response povecajBrojPoseta(@PathParam("id") Integer id) {
        return Response.ok(this.clanakService.povecajBrojPoseta(id)).build();
    }

    @GET
    @Path("/destinacija/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Destinacija findDestinaciju(@PathParam("id") Integer id) {
        return this.destinationService.findDestination(id);
    }

    @GET
    @Path("/clanakdestinacije/{destId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findClankeZaDestinaciju(@PathParam("destId") Integer id) {
        return Response.ok(this.clanakService.findClankeZaDestinaciju(id)).build();
    }

    @GET
    @Path("/destinacije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.destinationService.allDestinations()).build();
    }

}
