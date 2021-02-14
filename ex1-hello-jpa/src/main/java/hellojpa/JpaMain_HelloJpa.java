package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain_HelloJpa {
    /*
    public static void main(String[] args) {
        //Factory는 서버초기화할때 한번만 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //Jpa로 하나의 단위동작을 하고난다음엔 entitymanager close후 새로 entitymanager 만들어서 사용해야함
        //EntityManager는 쓰레드간에 공유하면안됨. 무조건 한 쓰레드내에서 사용하고 닫아줘야됨
        EntityManager em = emf.createEntityManager();

        //Jpa로 쿼리동작하려면 반드시 트랜잭션안에서 실행해야됨.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //Member member = new Member();
        try {
           /*
           Jpa insert
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member); // persist = insert (인자로 주어진 객체 변수값 그대로 저장됨, 해당 변수가 참조형이라도 그 값이 그대로 저장)
            */

            /*
            Jpa Select
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());
             */

            /*
            //JPA를 통해서 객체를 가져오면 가져온 객체에서 객체 변수값 변경하면 따로 update, 삽입 안해줘도
            //객체 변수값 분석해서 커밋전에 알아서 업데이트 쿼리 날려줌 <- awesome
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");
             */

            /*
            JPQL = 객체지향 SQL. 엔티티객체를 대상으로 쿼리를 작성할수있음

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10) // Paging처리하는것. 처음 검색결과부터 10개가져오는것
                    .getResultList();
            for(Member a : result){
                System.out.println("member.name = " + a.getName());
            }
             */

            /*
            // 비영속 = persistence context에 저장되지 않은 객체 그대로의 상태
            Member member1 = new Member();
            member1.setId(101L);
            member1.setName("HelloJpa");

            // 영속 = persistence context에 저장되어 DB에 저장되기 전의 상태(DB저장은 커밋시 저장됨)
            em.persist(member1); // <- member1 객체 영속화

            // JPA는 select할때 1차캐시(persistence context)에서 해당 pk값을 가진 객체가 있는지 조회함
            // 있으면 거기서 가져오고, 없으면 DB에서 조회한 뒤 persistence context에 저장하고 여기서 객체 가져옴
            Member findMember = em.find(Member.class, 101L);
            System.out.println(findMember.getId());
            System.out.println(findMember.getName());
             */

            /*
            // 처음 엔티티객체 가져올때 쿼리 날려서 객체 가져온뒤 persistence context에 저장하고
            // 이후 해당 객체 조회시 persistence context에서 조회하는지, db에서 조회하는지 실험해보는것.
            // => select 쿼리 한번만 날아간다는 의미
            Member findMember1 = em.find(Member.class, 101L);
            Member findMember2 = em.find(Member.class, 101L);
            System.out.println("findMember2 id = " + findMember2.getId());
            System.out.println("findMember2 name = " + findMember2.getName());

            // 영속엔티티는 객체명이 서로 다른 객체에서 객체를 조회해 가져오는경우 자바 컬렉션에서 가져오는 것 처럼
            // 서로 같은 객체의 주소를 참조함, persistence context에서 객체 주소를 가져오기 때문에 가능한 것
            System.out.println(findMember1 == findMember2); // true
             */

            /*
            // Jpa동작특징 : 쓰기지연(Insert)
            // 버퍼링등의 커밋전에 성능최적화를 할 수 있도록 해줌. 여러개의 쿼리를 Batch등으로 한번에 DB로보냄
            // 이는 persistence.xml의 옵션에서 설정할 수 있음
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            // persistence context에 저장되면서 jpa가 객체를 분석한 후에 쿼리 생성해서 sql저장소에 저장
            // 아직 DB로 쿼리 전송은 안함.
            em.persist(member1);
            em.persist(member2);
            System.out.println("===================="); // 이 선 이후에 쿼리 전송결과 나옴
             */

            /*
            // Jpa 동작특징 : 변경감지(Update)
            // 한번 persistence context에 등록된 영속 엔티티의 경우 해당 엔티티의 변수 데이터 수정시
            // 해당 엔티티의 setter메소드로 값만 변경해주고 커밋하면 자동으로 업데이트SQL문 생성해서 수정해줌
            // 아주 Awesome함 => Java Collection에 넣고 빼고 하는동작과 유사하도록 만든것
            Member member = em.find(Member.class, 150L);
            member.setName("changed member name");
            // em.persist(member) or em.update(member) 이런게 있어야하나? -> No. 괜히 썼다가 예상치못한 값이 들어갈수가있음.
             */

            /*
            // Entity삭제 : remove(객체) <- 동작은 위의 동작과 동일함
            Member member = em.find(Member.class, 160L);
            em.remove(member); // id 160번 멤버 삭제
             */

            /* 플러시(flush) : persistence context의 변경된 내용을 데이터베이스에 동기화하는 동
             flow : 변경감지 -> 수정된 엔티티 쓰기 지연SQL저장소에 등록 -> 쓰기지연 SQL 저장소의 쿼리 DB전송
             플러시 동작 조건
             1. em.flush(); -> 직접호출. 테스트할때 가끔 사용
             2. 트랜잭션 커밋
             3. JPQL 사용(JPQL 사용시엔 플러시가 자동으로 호출된 뒤 JPQL의 SQL쿼리 전송됨)
             플러시를 해도 1차캐시(persistence context)에 저장된 객체들은 지워지지않음
             */

            /* 준영속 : 영속성 컨텍스트에서 해당 객체 분리시키는것 -> 더 이상 JPA가 관리 하지 않음
            커밋해도 별다른 동작이 일어나지 않음
            em.detach(객체) <- 특정 객체만 준영속 상태로 변경
            em.clear() <- 영속성 컨텍스트에 있는 모든 객체를 준영속 상태로 변경
            em.close() <- 영속성 컨텍스트를 닫음
            */



/*
            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            //EntityManager는 생성하면 커넥션풀을 하나 사용하므로 반드시 사용하고 닫아줘야됨
            em.close();
        }

        // 서버를 닫을땐 EntityManagerFactory close해줘야함.
        emf.close();


    } */
}
