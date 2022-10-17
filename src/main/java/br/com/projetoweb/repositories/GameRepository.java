package br.com.projetoweb.repositories;

import br.com.projetoweb.models.Console;
import br.com.projetoweb.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByNameAndConsole(String name, Console console);
    Set<Game> findAllByPartnerId(Long id);
}
