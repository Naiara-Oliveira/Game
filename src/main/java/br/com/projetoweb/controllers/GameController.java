package br.com.projetoweb.controllers;

import br.com.projetoweb.dtos.GameDTO;
import br.com.projetoweb.models.Game;
import br.com.projetoweb.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v2/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Void> newGame(@RequestBody @Valid GameDTO... gameDTO) {
        try {
            this.gameService.newGame(gameDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> alterGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        try {
            Optional<Game> game = this.gameService.alterGame(id, gameDTO);
            return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> findGameByid(@PathVariable Long id) {
        try {
            Optional<Game> game = this.gameService.findById(id);
            return game.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Game>> findAllGames(Pageable pageable) {
        try {
            Page<Game> games = this.gameService.findAll(pageable);
            return ResponseEntity.ok(games);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(this.gameService.deleteGameById(id).get());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
