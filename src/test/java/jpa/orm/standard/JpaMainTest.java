package jpa.orm.standard;

import jpa.orm.standard.domain.*;
import jpa.orm.standard.embedded.Address;
import jpa.orm.standard.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class JpaMainTest {

    @Autowired
    EntityManager em;

    @Autowired
    TeamRepository teamRepository;

    @BeforeEach
    void init() {
        Address address = new Address("광주", "광역시");

        Team team1 = new Team();
        Team team2 = new Team();
        team1.setName("team1");
        team2.setName("team2");

        em.persist(team1);
        em.persist(team2);

        Member member1 = Member.builder()
                .name("member1")
                .team(team1)
                .homeAddress(address)
                .build();

        Member member2 = Member.builder()
                .name("member2")
                .team(team1)
                .homeAddress(address)
                .build();

        Member member3 = Member.builder()
                .name("member3")
                .team(team2)
                .build();

        Member member4 = Member.builder()
                .name("member4")
                .team(team2)
                .build();

        em.persist(member1);
        em.persist(member2);

        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
    }

    @Rollback(value = false)
    @Transactional
    @Test
    void embeddableTest() {

    }
    @DisplayName("parent 생성 시 childrent도 생성되는 지")
    @Transactional
    @Rollback(value = false)
    @Test
    void cascade_test() {
        Parent parent = new Parent();
        parent.setName("엄마");
        parent.addChild(new Child());
        parent.addChild(new Child());

        em.persist(parent);

        em.flush();
        em.clear();

        Parent parent1 = em.find(Parent.class, parent.getId());

        System.out.println("parent1 = " + parent1);
        System.out.println("parent1.children = " + parent1.getChildren());
    }

    @Transactional
    @Rollback(value = false)
    @Test
    void batch_fetch_test() {
        String query = "SELECT t FROM Team t join fetch t.members";

        List<Team> teams = em.createQuery(query, Team.class).getResultList();

        for (Team team : teams) {
            System.out.println("team = " + team.getName());

            for (Member member: team.getMembers()) {
                System.out.println(member.getName() + ", " + member.getTeam().getName());
            }
        }
    }

    @Transactional
    @Rollback(value = false)
    @Test
    void memberFromTeamFetchJoin() {
        String query = "SELECT m FROM Member m join fetch m.team";

        List<Member> members = em.createQuery(query, Member.class).getResultList();

        for (Member member :
                members) {
            System.out.println("member.getName() = " + member.getName());
            System.out.println( member.getTeam().getName());
        }
    }



    @Transactional
    @Rollback(value = false)
    @Test
    void teamFromMemberFetchJoin() {

        String query = "SELECT t FROM Team t join fetch t.members";

        List<Team> teams = em.createQuery(query, Team.class).getResultList();

        for (Team team : teams) {
            System.out.println("team = " + team.getName());

            for (Member member: team.getMembers()) {
                System.out.println(member.getName() + ", " + member.getTeam().getName());
            }
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void JpaMainTest() {
        Member member = new Member();

        member.setName("hyunin");

        em.persist(member);

        Team team = new Team();

        team.setName("3학년 3반");
        team.getMembers().add(member);
        em.persist(team);

        Member member1 = em.find(Member.class, member.getId());

        Team team1 = em.find(Team.class, team.getId());

        System.out.println("member1 = " + member1);
        System.out.println("team1 = " + team1);
    }

    @DisplayName("페치 조인 시 별칭을 줄 경우 on 조건문이 나가는 테스트")
    @Test
    @Transactional
    @Rollback(value = false)
    void aliasOneTest() {
        Member member1 = new Member();
        Member member2 = new Member();
        Member member3 = new Member();
        Member member4 = new Member();

        member1.setName("hyunin");
        member2.setName("kyungjun");
        member3.setName("taehyun");
        member4.setName("insun");


        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        Team team1 = new Team();
        Team team2 = new Team();

        team1.setName("team1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);

        team2.setName("team2");
        team2.getMembers().add(member3);
        team2.getMembers().add(member4);

        em.persist(team1);
        em.persist(team2);

        member1.setTeam(team1);
        member2.setTeam(team1);

        member3.setTeam(team2);
        member4.setTeam(team2);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

//        String query = "SELECT t From Team t join fetch t.members";
        String query1 = "SELECT m From Member m";
//        String query1 = "SELECT m FROM Member m where m.name like 'hyunin'";
//
//        List<Member> members = em.createQuery(query1, Member.class).getResultList();
//
//        for (Member member: members) {
//            System.out.println("member = " + member);
//        }

        List<Member> teams = em.createQuery(query1, Member.class).getResultList();

        System.out.println("teams = " + teams.size());

        for (Member team : teams) {
            System.out.println("team = " + team);
            System.out.println("================================ " + team.getTeam());
        }
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void tableTest() {

    }

    @Test
    @Transactional
    @Rollback(value = false)
    void itemTest() {
        Movie movie = new Movie();
        movie.setDirector("aaa");
        movie.setActor("bbb");
        movie.setName("바람과함께사라지다");
        movie.setPrice(10000);

        em.persist(movie);

        em.flush();
        em.clear();

        Movie findMove = em.find(Movie.class, movie.getId());
        System.out.println("findMove = " + findMove);

    }

}
