package hellojpa;

import javax.persistence.*;

@Entity
public class Locker {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private long id;
    private String name;

    // 일대일 연관관계에서의 양방향맵핑, FK는 멤버클래스가 갖고있음
    // 일대일 관계에선 FK어느테이블이 갖고있던 상관없음
    // FK 없는쪽에 mappedBy 걸어주면됨
    @OneToOne(mappedBy = "locker")
    private Member member;
}
