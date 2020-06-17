package spring.study.restaurantapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Transient//DB에 저장되지 않음
    @JsonInclude(JsonInclude.Include.NON_NULL)//넘겨주는 json데이터가 없는 경우 표시되지 않음
    private List<MenuItem> menuItems;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ArrayList<Review> reviews;

    public String getInformation() {
        return name+" in "+address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems=new ArrayList<>(menuItems);
    }

    public void updateInformation(String name, String address) {
        this.name=name;
        this.address=address;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews=new ArrayList<>(reviews);
    }
}
