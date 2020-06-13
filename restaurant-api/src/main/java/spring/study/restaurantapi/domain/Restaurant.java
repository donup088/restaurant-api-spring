package spring.study.restaurantapi.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;

    @Transient//DB에 저장되지 않음
    private List<MenuItem> menuItems=new ArrayList<>();

    public Restaurant(String name, String address) {
        this.name=name;
        this.address=address;
    }

    public Restaurant() {//테스트에서 json으로 데이터를 변환할 때 기본 생성자가 필요함
    }

    public Restaurant(Long id, String name, String address) {
        this.id=id;
        this.name=name;
        this.address=address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getInformation() {
        return name+" in "+address;
    }

    public List<MenuItem> getMenuItems(){
        return menuItems;
    }

    public void addMenuItem(MenuItem menuItem) {
         menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for(MenuItem menuItem:menuItems){
            addMenuItem(menuItem);
        }
    }

    public void setId(Long id) {
        this.id=id;
    }
}
