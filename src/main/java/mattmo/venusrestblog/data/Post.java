package mattmo.venusrestblog.data;

import lombok.*;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Post {
    private Long id;
    private String title;
    private String content;
}
