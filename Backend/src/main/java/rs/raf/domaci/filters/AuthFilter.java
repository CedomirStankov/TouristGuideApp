package rs.raf.domaci.filters;

import rs.raf.domaci.resources.ClanakResources;
import rs.raf.domaci.resources.DestinationResource;
import rs.raf.domaci.resources.PostResource;
import rs.raf.domaci.resources.UserResource;
import rs.raf.domaci.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    @Inject
    UserService userService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

//        if (true) return;

        if (!this.isAuthRequired(requestContext)) {
            return;
        }

        try {
            String token = requestContext.getHeaderString("Authorization");
            if(token != null && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
            }

            if (!this.userService.isActive(token)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }

            if (!this.userService.isAuthorized(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            if(requestContext.getUriInfo().getPath().contains("destinations")){
                if (!this.userService.isAdmin(token) && !this.userService.isKorisnik(token)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            }

            if(requestContext.getUriInfo().getPath().contains("clanak")){
                if (!this.userService.isAdmin(token) && !this.userService.isKorisnik(token)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            }

            if (requestContext.getUriInfo().getPath().contains("users") && requestContext.getUriInfo().getPath().contains("email")) {
                if (!this.userService.isAdmin(token) && !this.userService.isKorisnik(token)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            }

            if (requestContext.getUriInfo().getPath().contains("users") && !requestContext.getUriInfo().getPath().contains("email")) {
                if (!this.userService.isAdmin(token)) {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            }
        } catch (Exception exception) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private boolean isAuthRequired(ContainerRequestContext req) {
        if (req.getUriInfo().getPath().contains("login")) {
            return false;
        }

        List<Object> matchedResources = req.getUriInfo().getMatchedResources();
        for (Object matchedResource : matchedResources) {
            if (matchedResource instanceof DestinationResource || matchedResource instanceof UserResource || matchedResource instanceof ClanakResources) {
                return true;
            }
        }

        return false;
    }
}
