package jpa.orm.standard.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Locker {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
