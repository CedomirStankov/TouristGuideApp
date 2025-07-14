package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Destinacija;
import rs.raf.domaci.services.ClanakAktivnostService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clanakaktivnost")
public class ClanakAktivnostResource {

    @Inject
    ClanakAktivnostService clanakAktivnostService;

    @GET
    @Path("/{clanakId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAktivnostiZaClanak(@PathParam("clanakId") Integer clanakId) {
        return Response.ok(this.clanakAktivnostService.findAktivnostiZaClanak(clanakId)).build();
    }
}
