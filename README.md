*Descrição:* O jogo ubiDefense *(nome provisório)* será um jogo estilo Tower Defense, em que os jogadores, atráves de dispositivos como tablets e celulares, deverão construir torres em localizações do mundo real para defender ondas de inimigos.

___________________________________________
**Tétrade Elementar**<br>

*Estética:*
- ubiDefense será um jogo exclusivamente mobile, jogado através de um smartphone ou tablet, com toda interação sendo feita por meio da tela sensível ao toque.
- O jogo apresentará uma interface simples, contando apenas com:
 - Um mapa dos arredores do usuário, incluindo as torres que já estão construídas nesse espaço;
 - Indicador dos recursos atuais do jogador;
 - Lista das torres que o jogador pode construir no momento;

*História:*
- O jogo se passa em um cenário virtual, onde os inimigos representam vírus de computadores que querem invadir um sistema. O usuário deve proteger esse sistema colocando emissores de sinais que irão atrair, fazendo-os perder sua força antes que eles cheguem ao seu alvo.

*Mecânica:*

- Mapa:
 - Cada partida de ubiDefense se passa em um mapa; 
 - Um jogador pode iniciar uma partida ao desenhar um quadrado, selecionando pontos que serão dois vértices opostos. Esse quadrado irá representar o mapa em que se passará a partida, onde outros jogadores poderão participar;


- Torres:
 - Os jogadores deverão construir torres que irão atrair os inimigos. Essas torres terão dois atributos: sinal e bateria:
    - Sinal: Representa a área de alcance da torre. É definido pela quantidade de redes wifi que o dispositivo do jogador encontrar na hora de sua construção;
    - Vida: Representa a vida da torre. É definido pela quantidade de bateria que o dispositivo do jogador possui na hora de sua construção;

 
- Inimigos: 
 - Os inimigos surgem no começo da arena e seguem em linha reta até o seu final. Eles possuem uma quantidade de energia, que determina o tempo que irão sobreviver na arena;
 - Ao entrarem dentro do raio de alcance de uma torre eles seguem em linha reta em direção a essa torre;
 - Ao chegarem na torre, os inimigos diminuem a bateria da torre;


*Tecnologia:*
- O jogo irá possuir um smartspace de tamanho ainda a ser definido, onde estarão incluidos os dispositivos dos jogadores(tablets, celulares, etc), bem como um servidor central responsável pelo controle de certas funções do jogo(manutenção dos inimigos, criação das rotas e ondas de inimigos). 
- Os dispositivos dos jogadores deverão possuir alguma forma de localização espacial(ex: GPS), para que seja possível determinar onde suas torres serão instaladas, além da distância percorrida pelo jogador para acumulação de recursos.
- Será utilizado a API do Google Maps para a exibição e uso do mapa;
- Os dispositivos deverão poder se comunicar com o servidor central, para que informações do jogo possam ser comunicadas e atualizadas entre todos os jogadores. A princípio essa comunicação deverá se dar de forma contínua e síncrona entre o servidor e os dispositivos clientes.
