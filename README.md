# e-goku-api

A loja Goku é um e-commerce de vendas de produtos alimentícios que está crescendo 20% ao ano.

E para ajudar nesse crescimento foi construída uma api que mantêm íntegra as informações de endereço de entrega de seus clientes.

# API Stack

Para garantir performance na API e sem gargalos no Banco de Dados foi adotada uma Arquitetura fully non-blocking:

Reactive Stack with Spring WebFlux, MongoDB, Netty e Java 11

# Indexing

Para melhorar a performance na busca de endereço por cep foi criado um índice "zip_idx" na collection "addresses" do MongoDB.

# Caching

Para melhorar ainda mais o desempenho dos serviços foi criada uma estrutura de cache para manter os endereços sempre disponível e atualizado
dispensando a busca no banco de dados quando a informação já está em cache.

# Auditing

Para permitir auditoria em todas as alterações nas informações foi auditado as seguintes informações:
```
createdDate = Data em que a informação foi criada
createdBy = Responsável pela criação da informação
lastModifiedDate = Data da última alteração da informação
lastModifiedBy = Responsável pela última alteração da informação
```

# Securing

De maneira a garantir a segurança das informações, os serviços estão protegidos com a solução JSON Web Tokens e Spring Security 
para garantir que não possam ser acessados por pessoas ou sistemas não autorizados.

# Login

Request:
```
POST http://localhost:8085/auth/token
```

Body Json
```
{
    "username":"Joel",
    "password": "123"
}
```

Response:

Body Json
```
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2VsIiwicm9sZXMiOiJBRE1JTiIsImlhdCI6MTYxOTYyNzA2OSwiZXhwIjoxNjE5NjMwNjY5fQ.vsonUTl7HQ4w2Cjwye1uYCd0id5XDke3JwDJG_ThY_w"
}
```

Header Param
```
Name: Authorization
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2VsIiwicm9sZXMiOiJBRE1JTiIsImlhdCI6MTYxOTYyNzA2OSwiZXhwIjoxNjE5NjMwNjY5fQ.vsonUTl7HQ4w2Cjwye1uYCd0id5XDke3JwDJG_ThY_w"
```

# Swagger

```
http://localhost:8085/swagger-ui.html
```

# Docker

```
sudo docker build -t e-goku-api .
sudo docker run -p 8085:8085 e-goku-api
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

# MongoDB Connection URI Local

```
spring.data.mongodb.uri=mongodb://localhost:27017/test
```

# Cadastro de Endereço

Request:

```
POST http://localhost:8085/v1/addresses
```

Header Param
```
Name: Authorization
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2VsIiwicm9sZXMiOiJBRE1JTiIsImlhdCI6MTYxOTYyNzA2OSwiZXhwIjoxNjE5NjMwNjY5fQ.vsonUTl7HQ4w2Cjwye1uYCd0id5XDke3JwDJG_ThY_w"
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
  "lastModifiedDate": "2021-04-27T00:42:00.346123",
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
Value: "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2VsIiwicm9sZXMiOiJBRE1JTiIsImlhdCI6MTYxOTYyNzA2OSwiZXhwIjoxNjE5NjMwNjY5fQ.vsonUTl7HQ4w2Cjwye1uYCd0id5XDke3JwDJG_ThY_w"
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
  "lastModifiedDate": "2021-04-27T00:42:00.346",
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

