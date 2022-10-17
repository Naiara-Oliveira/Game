package br.com.projetoweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "partner",
        uniqueConstraints = {@UniqueConstraint(name = "partner_uq", columnNames = {
                "name", "phoneNumber"})})
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 15)
    private String phoneNumber;
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Game> games = new HashSet<>();
    @JsonIgnore
    public void setGames(Set<Game> games) {
        this.games.addAll(games);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Partner partner = (Partner) o;
        return id != null && Objects.equals(id, partner.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
