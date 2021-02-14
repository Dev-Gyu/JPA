package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// DB테이블에 맞춰서 객체 생성한경우
@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private long id;
    private String name;

    // 양방향 연관관계매핑. Member->Team은 가능했으나 Team->Member로는 참조할수없던걸 해결
    // Team입장에서 Member는 다수, 따라서 1:N 맵핑하도록 설정
    // mappedBy속성은 양방향연관관계에서의 FK주인이 아닌경우에 지정해서 FK의 수정권한이 주인에만 있도록 해줌
    // 연관관계주인은 해당 FK를 갖고있는 클래스로 지정할것.(즉, 다수(N)의 객체가 주인이된다는것)
    // mappedBy적힌곳에선 읽기만가능함. 값을 줘봐야 아무일이 안일어남.
    @OneToMany(mappedBy = "team") // 다수(N)인 클래스에 선언되어있는 변수명을 mappedBy 속성값으로 넣어줌
    List<Member> members = new ArrayList<>(); // add할때 NullPointException뜨지 않기 위해 초기화

    public void addMember(Member member){
        member.setTeam(this);
        members.add(member);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Team() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
