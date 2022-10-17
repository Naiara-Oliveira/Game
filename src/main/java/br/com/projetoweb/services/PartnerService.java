package br.com.projetoweb.services;

import br.com.projetoweb.dtos.GameDTO;
import br.com.projetoweb.dtos.PartnerDTO;
import br.com.projetoweb.models.Game;
import br.com.projetoweb.models.Partner;
import br.com.projetoweb.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final GameService gameService;

    @Autowired
    public PartnerService(PartnerRepository partnerRepository, GameService gameService) {
        this.partnerRepository = partnerRepository;
        this.gameService = gameService;
    }

    @Transactional
    public Set<Game> newPartner(PartnerDTO partnerDTO) {
        Partner partner = new Partner();
        partner.setName(partnerDTO.getName());
        partner.setPhoneNumber(partnerDTO.getPhoneNumber());
        Set<Game> games = findGames(partnerDTO.getGames(), partner);
        if (games.isEmpty()) {
            throw new RuntimeException("Nenhum jogo encontrado");
        }
        return games;
    }

    private Set<Game> findGames(Set<GameDTO> games, Partner partner) {
        Set<Game> listGame = new HashSet<>();
        for (GameDTO gameDTO : games) {
            Optional<Game> game = this.gameService.findByNameAndConsole(gameDTO.getName(), gameDTO.getConsole());
            if (game.isEmpty()) {
                this.gameService.newGame(gameDTO);
            }
            if (game.get().getPartner() == null) {
                game.get().setPartner(partner);
                listGame.add(game.get());
                partner.setGames(listGame);
                this.partnerRepository.saveAndFlush(partner);
            }else {
                throw new RuntimeException("Game com Dono");
            }
        }
        return listGame;
    }

    public Page<Partner> findAllPartners(Pageable pageable) {
        return this.partnerRepository.findAll(pageable);
    }

    @Transactional
    public void deletePartner(Long id) {
        this.partnerRepository.deleteById(id);
    }

}
