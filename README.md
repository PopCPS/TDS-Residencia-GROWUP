
![image](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/d413eb41-a769-4fca-9ae8-f465e3f656ce)

# Sobre nós

Somos o squad 23 da Residência do Porto digital. Realizamos essa solução para melhoria
da plataforma Strateegia. O Strateegia é uma plataforma criada pela empresa TDS que busca promover 
a colaboração entre um grupo/equipe através de debates. Você pode criar jornadas, mapas e pontos de 
divergência para ter debates importantes e aumentar a produtividade. Dessa forma, surge a necessidade
de extrair os dados das discussões para criar um relatório que permita analisar as divergências nos debates. 

# Baixe o relatório dos seus debates

## Instalação do sistema

1- Clone o repositório: git clone.

2- Abra em uma IDE de código. Sugestão: Intellij.

2- Inicie a aplicação com o botão de "Run"

4 - Baixe uma ferramenta para testes de requisições HTTP. Sugestão: Httpie ou postman

5 - Crie uma conta na plataforma strateegia e use o mesmo login e senha de lá para logar na nossa aplicação.

## Baixe o relatório do debate escolhido

1-Abra o Httpie, se autentique com o  Basic Auth e cole no httpie o link: "http://localhost:8080/journeys"

![journeys](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/ed1c9d9b-72af-44bf-91c2-99b72f520d6e)

2- Pegue o número de id da jornada que você deseja .


3- Cole no httpie o link: "http://localhost:8080/maps" e nos parâmetros adicione o id da jornada que você deseja gerar o relatório. 
Obs: o nome do parâmetro precisa ser: "journeyId"

![maps](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/ad7384e5-9219-444a-8061-bacdb1facbc7)


4- Pegue o número de id do mapa que você deseja

.
5- Cole no httpie o link: "http://localhost:8080/points"  e nos parâmetros adicione o id do mapa que você deseja gerar o relatório. 
Obs: o nome do parâmetro precisa ser: "mapId"

![points2](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/8b6d8f1a-b1b8-403b-b000-8c713d5f5ba9)


6- Visualize qual o ponto de divergência que você deseja e pegue sua posição.

7- Cole no navegador o link e substitua os seus respectivos parâmetros para baixar o docx :  http://localhost:8080/export/{journeyId}/{mapId}/{pointDivergence}
Obs: No pointDivergence você precisa colocar o número da posição do objeto que tem os dados do ponto de divergência 

![tela2](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/dbaf7e33-222c-45b7-a49a-abfa0a9ea63d)
