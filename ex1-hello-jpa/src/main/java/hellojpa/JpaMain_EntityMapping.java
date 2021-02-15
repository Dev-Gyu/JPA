package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

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
            /*
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
            */

            /*
            Member member = new Member();
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId()); // DB에서 실제 해당 객체 조회해오는것

            // 프록시 = 실제 클래스를 상속받아서 만들어짐. 실제 클래스와 겉모양이 같음
            // 프록시객체의 데이터타입체크시엔 == 이 아닌 instance of를 사용해야함
            Member findMember2 = em.getReference(Member.class, member.getId());
            System.out.println(findMember.getClass()); // findMember는 Member타입이 아닌 하이버네이트 프록시객체타입임 <- 가짜엔티티
            System.out.println(findMember.getUsername());
            System.out.println(findMember.getId());
            System.out.println(findmember2 instance of Member) // true
             */

            /*
            // 영속성 컨텍스트에 해당 객체가 존재한다면 프록시객체를 참조하더라도 영속성 컨텍스트의 실제 객체가 반환됨
            // JPA의 특성 ( 마치 컬렉션객체에서 꺼낸것처럼 동작 )
            Member member1 = em.find(Member.class, member.getId());
            Member reference = em.getReference(Member.class, member.getId());

            // 반대로 프록시(참조)객체를 먼저 호출하게되면 find하게 되도 프록시객체를 반환함
            Member reference2 = em.getReference(Member.class, member.getId());
            Member member2 = em.find(Member.class, member.getId());
            System.out.println("reference2 == member2 : " + (reference2 == member2)); // true

             */

            /*
            // 프록시객체를 가져온 후 준영속상태(영속성컨텍스트에서 더이상 관리하지않는상태)로 전환될경우
            // 해당 프록시객체를 초기화하면 에러발생함
            Member refMember = em.getReference(Member.class, member.getId());
            System.out.println(refMember.getClass());
            em.detach(refMember); // 프록시 객체 참조하고있는 refMember 준영속상태로 전환
            // em.close(); // 예외발생
            // em.clear(); // 예외발생
            System.out.println(refMember.getUsername()); // 준영속 상태의 프록시객체 초기화시도 -> 예외 발생

            Member refMember2 = em.getReference(Member.class, member.getId());
            Hibernate.initialize(refMember); // 프록시 객체 강제초기화
             */

            Member member = new Member();
            member.setUsername("user1");
            member.setHomeAddress(new Address("city1", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "old1Street", "50000"));
            member.getAddressHistory().add(new Address("old2", "old2Street", "30000"));

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());

            List<Address> findMemberAddressHistory = findMember.getAddressHistory();

            for (Address a : findMemberAddressHistory) {
                System.out.println(a.getCity());
                System.out.println(a.getStreet());
                System.out.println(a.getZipcode());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println(favoriteFood);
            }

            // 값타입 컬렉션 수정
            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("changedCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨"); // favoriteFood가 현재 Set<String>이므로 불변이기때문에 해당 값 가진 인덱스 삭제하고 새로추가하는방식으로 수정해야됨
            findMember.getFavoriteFoods().add("한식");

            // 주소변경
            // Cascading걸어둔것처럼 따로 persist안해줘도 알아서 저장됨
            // 컬렉션에서 remove할때 equals를 쓰기때문에 Address객체에 equals 및 HashCode 메소드 오버라이딩 해줘야됨
            // 1. JPA가 이 방법으로 쿼리 전송하면 old1만 지우는게 아니라 해당 멤버의 Address 엔티티값을 모두 다 지움
            // 2. remove한 엔티티를 제외한 나머지 원래 있던 엔티티를 전부 insert함
            // 3. add한 엔티티 insert함 => 쿼리 전송 엄청날수있음
            findMember.getAddressHistory().remove(new Address("old1", "old1Street", "50000"));
            findMember.getAddressHistory().add(new Address("newCity1", "street", "50000"));





            tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        entityManagerFactory.close();
    }
}
