package mattmo.venusrestblog.Controllers;


import lombok.AllArgsConstructor;
import mattmo.venusrestblog.data.Category;
import mattmo.venusrestblog.data.Post;
import mattmo.venusrestblog.data.User;

import mattmo.venusrestblog.misc.FieldHelper;
import mattmo.venusrestblog.repository.CategoriesRepository;
import mattmo.venusrestblog.repository.PostsRepository;
import mattmo.venusrestblog.repository.UsersRepository;
import mattmo.venusrestblog.security.OAuthConfiguration;
import mattmo.venusrestblog.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/posts", produces = "application/json")
public class PostsController {
    private EmailService emailService;
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
    @PreAuthorize("hasAuthority('USER') && hasAuthority('ADMIN')")
    public void createPost(@RequestBody Post newPost, OAuth2Authentication auth) {
//        if(auth == null) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
//        }

        String userName = auth.getName();
        User author = usersRepository.findByUserName(userName);
        newPost.setAuthor(author);

        newPost.setCategories(new ArrayList<>());

        Category cat = categoriesRepository.findById(1L).get();
        Category dog = categoriesRepository.findById(2L).get();

        newPost.getCategories().add(cat);
        newPost.getCategories().add(dog);

        postsRepository.save(newPost);

        emailService.prepareAndSend(newPost, "New Post","Hey, you just made a new post");
    }

//    @DeleteMapping("/{id}")
//    public void deletePostById(@PathVariable long id) {
//        Optional<Post> optionalPost = postsRepository.findById(id);
//        if(optionalPost.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post id " + id + " not found");
//        }
//        postsRepository.deleteById(id);
//    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') && hasAuthority('ADMIN')")
    public void updatePost(@RequestBody Post updatedPost, @PathVariable long id) {
        Optional<Post> originalPost = postsRepository.findById(id);
        if(originalPost.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post " + id + " not found");
        }
        // in case id is not in the request body (i.e., updatedPost), set it
        // with the path variable id
        updatedPost.setId(id);

        // copy any new field values FROM updatedPost TO originalPost
        BeanUtils.copyProperties(updatedPost, originalPost.get(), FieldHelper.getNullPropertyNames(updatedPost));

        postsRepository.save(originalPost.get());
    }
    @PreAuthorize("hasAuthority('USER') && hasAuthority('ADMIN')")
    @DeleteMapping ("/{id}")
    public void DeletePostById(@PathVariable long id) {
        postsRepository.deleteById(id);
    }
}
