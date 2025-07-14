package rs.raf.domaci.resources;

import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;
import rs.raf.domaci.services.PostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/posts")
public class PostResource {

    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(this.postService.allPosts()).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post create(@Valid Post post) {
        return this.postService.addPost(post);
    }

    @PUT
    @Path("/{id}/comment")
    @Produces(MediaType.APPLICATION_JSON)
    public Post comment(@PathParam("id") Integer id, Komentar comment) {
        return this.postService.addComment(id, comment);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post find(@PathParam("id") Integer id) {
        return this.postService.findPost(id);
    }

}
