package mattmo.venusrestblog;

import mattmo.venusrestblog.data.Post;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {

    private List<Post> posts = new ArrayList<>();

    @GetMapping("/")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Post> fetchPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public Post fetchPostById(@PathVariable long id) {
        // search through the list of posts and return the post
        // that matches the given id
        Post posts = findPostById(id);
        //what to do if we don't find it
        throw new RuntimeException("I don't know what I was doing");
    }

    public Post findPostById(long id) {
            for (Post post : posts) {
                if (post.getId() == id) {
                    return post;
                }
            }
            return null;
        }

    @PostMapping("/")
    public void createPost(@RequestBody Post newPost){
        posts.add(newPost);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        // search through the list of posts and return the post
        // that matches the given id
        for (Post post: posts) {
            if(post.getId() == id) {
                // if we find the post then delete it
                posts.remove(post);
                return;
            }
        }
        //what to do if we don't find it
        throw new RuntimeException("I don't know what I was doing");
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
        // find the post to update in the posts list

        Post post = findPostById(id);
        if(post == null) {
            System.out.println("Post not found");
        } else {
            if(updatedPost.getTitle() != null) {
                post.setTitle(updatedPost.getTitle());
            }
            if(updatedPost.getContent() != null) {
                post.setContent(updatedPost.getContent());
            }
            return;
        }
        throw new RuntimeException("Post not found");
    }
}
