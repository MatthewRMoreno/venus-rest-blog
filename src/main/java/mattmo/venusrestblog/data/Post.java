package mattmo.venusrestblog.data;

import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1024)
    private String content;

    @Transient
    private User author;

    @Transient
    private Collection<Category> categories;
}
