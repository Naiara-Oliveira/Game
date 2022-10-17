package br.com.projetoweb.services;

import br.com.projetoweb.models.GameLoan;
import br.com.projetoweb.repositories.GameLoanRepository;
import br.com.projetoweb.repositories.GameRepository;
import br.com.projetoweb.repositories.PartnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class GameLoanService {
    private final GameLoanRepository gameLoanRepository;
    private final GameRepository gameRepository;
    private final PartnerRepository partnerRepository;
    private final int maxGame = 5;

    public GameLoanService(GameLoanRepository gameLoanRepository, GameRepository gameRepository, PartnerRepository partnerRepository) {
        this.gameLoanRepository = gameLoanRepository;
        this.gameRepository = gameRepository;
        this.partnerRepository = partnerRepository;
    }


    public Set<GameLoan> findAllByPartnerId(Long id) {
        return this.gameLoanRepository.findAllByPartnerId(id);
    }

    @Transactional
    public GameLoan newGameLoan(Long gameId, Long partnerId) {
        var game = this.gameRepository.findById(gameId).orElseThrow();
        var gameLoanByPartner = this.gameLoanRepository.findAllByPartnerId(partnerId);

        if (gameLoanByPartner.size() >= maxGame) {
            throw new RuntimeException("Limite de empréstimos atingido");
        }
        if(gameLoanByPartner.size() > 0){
        for (GameLoan gameLoan : gameLoanByPartner) {
            if (gameLoan.getLoanDate().isAfter(gameLoan.getReturnDate())) {
                throw new RuntimeException("Jogo ainda não devolvido");
            }}
        }
        if(gameLoanByPartner.isEmpty()){
           var gameLoan = new GameLoan();
            gameLoan.setGames(game);
            gameLoan.setPartner(this.partnerRepository.findById(partnerId).orElseThrow());
            gameLoan.setLoanDate(LocalDate.now());
            gameLoan.setReturnDate(LocalDate.now().plusDays(7));
            return this.gameLoanRepository.saveAndFlush(gameLoan);
        }else{
            throw new RuntimeException("Jogo já emprestado");
        }
    }
    @Transactional
    public Optional<GameLoan> findById(Long id) {
        return this.gameLoanRepository.findById(id);
    }

    @Transactional
    public Page<GameLoan> findAll(Pageable page) {
      return this.gameLoanRepository.findAll(page);
    }

    @Transactional
    public void deleteGameLoan(Long id) {
        this.gameLoanRepository.deleteById(id);
    }


}