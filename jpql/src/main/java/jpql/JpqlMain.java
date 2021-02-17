package jpql;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            /*
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            //jpql 기본문법 사용법
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.id = 10", Member.class);
            파라미터 바인딩, 이름기준으로 바인딩 (위치기준 바인딩은 하지말것)
            List<Member> resultList1 = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();
             */

            /* 조회 결과가 1개 또는 여러개인경우 대응. 조회값 없으면 빈컬렉션 객체 반환. -> 예외발생에 유연함
            List<Member> resultList = query.getResultList();
            for (Member member1 : resultList) {
                System.out.println(member1.getUsername());
            }
             */
            //Member result = query.getSingleResult(); // 조회결과 1개일경우에만 사용해야함. 결과값없거나 2개이상이면 예외발생함

            /*

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);

            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            // 내부조인 = inner join
            String innerJoinQuery = "select m from Member m inner join m.team t"; // + where t.name = :teamName등으로 바인딩해도됨
            // (좌측)외부조인 = left [outer] join
            String outerJoinQuery = "select m from Member m left join m.team t";
            // 세타조인
            String thetaJoinQuery = "select count(m) from Member m, Team t where m.username = t.name ";

            List<Member> resultList = em.createQuery(innerJoinQuery, Member.class).getResultList();

            List<Member> resultList1 = em.createQuery(outerJoinQuery, Member.class).getResultList();

            List<Integer> resultList2 = em.createQuery(thetaJoinQuery, Integer.class).getResultList();

            // 조인대상 필터링 (회원과 팀을 조인하면서, 팀이름이 "A"인 팀만 조인)
            String filterJoinQuery = "select m from Member m left join Team t on t.name = 'teamA'";
            List<Member> resultList3 = em.createQuery(filterJoinQuery, Member.class).getResultList();

            // 연관관계 없는 엔티티 외부 조인 (회원과 팀을 조인하면서 이름이 서로 같은 경우만 조회)
            String nonRelationJoinQuery = "select m from Member m left join Team t on m.username = t.name";
            List<Member> resultList4 = em.createQuery(nonRelationJoinQuery, Member.class).getResultList();

             */
            /* Case식
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            String caseQuery = "select " +
                                    "case when m.age <= 10 then '학생요금' " +
                                        "when m.age >= 60 then '경로요금' " +
                                        "else '일반요금' " +
                                "end from Member m";
            List<String> resultList = em.createQuery(caseQuery, String.class).getResultList();
            System.out.println(resultList.get(0));

            // coalesce 식(둘을 비교해서 값이 있으면 첫번째 인자 가져오고, 값이 없으면 두번째 인자 가져옴)
            String coalesceQuery = "select coalesce(m.username, '이름 없는 회원') from Member m ";
            List<String> resultList = em.createQuery(coalesceQuery, String.class).getResultList();
            System.out.println(resultList);
            */

            // 페치조인
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setAge(10);
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // 엔티티 페치 조인
            // String fetchJoinQuery = "select m from Member m";
            String fetchJoinQuery = "select m from Member m join fetch m.team"; // fetch join으로 멤버와 관련있는 Team정보까지 같이 가져옴
            List<Member> resultList = em.createQuery(fetchJoinQuery, Member.class).getResultList();

            // fetch join 안쓰면 team객체가 프록시인상태라 N+1문제 발생
            // fetch join 쓰면 team객체는 영속성 컨텍스트에 저장되어있기때문에 실제 엔티티가 들어있음
            for (Member member : resultList) {
                System.out.println("member = " + member.getUsername() + " , " + member.getTeam().getName());
            }

            // 컬렉션 페치 조인
            // 일대다, 다대다 관계에서의 컬렉션 페치 조인시 데이터가 중복되는 소위 데이터 뻥튀기가 될 여지가 있음. * 다대일은 딱히 뻥튀기될 일이 없음
            // String fetchJoinQuery2 = "select t from Team t join fetch t.members";

            // jpa distinct는 sql문에 distinct추가 및 Application차원의 중복엔티티도 제거해줌
            String fetchJoinQuery2 = "select distinct t from Team t join fetch t.members";
            List<Team> resultList1 = em.createQuery(fetchJoinQuery2, Team.class).getResultList();
            for (Team team : resultList1) {
                System.out.println("team = " + team.getName() + "| size = " + team.getMembers().size());
            }

            // named쿼리 = 특정 엔티티에서 애노테이션,XML로 미리 쿼리 선언해두고 그 쿼리의 이름만으로 사용하는 쿼리.
            // 재사용가능, 컴파일시점에서 문법에러 잡아줌(Awesome)
            // 스프링 DataJPA에선 인터페이스의 추상메소드에 선언해주는것만으로 NamedQuery 설정가능. basicJpa는 클래스에 직접선언해야하는데 좀 비추
            List<Member> resultList2 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();
            for (Member member : resultList2) {
                System.out.println("member = " + member.getUsername());
            }

            // 벌크 연산
            // 벌크연산은 영속성 컨텍스트를 무시하고 DB에 바로 쿼리가 날아가기때문에 주의가 필요하다.
            // DB에 다이렉트로 쿼리 날아가는경우엔 영속성컨텍스트의 객체는 변화가 없기때문에 변경된사항에 대해 자동으로 객체를 리셋해주지 않음
            // 1. 벌크연산 실행 후 영속성 컨텍스트 초기화(clear()실행, 기존 영속성 컨텍스트에 존재하던 영속성객체들 새로 가져오기위함, flush는 DB로 쿼리날아가기전에 무조건 실행되므로 안해줘도됨)
            // 2. 벌크연산 먼저 실행
            int i = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            em.clear();
            Member member = em.find(Member.class, member1.getId());
            System.out.println("update member age result = " + member.getAge());


            tx.commit();
        }catch(Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally{
            em.close();
        }
        emf.close();
    }
}
