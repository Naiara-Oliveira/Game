package br.com.projetoweb.services;

import br.com.projetoweb.dtos.GameDTO;
import br.com.projetoweb.models.Console;
import br.com.projetoweb.models.Game;
import br.com.projetoweb.models.Partner;
import br.com.projetoweb.repositories.GameRepository;
import br.com.projetoweb.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class GameService {
    private final GameRepository gameRepository;

    private final PartnerRepository partnerRepository;

    @Autowired
    public GameService(GameRepository gameRepository, PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public Game newGame(GameDTO gameDTO) {
        Game newGame = new Game();
        newGame.setName(gameDTO.getName());
        newGame.setConsole(gameDTO.getConsole());
        return this.gameRepository.saveAndFlush(newGame);
    }

    @Transactional
    public void newGame(GameDTO... gameDTO) {
        for (GameDTO game : gameDTO) {
            Game newGame = new Game();
            newGame.setName(game.getName());
            newGame.setConsole(game.getConsole());
            this.gameRepository.save(newGame);
        }
    }

    @Transactional
    public void deleteGame(Game game) {
        this.gameRepository.delete(game);
    }

    public Page<Game> findAll(Pageable page) {
        return this.gameRepository.findAll(page);
    }

    public Optional<Game> findById(Long id) {
        return this.gameRepository.findById(id);
    }

    @Transactional
    public Optional<Game> alterGame(Long id, GameDTO gameDTO) {
        Optional<Game> gamerModel = this.gameRepository.findById(id);
        gamerModel.ifPresent(game -> {
            game.setName(gameDTO.getName());
            game.setConsole(gameDTO.getConsole());
        });
        return gamerModel;
    }

    public Optional<Game> findByNameAndConsole(String name, Console console) {
        return this.gameRepository.findByNameAndConsole(name, console);
    }

    public Set<Game> findGameByPartnerId(Long id) {
        return this.gameRepository.findAllByPartnerId(id);
    }

    @Transactional
    public Optional<Game> deleteGameById(Long gameId) throws Exception {
        Optional<Game> game = this.gameRepository.findById(gameId);
        if (game.isPresent()) {
            Partner partner = game.get().getPartner();
            partner.getGames().remove(game.get());
            if (partner.getGames().isEmpty()) {
                this.partnerRepository.delete(partner);
            }
        }
        return game;
    }
}
