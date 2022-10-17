package br.com.projetoweb.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "game",
        uniqueConstraints = { @UniqueConstraint (name = "game_uq" , columnNames = {
                "name", "console" })})
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Console console;
    @ManyToOne
    @JsonIgnore
    private Partner partner;

    public void add(Game game) {
        this.setPartner(game.getPartner());
    }


}
