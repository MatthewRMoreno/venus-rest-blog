package mattmo.venusrestblog.misc.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUserDto {
    private long id;
    private String username;
    private String email;
}