package br.com.projetoweb.repositories;

import br.com.projetoweb.models.Console;
import br.com.projetoweb.models.Game;
import br.com.projetoweb.models.GameLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface GameLoanRepository extends JpaRepository<GameLoan, Long> {

    Optional<GameLoan> findByGames_Id(Long id);

    void deleteById(Long id);
    Set<GameLoan> findAllByPartnerId(Long id);
}


