package rs.raf.domaci.repositories.posts;

import rs.raf.domaci.entities.Komentar;
import rs.raf.domaci.entities.Post;

import java.util.List;

public interface PostRepository {
    public Post addPost(Post post);
    public List<Post> allPosts();
    public Post findPost(Integer id);
    public Post addComment(Integer id, Komentar comment);
}
