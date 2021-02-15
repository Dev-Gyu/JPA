package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

// DB테이블에 맞춰서 객체 생성한경우
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @Embedded
    private Address homeAddress;

    // 값 타입 컬렉션의 테이블맵핑정보 설정
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
                        @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns =
                        @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();



//    @Column(name = "TEAM_ID")
//    private long teamId;

    // Member = N : Team = 1 즉, Member Table입장에선 다수 -> 1 이기때문에 ManyToOne을검
    // 즉, Team의 PK값이 Member의 FK값이된경우 FK값을 가진쪽에서 ManyToOne을 거는것
    // 이렇게하면 Team 테이블의 정보를 그대로 객체처럼 쓸수있음
    // 연관관계주인은 해당 FK를 갖고있는 클래스로 지정할것.(즉, 다수(N)의 객체가 주인이된다는것)
    @ManyToOne(fetch = FetchType.LAZY) // 실무에선 반드시 지연로딩만 쓸것. 즉시로딩쓰면 쿼리 N+1문제 발생.
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    // 일대일 양방향연관관계에서의 연관관계주인. 일대일 관계에선 FK어느테이블이 갖고있던 상관없음
    // FK 없는쪽에 mappedBy 걸어주면됨
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    public Member() {
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
