package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// DB테이블에 맞춰서 객체 생성한경우
@Entity
public class Member {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private long teamId;

    // Member = N : Team = 1 즉, Member Table입장에선 다수 -> 1 이기때문에 ManyToOne을검
    // 즉, Team의 PK값이 Member의 FK값이된경우 FK값을 가진쪽에서 ManyToOne을 거는것
    // 이렇게하면 Team 테이블의 정보를 그대로 객체처럼 쓸수있음
    // 연관관계주인은 해당 FK를 갖고있는 클래스로 지정할것.(즉, 다수(N)의 객체가 주인이된다는것)
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Member() {
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
