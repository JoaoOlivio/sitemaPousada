# Projeto Sistema de Pousada


* Este é um projeto realizado como trabalho para a disciplina de Programação Orientada a Objetos II. Esse sistema é responsável cadastrar quartos, além de cliente e da reserva. Também é possivel gerar gráficos e relatórios.

* Como requisito do trabalho, foi criado uma regra de negócio na qual não é possível realizar uma reserva para um quarto quando ele ja estiver reservado no sistema.

## :desktop_computer: Tecnologias e Conceitos utilizados 
- Linguagem **Java** e a tecnologia **JavaFx** para construção das interfaces gráficas. 
- Biblioteca **JFoenix** que implementa o _Material Design_ utilizando componentes Java.
- **Padrão MVC** (Model, View e Controller) que divide o projeto em camadas de responsabilidades. 
- **Padrão DAO** (Data Acess Object) que isola as regras de negócio das regras de persistência de dados.
- **PostgreSQL** como SGBD para armazenar e gerenciar os dados da aplicação.
- **Padrão Factory** para gerar a instância de um determinado SGBD para persistência dos dados, permitindo gerar uma instância de outro SGBD como o MySql.  
- **Jasper Reports** para geração de relatórios de entrada de animais com filtro de data.


---

## :man_technologist: Como executar o Sistema

- Instale o Java 8 em seu Computador.
- Faça um clone deste repositório.
- Abra o projeto em sua IDE de preferência (Net Beans, VsCode, etc).
- Certifique-se que as bibliotecas do diretório 'lib' estão sendo utilizados.
- Crie um Banco de Dados no PostgreSQL com nome de 'db_granja'
- Execute o código SQL dentro do arquivo 'script_bd.sql' para criar a estrutura do banco de dados da aplicação.
