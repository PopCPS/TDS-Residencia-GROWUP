# Sobre nós

Somos o squad 23 da Residência do Porto digital. Realizamos essa solução para melhoria
da plataforma Strateegia. A plataforma Strateegia busca promover colaboração entre 
inteligências individuais, sociais e artificiais. Desse forma, isso gera a necessidade 
de extrair dados das discussões para criar um relatório que permita analisar as divergências
nos debates.

# Baixe o relatório dos seus debates

## Instalação do sistema

1- Clone o repositório: git clone.

2- Abra em uma IDE de código. Sugestão: Intellij.

2- Inicie a aplicação com o botão de "Run"

4 - Baixe uma ferramenta para testes de requisições HTTP. Sugestão: Httpie ou postman

5 - Faça uma conta no strateegia e use o mesmo login e senha de lá para logar na nossa aplicação.

## Baixe o relatório do debate escolhido

1- Cole no httpie o link: "http://localhost:8080/journeys"

![journeys](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/ed1c9d9b-72af-44bf-91c2-99b72f520d6e)

2- Pegue o número de id da jornada que você deseja .

3- Cole no httpie o link: "http://localhost:8080/maps"   

![maps](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/0ca60f31-95a5-4675-bbac-c6981586d08b)

4- Pegue o número de id do mapa que você deseja.

5- Cole no httpie o link: "http://localhost:8080/points"

![points](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/7a9acf57-a0f9-45fc-98d4-0b363ddf6eb1)

6- Visualize qual o ponto de divergência que você deseja e pegue sua posição.

7- Cole no navegador o link e substitua os seus respectivos parâmetros para baixar o docx :  http://localhost:8080/export/{journeyId}/{mapId}/{pointDivergence}

![localhost_8080_export_660572d9feb2310be9fd8c3f_660572d9feb2310be9fd8c40 - Google Chrome 04_06_2024 14_57_07](https://github.com/PopCPS/TDS-Residencia-GROWUP/assets/110575974/a4511856-2b48-4748-b8fd-a7cf3e6fdf9c)
