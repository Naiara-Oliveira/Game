package br.com.projetoweb.controllers;

import br.com.projetoweb.dtos.PegarIdDTO;
import br.com.projetoweb.models.GameLoan;
import br.com.projetoweb.services.GameLoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v2/game-loan")

public class GameLoanController {
    private final GameLoanService gameLoanService;


    public GameLoanController(GameLoanService gameLoanService) {
        this.gameLoanService = gameLoanService;
    }

    @PostMapping("/{partnerId}")
    public ResponseEntity<GameLoan> newGameLoan(@PathVariable Long partnerId, @RequestBody PegarIdDTO id) {
        try {
            GameLoan gameLoan = this.gameLoanService.newGameLoan(partnerId, id.getId());
            return ResponseEntity.ok(gameLoan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<GameLoan>> findGameLoanById(@PathVariable Long id) {
        try {
            Optional<GameLoan> gameLoan = this.gameLoanService.findById(id);
            return ResponseEntity.ok(gameLoan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<Page<GameLoan>> findAllGameLoans(Pageable pageable) {
        try {
          Page<GameLoan> gameLoans = this.gameLoanService.findAll(pageable);
            return ResponseEntity.ok(gameLoans);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameLoan(@PathVariable Long id) {
        try {
            this.gameLoanService.deleteGameLoan(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
