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
- Assim como a estética, a história ainda não foi definida.

*Mecânica:*

- Mapa:
 - Cada partida de ubiDefense se passa em um mapa; 
 - O mapa é um retângulo(tamanho a definir) situado sobre o mundo real; 
 - O jogadores poderão entrar no mapa, e a partir daí irão participar da mesma partida; 

 
- Rota:
 - A cada determinado intervalo de tempo um novo jogador(aleatório?) é escolhido para desenhar a rota pela qual os monstros seguirão; 
 - O jogador terá um limite de tamanho, mínimo e máximo, que deverá desenhar a rota; 
 - A rota deverá começar e terminar em uma extremidade do mapa, sendo que a extremidade de inicio e de final devem ser diferentes; 
 - A nova rota não poderá cruzar a rota anterior; 

 
- Torres:
 - Cada torre terá 2 atributos: nível e elemento(dia/noite/neutro). 
 - O jogador deverá acumular recursos para construir as torres. O recursos são acumulados a medida que o jogador se locomove pelo mapa. 
 - Ao se aproximar de uma torre que foi construída por outro jogador o jogador poderá evolui-la. Cada jogador só poderá evoluir cada torre uma vez. Ao evoluir uma torre o jogador receberá um bônus multiplicador temporário que irá aumentar a quantidade de recursos que ele acumula. 

 
- Inimigos: 
 - O inimigos irão surgir aleatoriamente no começo da rota e a seguirão até o seu final. 
 - Cada inimigo terá 2 atributos principais: vida e elemento(dia/noite/neutro). 
 - As torres causam dano nos inimigos igual ao seu nível porém torres de elemento oposto causam o dobro de dano e torres de elemento igual causam metade do dano.


*Tecnologia:*
- O jogo irá possuir um smartspace de tamanho ainda a ser definido, onde estarão incluidos os dispositivos dos jogadores(tablets, celulares, etc), bem como um servidor central responsável pelo controle de certas funções do jogo(manutenção dos inimigos, criação das rotas e ondas de inimigos). Para um teste inicial será considerado o campus Darcy Ribeiro da Unb.
- Os dispositivos dos jogadores deverão possuir alguma forma de localização espacial(ex: GPS), para que seja possível determinar onde suas torres serão instaladas, além da distância percorrida pelo jogador para acumulação de recursos.
- Será necessário o uso de um mapa para localização dos jogadores. O serviço de mapa utilizado ainda será definido.
- Os dispositivos deverão poder se comunicar com o servidor central(e talvez entre si, ainda a se definir de acordo com a definição completa do gameplay), para que informações do jogo possam ser comunicadas e atualizadas entre todos os jogadores. A princípio essa comunicação deverá se dar de forma contínua e síncrona entre o servidor e os dispositivos clientes.
