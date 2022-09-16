package mattmo.venusrestblog.Controllers;


import mattmo.venusrestblog.data.Category;
import mattmo.venusrestblog.data.Post;
import mattmo.venusrestblog.data.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private List<Post> posts = new ArrayList<>();
    private long nextId = 1;

    @GetMapping("")
//    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Post> fetchPosts() {
        return posts;
    }

    @GetMapping("/{id}")
    public Post fetchPostById(@PathVariable long id) {
        // search through the list of posts
        // and return the post that matches the given id
        Post post = findPostById(id);
        if(post == null) {
            // what to do if we don't find it
            throw new RuntimeException("I don't know what I am doing");
        }

        // we found the post so just return it
        return post;
    }

    private Post findPostById(long id) {
        for (Post post: posts) {
            if(post.getId() == id) {
                return post;
            }
        }
        // didn't find it so do something
        return null;
    }

    @PostMapping("")
    public void createPost(@RequestBody Post newPost) {
//        System.out.println(newPost);
        // assign  nextId to the new post
        newPost.setId(nextId);

        // use a fake author for the post
        User fakeAuthor = new User();
        fakeAuthor.setId(99);
        fakeAuthor.setUserName("fake author");
        fakeAuthor.setEmail("fakeauthor@stuff.com");
        newPost.setAuthor((org.apache.catalina.User) fakeAuthor);

        // make some fake categories and throw them in the new post
        Category cat1 = new Category(1L, "bunnies", null);
        Category cat2 = new Category(2L, "margaritas", null);
        newPost.setCategories(new ArrayList<>());
        newPost.getCategories().add(cat1);
        newPost.getCategories().add(cat2);

        nextId++;

        posts.add(newPost);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        // search through the list of posts
        // and delete the post that matches the given id
        Post post = findPostById(id);
        if(post != null) {
            posts.remove(post);
            return;
        }
        // what to do if we don't find it
        throw new RuntimeException("Post not found");
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