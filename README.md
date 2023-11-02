


# ZupPlaces

A ideia por trás da ZuPlaces é conectar as pessoas com os recursos essenciais para o seu dia a dia, independentemente de sua localização. O projeto visa orientar os usuários na busca por facilidades como por exemplo: acesso à internet, computadores, água potável, espaços para dança e energia para carregar dispositivos móveis. O produto surge para solucionar a seguinte questão: onde posso encontrar o que preciso, com base em onde estou ou pretendo estar?

### API REST

Com essa API você consegue pegar a localização atual do usuário e calcular a distância dos espaços cadastrados, sendo assim, depois jogar no mapa do frontend.


## 🖥️ Tecnologias Utilizadas
* `Java 11` - Linguagem de programação
* `Spring Boot (2.7.15)` - Framework para criação de aplicativos Java
* `Spring Boot Data JPA` - Facilita o acesso a bancos de dados relacionais.
* `Spring Boot Validation` - Biblioteca que ajuda na validação de entrada de dados em aplicativos Spring Boot.
* `Spring Boot Web` - Facilita o desenvolvimento de aplicativos da web usando o Spring Boot.
* `H2 Database (Runtime)` - Um banco de dados SQL leve e embutido que é executado em tempo de execução.
* `PostgreSQL` - Um robusto banco de dados.
* `Lombok` - Uma biblioteca Java que ajuda a reduzir a verbosidade do código.
* `Springdoc OpenAPI UI (1.7.0)` -  Uma ferramenta que gera automaticamente a documentação da API com base nas anotações do Spring.
* `Junit (4.12)` -  Uma ferramenta para teste unitários.
* `Modelmapper (2.4.4)` -  Ferramenta para fazer o mapeamento entre model e DTO.
* `Gson (2.8.8)` -  Ferramenta para converter JSON para objeto ou objeto em JSON.
* `HTML` - Linguagem de marcação para estruturar o conteúdo da página.
* `CSS` - Linguagem de estilização para dar estilo às páginas.
* `JavaScript` - Linguagem de programação para interatividade do usuário.

---

# ⚙️ Como Executar a Aplicação BackEnd

1. **Pré-requisitos:**
    - Certifique-se de ter o [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11) instalado em seu computador.

2. **Clone o Repositório:**
    - Faça o clone do repositório do projeto para o seu ambiente de desenvolvimento.

3. **Navegue até o Diretório:**
    - Abra o terminal e navegue até o diretório onde se encontra o arquivo `Application.java`.

4. **Compilação:**
    - Compile o arquivo utilizando o seguinte comando:
      ```
      javac Application.java
      ```

5. **Execução:**
    - Após compilar, execute a aplicação com o seguinte comando:
      ```
      java Application.java
      ```

<br> <br>

## 🚀 Como Executar o Frontend com Live Server no VSCode

## Passo 1: Instale o Visual Studio Code (VSCode) e a extensão Live Server

Certifique-se de ter o Visual Studio Code instalado em seu computador. Você pode baixá-lo em [https://code.visualstudio.com/](https://code.visualstudio.com/). Em seguida, instale a extensão Live Server. Para fazer isso, siga estas etapas:

- 📦 Abra o VSCode.
- 🛠️ Vá para a aba "Extensões" no menu lateral esquerdo.
- 🔍 Pesquise por "Live Server" na barra de pesquisa.
- 📥 Clique em "Instalar" ao lado da extensão oferecida por Ritwick Dey.

## Passo 2: Crie um projeto frontend

Certifique-se de que você já tenha um projeto frontend com um arquivo HTML (como o `index.html`) pronto para ser executado. Se ainda não tiver um, crie um diretório para seu projeto e adicione um arquivo HTML (por exemplo, `index.html`) e outros arquivos (CSS, JavaScript, etc.) conforme necessário.

## Passo 3: Abra o projeto no VSCode

Abra o VSCode e vá para o menu "Arquivo" (File) > "Abrir Pasta" (Open Folder). Selecione a pasta raiz do seu projeto frontend.

## Passo 4: Configure o Live Server

Agora que você está no VSCode com seu projeto aberto, siga estas etapas para configurar o Live Server:

- 📄 Abra o arquivo `index.html` do seu projeto no VSCode.
- 🖱️ Clique com o botão direito no arquivo `index.html` e escolha "Abrir com o Live Server" no menu de contexto.

## Passo 5: Execute a aplicação

O Live Server abrirá o arquivo `index.html` no seu navegador padrão. Normalmente, ele abrirá em http://127.0.0.1:5500/, que é uma URL local. Como mencionado, essa abordagem ajuda a contornar problemas de Cross-Origin Resource Sharing (CORS) durante o desenvolvimento.

Agora você deverá ver sua aplicação frontend em execução no navegador.

Lembre-se de que, ao usar o Live Server, ele também fornece funcionalidades de recarga automática, o que significa que qualquer alteração feita nos arquivos do seu projeto será refletida automaticamente no navegador sem a necessidade de atualizar manualmente a página. Isso torna o desenvolvimento frontend mais eficiente.

Espero que este guia seja útil para você rodar sua aplicação frontend usando o Live Server no VSCode. Se você encontrar problemas ou mensagens de erro, verifique sua configuração e certifique-se de que tudo foi seguido corretamente.


## 🌟 StackSpot para a arquitetura backend

Foi utilizado StackSpot, criamos um plugin para montar a arquitetura inicial do projeto.

stk apply plugin guijanuario/catalisa-t5-squad1/java-spring-boot-zuplacesapi@0.0.1

---


## 📚 Documentação com Swagger

A documentação da API é gerada automaticamente pelo Swagger, facilitando a compreensão e teste das suas rotas. Siga os passos abaixo para acessar a documentação:

1. Certifique-se de que a aplicação esteja em execução.
2. Abra um navegador da web.
3. Acesse o seguinte link: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).
4. Na página do Swagger, você verá uma lista de todas as operações disponíveis na sua API, incluindo os detalhes de entrada e saída para cada rota.
5. Clique em uma operação para abrir seus detalhes. Aqui, você pode testar a rota diretamente no navegador, fornecendo os parâmetros necessários e clicando em "Try it out!".

Certifique-se de que a aplicação esteja em execução para que o Swagger possa gerar a documentação corretamente. A documentação do Swagger é uma ferramenta valiosa para desenvolvedores e usuários da API, permitindo entender e testar facilmente as funcionalidades disponíveis.

---


### 🏢 Banco de Dados em Memória H2

A aplicação utiliza o banco de dados em memória H2 para armazenar os dados. Para acessar o console de administração do H2, siga os passos abaixo:

1. Acesse [http://localhost:8080/h2-console](http://localhost:8080/h2-console) no seu navegador.
2. No campo "JDBC URL", coloque `jdbc:h2:mem:dbtaxEasy` (que é a URL de conexão com o banco de dados H2 em memória).
3. No campo "Username", insira `root`.
4. No campo "Password", insira `admin123`.
5. Clique em "Connect" para acessar o console de administração do H2.

Lembre-se de que o banco de dados em memória H2 é reiniciado sempre que a aplicação é reiniciada.

---

### 🐳 Como executar o docker

- Necessário ter docker instalado em sua máquina.
- Você precisará ter o [Docker](https://www.docker.com/products/docker-desktop/) instalado no seu computador;
- Abra o terminal e navegue até a raiz do projeto;
- Execute o comando abaixo, para criar o banco de dados chamado stackspot, com usuário postgres e password, password.

```
docker-compose up
```

# 🌟 Fluxo de Trabalho com GitHub Actions e GitFlow

Este repositório utiliza o GitHub Actions para automatizar a integração contínua e os testes no projeto, seguindo o fluxo de trabalho com GitFlow. O GitHub Actions é configurado para executar diferentes etapas com base nos eventos de push para determinados ramos do repositório.

## Descrição do Fluxo de Trabalho

### Fluxo de Trabalho "Esteira de Testes"

Este fluxo de trabalho é acionado sempre que ocorre um push para os ramos "feature**" ou "develop".

**Passos:**

1. 🛠️ **Build (Compilação):** Nesta etapa, o código é compilado usando o JDK 11 e o Maven. O comando `mvn clean package -DskipTests=true` é usado para compilar o código, ignorando os testes unitários.

### Fluxo de Trabalho "Execute Testes Unitários"

Este fluxo de trabalho é acionado após a conclusão bem-sucedida do fluxo "Build".

**Passos:**

1. 🧪 **Set up JDK 11 (Configurar o JDK 11):** Configura o ambiente Java JDK 11.
2. 🧪 **Testes Unitários:** Executa os testes unitários do projeto usando o comando `mvn test`.

### Fluxo de Trabalho "Execute Testes de Integração"

Este fluxo de trabalho é acionado após a conclusão bem-sucedida do fluxo "Execute Testes Unitários".

**Passos:**

1. 🧪 **Set up JDK 11 (Configurar o JDK 11):** Configura o ambiente Java JDK 11.
2. 🧪 **Testes de Integração:** Executa os testes de integração do projeto usando o comando `mvn verify`.

### Fluxo de Trabalho "Relatório de Cobertura de Código"

Este fluxo de trabalho é acionado após a conclusão bem-sucedida do fluxo "Execute Testes de Integração".

**Passos:**

1. 🧪 **Set up JDK 11 (Configurar o JDK 11):** Configura o ambiente Java JDK 11.
2. 📊 **Relatório JaCoCo:** Gera um relatório de cobertura de código usando o JaCoCo com o comando `mvn jacoco:report`.

## Executando o Fluxo de Trabalho

Os fluxos de trabalho são executados automaticamente sempre que ocorre um push para os ramos "feature**" ou "develop". Os testes e a cobertura de código são avaliados para garantir a qualidade do código.

---

👨‍💻 Autores

Nome: Guilherme Januário <br>
Linkedin: https://www.linkedin.com/in/guilherme-janu%C3%A1rio/ <br> 
GitHub: https://github.com/guiijanuario

Nome: Ricardo dos Santos <br>
Linkedin: https://www.linkedin.com/in/ricardo-dos-santos-18006a239/ <br>
GitHub: https://github.com/RickZup <br>

Nome: Athos Caetano da Silva <br>
Linkedin: https://www.linkedin.com/in/athos-caetano-da-silva-26722020b/ <br>
GitHub: https://github.com/AthosDeveloper