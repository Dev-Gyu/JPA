package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain_EntityMapping {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            /*
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // DB테이블에 맞춰서 객체 생성한경우의 FK값으로 멤버의 팀정보를 가져올경우 FK값으로 상위테이블의 PK값에 해당하는 정보를 가져와야됨
            // => 뭔가 이상함. 객체지향적이지 않음.
            // 특히 조회할때 더욱 심함.
            Member member = new Member();
            member.setUsername("member1");
            member.setTeamId(team.getId());
            em.persist(member);
             */

            /* ** 연관관계 맵핑은 단방향매핑만으로 설계를 끝내고 반대방향의 조회가 필요할경우 양방향 추가할것 ** */

            // 명심: 객체에서 양방향맵핑을 쓰는게 그리 권장되는것은 아님.
            // 양방향 연관관계맵핑시엔 항상 양쪽의 객체에 값을 설정할것
            // 안하면 1차캐시에 저장된 값에대한 이상한값이나 null값이 반환될수있음
            // 양방향 매핑시에 무한루프를 조심해서 설계해야한다.

            // Jpa 연관관계모델링 사용해서 조회하는경우
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // Jpa의 연관관계모델링 사용해서 저장하는경우
            Member member = new Member();
            member.setUsername("member1");

            // 양방향 연관관계맵핑시엔 항상 양쪽의 객체에 값을 설정할것
            // 안하면 1차캐시에 저장된 값에대한 이상한값이나 null값이 반환될수있음
            member.setTeam(team);
            team.addMember(member); // 연관관계 편의메소드

            em.persist(member);

            em.flush();
            em.clear();

            // Jpa 연관관계모델링 사용해서 조회하는경우
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();
            List<Member> members = findMember.getTeam().getMembers();
            for(Member a : members){
                System.out.println("member name = " + a.getUsername());
            }


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        entityManagerFactory.close();
    }
}
