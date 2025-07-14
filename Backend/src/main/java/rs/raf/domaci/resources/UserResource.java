package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Korisnik;
import rs.raf.domaci.requests.LoginRequest;
import rs.raf.domaci.services.UserService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid LoginRequest loginRequest)
    {
        Map<String, String> response = new HashMap<>();

        String jwt = this.userService.login(loginRequest.getEmail(), loginRequest.getLozinka());
        if (jwt == null) {
            response.put("message", "These credentials do not match our records");
            return Response.status(422, "Unprocessable Entity").entity(response).build();
        }

        response.put("jwt", jwt);

        return Response.ok(response).build();
    }

    @GET
    @RolesAllowed("admin")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik find(@PathParam("id") Integer id) {
        return this.userService.findKorisnik(id);
    }

    @GET
    @Path("/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Korisnik find(@PathParam("email") String email) {
        return this.userService.findKorisnikWithEmail(email);
    }

    @GET
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.userService.allKorisnici()).build();
    }

    @POST
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Korisnik korisnik) {
        if(this.userService.addKorisnik(korisnik)==null){
            return Response.status(422, "Unprocessable Entity").entity("User sa tom mejl adresom vec postoji").build();
        }else{
            return Response.ok().build();
        }
    }

    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        this.userService.deleteKorisnik(id);
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/status/{id}")
    public void promeniStatus(@PathParam("id") Integer id) {
        this.userService.promeniStatus(id);
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Integer id, Korisnik korisnik) {
        this.userService.editProfile(id, korisnik);
        return Response.ok().build();
    }
}
