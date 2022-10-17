package br.com.projetoweb.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
//emprestimo Jogo
public class GameLoan {
    @Id
    private int id;
    @OneToOne
    private Partner partner;
    @OneToOne
    private Game games;
    @Column(nullable = false, length = 10)
    private LocalDate loanDate;
    @Column(nullable = false, length = 10)
    private LocalDate scheduledReturnDate;
    @Column(nullable = false, length = 10)
    private LocalDate returnDate;


    public GameLoan(Game game, Partner partner) {
        this.games = game;
        this.partner = partner;
        this.loanDate = LocalDate.now();
        this.scheduledReturnDate = LocalDate.now().plusDays(7);
    }

}
