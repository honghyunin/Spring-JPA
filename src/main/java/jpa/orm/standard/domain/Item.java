package jpa.orm.standard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@DiscriminatorColumn
@Entity
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String name;

    private int price;
}
