## 🚀 Portfólio de Projetos - Foco em Backend Java e Infraestrutura Self-Hosted

Este projeto atua como meu **laboratório prático** e **vitrine profissional**, com o objetivo principal de consolidar o conhecimento em **Desenvolvimento Backend com Java** e **Infraestrutura Própria (Self-Hosted)**. Ele foi desenvolvido do zero para demonstrar a capacidade de gerenciar o ciclo completo: do código à produção, incluindo a **sincronização dinâmica de projetos**.

---

### 🎯 Funcionalidade Central: Sincronização Dinâmica com GitHub

Um recurso essencial é a **sincronização automática** para manter a seção de projetos sempre atualizada, espelhando meus repositórios no GitHub.

* **Fonte de Dados:** Utiliza a **API Pública do GitHub** para buscar informações sobre meus projetos.
* **Processo de Sincronização:** Um `Schedule` (tarefa agendada) no **Spring Boot** é executado periodicamente para iniciar a busca.
* **Filtro:** A rotina filtra os repositórios que contêm a *tag* **"portfolio"** e, em seguida, expõe as informações essenciais (como nome, descrição e tecnologias) na interface do portfólio.

---

### 🛠️ Stack Tecnológica & Arquitetura (Monolítica para Estudo)

A arquitetura inicial é **monolítica (Monorepo)**, escolhida para simplificar a fase de desenvolvimento e focar no domínio das tecnologias principais.

#### 💻 Desenvolvimento

| Categoria | Tecnologia | Detalhe |
| :--- | :--- | :--- |
| **Backend** | **Java 25** | Utilizado para serviços backend de alta performance. |
| **Framework** | **Spring Boot** | Criação de serviços robustos e eficientes. |
| **Frontend Rendering** | **Thymeleaf** | Escolhido para **Server-Side Rendering (SSR)**. |
| **Interatividade** | **HTMX** | Adiciona dinamismo e interatividade com foco no backend, minimizando a dependência de JavaScript. |
| **Autenticação (Estudo)** | **JWT** | Implementação de **JSON Web Tokens** para fins de estudo e prática de autenticação. |

#### 💾 Armazenamento Self-Hosted (Infraestrutura Própria)

| Serviço | Tecnologia | Modo de Uso em Produção |
| :--- | :--- | :--- |
| **Banco de Dados** | **PostgreSQL** | Container Docker com **volumes persistentes** para garantir a durabilidade dos dados. |
| **Storage de Objetos** | **MinIO** | Solução **Self-Hosted** para armazenamento de imagens e *assets* grandes, rodando em container com persistência via volumes. |

---

### 🚀 Estratégia de Infraestrutura e Deployment

Este é o ponto focal de estudo do projeto: o **deployment manual** e a gestão da infraestrutura do zero.

| Componente | Tecnologia | Propósito |
| :--- | :--- | :--- |
| **Servidor** | **Contabo VM** | Máquina virtual Linux dedicada ao *hosting* de Produção. |
| **Containerização** | **Docker** | Empacota o Spring Boot e todos os serviços de suporte (Postgres, MinIO) para isolamento e portabilidade. |
| **Proxy Reverso** | **Nginx** | Gerencia o tráfego e faz o roteamento das requisições para o container Java. |
| **CI/CD** | **GitHub Actions** | Automatiza os processos de *build*, testes e entrega contínua (CD) para a VM. |

---

### 🗺️ Visão de Futuro e Próximos Passos

O plano de evolução visa aprimorar a arquitetura e otimizar a performance, aprofundando o aprendizado:

1.  **Refatoração para Microserviços:** Evoluir a arquitetura monolítica para um modelo **Multirepo/Microservices** para praticar a comunicação e o isolamento de serviços.
2.  **Otimização de Performance:** Implementação de estratégias de **caching (ex: Redis)** para melhorar a velocidade e reduzir a latência de acesso ao portfólio.
3.  **Desenvolvimento Admin CMS:** Construir uma interface administrativa simples para gerenciar o conteúdo.