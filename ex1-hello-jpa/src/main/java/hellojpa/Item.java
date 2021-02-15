package hellojpa;

import javax.persistence.*;

@Entity
// 객체에서 상속에대한 JPA의 기본테이블 구성 전략 = 싱글 테이블 전략 ( 부모의 테이블에 모든 자식컬럼을 생성하는것 )
// 이를 조인전략으로 변경하려면 @Inheritance(strategy = InheritanceType.JOINED) 쓰면 됨.
@Inheritance(strategy = InheritanceType.JOINED)
// 객체 상속에 대해 JPA로 테이블 맵핑을 할경우 부모엔티티의 테이블에 해당 row가 어느 자식엔티티의 것인지 구분하도록 Dtype생성해주는 애노테이션
// 이걸 부모클래스에 선언해주면 해당 엔티티 테이블 컬럼에 Dtype컬럼이 생성되고, 레코드삽입시 해당 자식레코드의 이름이 저장됨
// 되도록 선언하도록 한다.
@DiscriminatorColumn
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
