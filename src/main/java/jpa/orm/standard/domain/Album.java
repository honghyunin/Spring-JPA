package jpa.orm.standard.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter @Setter
@Entity
public class Album extends Item {

    private String artist;
}
