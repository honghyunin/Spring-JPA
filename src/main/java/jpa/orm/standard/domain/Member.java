package jpa.orm.standard.domain;

import jpa.orm.standard.embedded.Address;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    private Address homeAddress;
}
