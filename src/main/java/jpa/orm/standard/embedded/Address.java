package jpa.orm.standard.embedded;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Address {
    public Address(String city, String name) {
        this.city = city;
        this.homeName = name;
    }

    private String city;
    private String homeName;
}
