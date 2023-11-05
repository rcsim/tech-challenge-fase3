# tech-challenge-fase3  
## Repositório para o Tech Challenge - Grupo 30
Este repositório contém o código-fonte e a documentação para o Tech Challenge - Fase 3, desenvolvido pelo Grupo 30.

O projeto consiste em uma API RESTful para gerenciamento de estacionamento. A API permite criar um usuário, fazer checkin e checkout de um veículo estácionado, e realizar o pagamento por estacionar.

## 1- Relatório Técnico
Tecnologias e ferramentas utilizadas

* Linguagem de programação: 

    * Java 17

* Framework:
    * Spring Boot 3.1.3

* Bibliotecas:
  * Spring Web
  * Spring Mail
  * Spring job Runner
  * OpenAPI
  * IText Core
  * Lombok
  * 
* Banco de dados:
  * PostgreSQL
* Outras ferramentas:
  * Docker 

## Configurações da solução

### Banco de Dados
Configuração de teste utilizando o H2:
https://github.com/rcsim/tech-challenge-fase3/blob/e6cc39c83eccb9eca2657d5cf7ed8c54938d04d9/src/main/resources/application-test.properties#L1-L5

Configuração de produção utilizando o PostgreSQL
https://github.com/rcsim/tech-challenge-fase3/blob/e6cc39c83eccb9eca2657d5cf7ed8c54938d04d9/src/main/resources/application-dev.properties#L1-L7

Relacionamento entre as entidades:

![image](https://github.com/rcsim/tech-challenge-fase3/assets/30301531/a5657a69-add1-44d8-a3a3-fb196b687ffc)


### Container


Criamos um container para aplicação e outro para o banco de dados e uma rede no modo bridge para ter acesso ao containers via localhost:

https://github.com/rcsim/tech-challenge-fase3/blob/e6cc39c83eccb9eca2657d5cf7ed8c54938d04d9/docker-compose.yml#L1-L33

Também adicionamos o arquivo Dockerfile que gerencia o processo de build da aplicação através do Maven e JDK, já inicializando a aplicação:

https://github.com/rcsim/tech-challenge-fase3/blob/e6cc39c83eccb9eca2657d5cf7ed8c54938d04d9/Dockerfile#L1-L15

Para criação dos container, compilar e rodar a applicação é necessário apenas o comando:

docker-compose up -d

![image](https://github.com/rcsim/tech-challenge-fase3/assets/30301531/64153730-7581-45b2-ad76-ace76dd798a9)


### Documentação das APIS 
Adicionamos a geração automática da documentação através da biblioteca SpringDoc OpenAPI, a documentação pode ser acessada enquanto a aplicação estiver rodando em http://localhost:8080/swagger-ui/index.html#/:

![image](https://github.com/rcsim/tech-challenge-fase3/assets/30301531/146d8af8-6bb3-411a-9da9-0dad791d25ef)
![image](https://github.com/rcsim/tech-challenge-fase3/assets/30301531/728cd1ac-29e2-4070-8cc2-479d38eb7358)
![image](https://github.com/rcsim/tech-challenge-fase3/assets/30301531/86d37fa7-1104-413c-98e3-0cb80983924c)


#### Arquivo POSTMAN

Um arquivo JSON com todas as requisições Postman para testar a API está disponível no seguinte link:

https://github.com/rcsim/tech-challenge-fase3/blob/main/src/main/resources/postman/Tech-challenge-3.postman_collection.json

## Deploy AWS

Elaboramos um arquivo "build.bat" com o propósito de facilitar o processo de implantação das imagens Docker no Amazon Elastic Container Registry (ECR). Ao ajustar os parâmetros neste arquivo, você pode provisionar tanto o contêiner da aplicação quanto o do banco de dados. Para orquestrar a execução desses contêineres no cluster ECS que foi criado, configuramos uma definição de tarefa (task definition). Além disso, montamos um ambiente ECS baseado em infraestrutura EC2, fornecendo instâncias que serão encarregadas de executar os contêineres. Estas instâncias são fundamentais para a operação e funcionamento adequado de nossas aplicações.
Este conjunto de ações garante uma estrutura sólida e escalável para a execução de seus contêineres Docker no ambiente AWS.

![Arquitetura Aws](src/main/resources/Aws/arquitetura.png)

Evidencias em fotos na pasta: src/main/resources/Aws





## Conclusões 

O projeto foi desenvolvido com sucesso, atendendo aos requisitos do desafio. As tecnologias e ferramentas utilizadas foram adequadas para o propósito do projeto e contribuíram para o desenvolvimento eficiente e robusto do sistema.

Principais dificuldades encontradas
* Entendimento das regras de negócio: As regras de negócio do projeto eram complexas e exigiam um entendimento cuidadoso.
