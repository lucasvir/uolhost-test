# Uol Host teste back-end
API Rest - proposta de teste back-end do Uol Host. <br />
Desenvolvido para meus estudos em Java e Spring Framework

> "O teste consiste em montar uma aplicação Java capaz de recuperar informações de um arquivo XML e de um arquivo JSON, persistir um cadastro em um banco de dados em memória ou em arquivo e listar os cadastros em uma interface simples." <br />

<br />

Projeto inclui:
- Leitura e processamento de dados que estão em arquivos JSON e XML via URL externa.
- Persistencia em banco de dados em memória (H2). 
- Listar todos os usuário incluindo os codinomes escolhidos randomicamente dos arquivos, bem como de que lista o codinome pertence.

Mais detalhes da [proposta](https://github.com/uolhost/test-backEnd-Java). 
<br />
Repositório do [front](https://github.com/lucasvir/uolhost-spa).
<br />
Aplicação [Front-end](https://uolhost-spa-lucasvir.vercel.app).

<br />

## Techs:
  - Java 17
  - Spring Framework
  - JPA/Hibernate
  - JUnit
  - H2 Database
  - Maven

<br />
    
## :construction: EXEC

> Pré-rquisitos:
> - instalar o Java ([Oracle](https://www.oracle.com/java/technologies/downloads/)).
> - instalar banco de dados H2 ([H2 Database](http://www.h2database.com/html/download.html)).


```bash

 # clonando repositório
git clone https://github.com/lucasvir/uolhost-test.git

```

```bash
#acessar diretório
cd uolhost-test
```

```bash
#inicializar aplicação setando as variáveis de ambiente
#DB_URL: endereço do banco de dados
#DB_USERNAME: nome do usuário do banco de dados (padrão no H2: sa)
#DB_PASSWORD: senha de acesso ao banco de dados (padrão no H2: password)

#exemplo:
java -DDB_URL=jdbc:h2:mem:uolhost -DDB_USERNAME=<nomedousuario> -DDATASOURCE_PASSWORD=<senhadousuario> -jar target/api-0.0.1-SNAPSHOT.jar
```

```bash
#acessar banco de dados
No browser acesse: http://localhost:8080/h2-console
```

*obs: a porta exposta da api deve ser '8080' para o funcionamento correto com o front-end já [hospedado](https://uolhost-spa-lucasvir.vercel.app).*

## 

### POST - /users/form
Criando usuário

![Create User](github/imgs/uoltest_post.png)

### GET - /users
Listando todos usuários

![Index Users](github/imgs/uolhots_get.png)

### DB - SELECT * users
DB com os dados persistidos

![Persistence Layer](github/imgs/db_persist.png)

