package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Aktivnost;
import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.services.AktivnostService;
import rs.raf.domaci.services.KomentarService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/komentar")
public class KomentarResource {
    @Inject
    private KomentarService komentarService;

    @GET
    @Path("/clanak/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all(@PathParam("id") Integer id) {
        return Response.ok(this.komentarService.allKomentari(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Komentar komentar) {
        this.komentarService.addKomentar(komentar);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Komentar find(@PathParam("id") Integer id) {
        return this.komentarService.findKomentar(id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.komentarService.deleteKomentar(id);
    }

}
