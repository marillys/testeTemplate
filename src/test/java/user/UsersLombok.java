package user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class UsersLombok {
    private String name;
    private String gender;
    private String email;
    private String status;


}
