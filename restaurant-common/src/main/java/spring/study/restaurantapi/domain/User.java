package spring.study.restaurantapi.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotNull
    private Long level;

    @NotEmpty
    private String name;

    public boolean isAdmin(){
        return level>=100;
    }

    public boolean isActive() {
        return level>0;
    }

    public void deactivate() {
        level=0L;
    }
}
