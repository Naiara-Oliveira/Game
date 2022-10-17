package br.com.projetoweb.controllers;

import br.com.projetoweb.dtos.PartnerDTO;
import br.com.projetoweb.models.Game;
import br.com.projetoweb.models.Partner;
import br.com.projetoweb.services.GameService;
import br.com.projetoweb.services.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("v2/partner")
public class PartnerController {

    private final PartnerService partnerService;
    private final GameService gameService;

    @Autowired
    public PartnerController(PartnerService partnerService, GameService gameService) {
        this.partnerService = partnerService;
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Set<Game>> newPartner(@RequestBody PartnerDTO partnerDTO) {
        try {
            Set<Game> games = this.partnerService.newPartner(partnerDTO);
            return ResponseEntity.ok(games);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Partner>> findAllPartners(Pageable pageable) {
        try {
            Page<Partner> partners = this.partnerService.findAllPartners(pageable);
            return ResponseEntity.ok(partners);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/games/{id}")
    public ResponseEntity<Set<Game>> findGameByPartnerId(@PathVariable Long id) {
        try {
            Set<Game> gameByPartnerId = this.gameService.findGameByPartnerId(id);
            return ResponseEntity.ok(gameByPartnerId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id){
        try {
            this.partnerService.deletePartner(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}