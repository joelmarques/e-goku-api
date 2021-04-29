# e-goku-api

A loja Goku é um e-commerce de vendas de produtos alimentícios que está crescendo 20% ao ano.

E para ajudar nesse crescimento foi construída uma api que mantêm íntegra as informações de endereço de entrega de seus clientes.

# API Stack

Para garantir performance na API e sem gargalos no Banco de Dados foi adotada uma Arquitetura fully non-blocking:

Reactive Stack with Spring WebFlux, MongoDB, Netty e Java 11

# Indexing

Para melhorar a performance na busca de endereço por cep foi criado um índice "zip_idx" na collection "addresses" do MongoDB.

```
@Indexed(name = "zip_idx", unique = true)
private String zip;
```

# Caching

Para melhorar ainda mais o desempenho dos serviços foi criada uma estrutura de cache para manter os endereços sempre disponível e atualizado
dispensando a busca no banco de dados quando a informação já está em cache.

```
@Cacheable(value = "addresses", key = "#zip")
public Mono<AddressModel> findByZip(String zip) { return addressRepository.findByZip(zip); }
```

# Auditing

Para permitir auditoria em todas as alterações nas informações foi auditado as seguintes informações:
```
createdDate = Data em que a informação foi criada
createdBy = Responsável pela criação da informação
lastModifiedDate = Data da última alteração da informação
lastModifiedBy = Responsável pela última alteração da informação
```

```
@CreatedDate
private LocalDateTime createdDate;

@CreatedBy
private String createdBy;

@LastModifiedDate
private LocalDateTime lastModifiedDate;

@LastModifiedBy
private String lastModifiedBy;
```

# Securing

De maneira a garantir a segurança das informações, os serviços estão protegidos com a solução JSON Web Tokens e Spring Security 
para garantir que não possam ser acessados por pessoas ou sistemas não autorizados.

Foram implementadas as seguintes regras de acesso:
```
Somente os usuários com perfil "ADMIN" pode cadastrar outros usuários;
Somente os usuários com perfil "USER" pode cadastrar "endereços";
Todos os usuários precisam está autenticados.
```
```
.pathMatchers(HttpMethod.POST,"/v1/users").hasRole("ADMIN")
.pathMatchers(HttpMethod.POST,"/v1/addresses").hasRole("USER")
.pathMatchers("/v1/**").authenticated()
```

# Swagger

Para realizar toda a documentação dos serviços disponibilizados por essa api foi utilizado o Swagger com OpenAPI 3

```
http://localhost:8085/swagger-ui.html
```

# MongoDB Connection URI Local

```
spring.data.mongodb.uri=${SPRING_DATA_MONGODB_URI:mongodb://localhost:27017/test}
```

# Docker

```
sudo docker build -t e-goku-api .
sudo docker run -p 8085:8085 e-goku-api
```

# Docker-compose

Foi criado um "Dockerfile" e um "docker-compose.yml" para criar/subir uma imagem docker da api com Java 11, 
subir uma instancia do mongodb com ambos na mesma network.

Para subir todo o ambiente e deixar disponível para uso, 
basta executar no terminal na raiz da aplicação (diretório onde está o arquivo "docker-compose.yml":

```
$ sudo docker-compose build
$ sudo docker-compose up -d
```

# Caso você ainda não tenha o Docker e o Docker compose instalados*:

*Considerando o SO como Linux/Ubuntu

```
$ sudo apt install docker
$ sudo apt install docker.io
$ docker -v

$ sudo apt install docker-compose
$ docker-compose -v
```

# Health check

Request:

```
GET http://localhost:8085/actuator/health
```

Response:

```
{"status":"UP"}
```

# Login

Request:
```
POST http://localhost:8085/auth/token
```

Body Json
```
{
    "username":"admin",
    "password": "admin"
}
```

Response:

Body Json
```
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTYxOTY2OTgxNCwiZXhwIjoxNjE5NjczNDE0fQ.-85AgUQWjEcouX6Akd0qduLe3RCEqwCi6-LIW1QKYO4"
}
```

Header Param
```
Name: Authorization
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTYxOTY2OTgxNCwiZXhwIjoxNjE5NjczNDE0fQ.-85AgUQWjEcouX6Akd0qduLe3RCEqwCi6-LIW1QKYO4"
```

# Cadastro de Usuários

Obs: Somente os usuários com perfil "ADMIN" pode cadastrar outros usuários.

Request:

```
POST http://localhost:8085/v1/users
```

Header Param
```
Name: Authorization
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsImlhdCI6MTYxOTY2OTgxNCwiZXhwIjoxNjE5NjczNDE0fQ.-85AgUQWjEcouX6Akd0qduLe3RCEqwCi6-LIW1QKYO4"
```

Body JSON

```
{
	"name": "Joel Marques",
	"email": "bitmarques@gmail.com",
	"phone": "(11) 97677-3970",
	"username": "joel",
	"password": "123",
	"active": true,
	"roles": ["USER"]
}
```

Response:
```
Http status: 201
Media type: application/json
```

```
{
  "id": "608a0e7096b4b45ea9a26a57",
  "createdDate": "2021-04-28T22:40:00.109346",
  "createdBy": "admin",
  "lastModifiedDate": "2021-04-28T22:40:00.109346",
  "lastModifiedBy": "admin",
  "name": "Joel Marques",
  "email": "bitmarques@gmail.com",
  "phone": "(11) 97677-3970",
  "username": "joel",
  "password": "{bcrypt}$2a$10$6jHRH5UmOWWoKeFBjdIYhOdNcIE2Nb627RIKkmPeXocsqCsJ/HjNW",
  "active": true,
  "roles": [
    "USER"
  ]
}
```

# Cadastro de Endereço

Obs: Somente os usuários com perfil "USER" pode cadastrar "endereços".

Request:

```
POST http://localhost:8085/v1/addresses
```

Header Param
```
Name: Authorization
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2VsIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2MTk2Njk5MzksImV4cCI6MTYxOTY3MzUzOX0.7OzRCbQ4Zys8r4Ir9lBogfN9RZ4AyoUB5sy8pqJrMvE"
```

Body JSON

```
{
  "zip": "88330659",
  "line1": "Avenida do Estado Dalmo Vieira",
  "line2": "de 4047 a 4595 - lado ímpar",
  "district": "Centro",
  "city": "Balneário Camboriú",
  "state": "SC",
  "country": "BR",
  "ibge": "4202008",
  "ddd": "47"
}
```

Response:
```
Http status: 201
Media type: application/json
```

```
{
  "id": "60878808d652a12deb8cc1fd",
  "createdDate": "2021-04-27T00:42:00.346123",
  "createdBy": "joel",
  "lastModifiedDate": "2021-04-27T00:42:00.346123",
  "lastModifiedBy": "joel",
  "zip": "88330659",
  "line1": "Avenida do Estado Dalmo Vieira",
  "line2": "de 4047 a 4595 - lado ímpar",
  "district": "Centro",
  "city": "Balneário Camboriú",
  "state": "SC",
  "country": "BR",
  "ibge": "4202008",
  "ddd": "47"
}
```

# Busca de Endereço por CEP

Obs: Todos os usuários autenticados podem buscar endereços.

URI com Path Variable:
```
GET http://localhost:8085/v1/addresses/{zip}
```

Request:
```
GET http://localhost:8085/v1/addresses/88330659
```

Header Param
```
Name: Authorization
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2VsIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2MTk2Njk5MzksImV4cCI6MTYxOTY3MzUzOX0.7OzRCbQ4Zys8r4Ir9lBogfN9RZ4AyoUB5sy8pqJrMvE"
```

Response:
```
Http status: 200
Media type: application/json
```

```
{
  "id": "60878808d652a12deb8cc1fd",
  "createdDate": "2021-04-27T00:42:00.346",
  "createdBy": "joel",
  "lastModifiedDate": "2021-04-27T00:42:00.346",
  "lastModifiedBy": "joel",
  "zip": "88330659",
  "line1": "Avenida do Estado Dalmo Vieira",
  "line2": "de 4047 a 4595 - lado ímpar",
  "district": "Centro",
  "city": "Balneário Camboriú",
  "state": "SC",
  "country": "BR",
  "ibge": "4202008",
  "ddd": "47"
}
```

# Referências
```
https://www.baeldung.com/spring-5
https://www.baeldung.com/spring-5-functional-web
https://www.baeldung.com/spring-security-5-reactive
https://www.baeldung.com/spring-cache-tutorial
https://spring.io/reactive
https://spring.io/guides/gs/caching/
https://springdoc.org/
https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html
https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#auditing
https://jwt.io/
```

# That's all

Hope you enjoy it.

