
# Game


<h2>Criação de um catálogo para um grupo de gamers, onde se pode consultar quem possui quais games.</h2>

<h4>Três entidades independentes:</h4>
<ul>GameModel
<li> id, name, console, owner</li>
</ul>
	
<ul>
<li>PartnerModel</li>
<li>id, name, phoneNumber</li>
</ul>

<ul>
<li>GameLoanModel</li>
<li>id, partner, game, loanDate, scheduledReturnDate , returnDate</li>
</ul>

<h4>Restrição:</h4>
<p>Para se associar e manter-se associado, cada membro deve ter pelo menos um jogo no catálogo.</p>
<br>
<ul>
	<li>(+) Não pode realizar empréstimo se estiver em atraso.</li>
	<li>(+) Não pode realizar empréstimo de mais de 5 jogos simultâneamente.</li>
	<li> (+) Não pode permir a exclusao de um associado se houver empréstimos ativo de jogos dele ou se ele tiver empréstimos ativos de jogos de outro associado.</li>
</ul>
