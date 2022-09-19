package mattmo.venusrestblog.Controllers;


import lombok.AllArgsConstructor;
import mattmo.venusrestblog.data.Category;
import mattmo.venusrestblog.data.Post;
import mattmo.venusrestblog.data.User;

import mattmo.venusrestblog.repository.CategoriesRepository;
import mattmo.venusrestblog.repository.PostsRepository;
import mattmo.venusrestblog.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private CategoriesRepository categoriesRepository;

    @GetMapping("")
    public List<Post> fetchPosts() {
        return postsRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Post> fetchPostById(@PathVariable long id) {
        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + id + " not found");
        }
        return optionalPost;
    }

    @PostMapping("")
    public void createPost(@RequestBody Post newPost) {

        // use docrob as author by default
        User author = usersRepository.findById(1L).get();
        newPost.setAuthor(author);
        newPost.setCategories(new ArrayList<>());

        Category cat1 = categoriesRepository.findById(1L).get();
        Category cat2 = categoriesRepository.findById(2L).get();

        newPost.getCategories().add(cat1);
        newPost.getCategories().add(cat2);

        postsRepository.save(newPost);
    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable long id) {
        Optional<Post> optionalPost = postsRepository.findById(id);
        if(optionalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + id + " not found");
        }
        postsRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
        // in case id  is not in req body set it with path var id
        updatedPost.setId(id);
        postsRepository.save(updatedPost);
    }

    @DeleteMapping ("/{id}")
    public void DeletePostById(@PathVariable long id) {
        postsRepository.deleteById(id);
    }
}
