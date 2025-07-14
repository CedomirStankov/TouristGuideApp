package rs.raf.domaci.services;

import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;
import rs.raf.domaci.repositories.posts.PostRepository;

import javax.inject.Inject;
import java.util.List;

public class PostService {

    public PostService() {
        System.out.println(this);
    }

    @Inject
    private PostRepository postRepository;

    public Post addPost(Post post) {
        return this.postRepository.addPost(post);
    }

    public Post addComment(Integer id, Komentar comment) {
        return this.postRepository.addComment(id, comment);
    }

    public List<Post> allPosts() {
        return this.postRepository.allPosts();
    }

    public Post findPost(Integer id) {
        return this.postRepository.findPost(id);
    }

}
