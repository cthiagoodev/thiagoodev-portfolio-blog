Este é o repositório do meu **Portfólio Pessoal e Blog de Tecnologia**, um projeto que decidi desenvolver do zero para ser meu laboratório de aprendizado e minha vitrine profissional. Meu principal objetivo é praticar e consolidar conhecimentos em **Desenvolvimento Full-Stack** e **Infraestrutura Própria (Self-Hosted)**, cobrindo o ciclo completo: do código à produção.

Estou animado em usar este projeto para expor meus trabalhos, compartilhar artigos técnicos e, claro, me desafiar com novas tecnologias.

---

## 🎯 Funcionalidade Central: Sincronização Dinâmica com GitHub

Um dos recursos mais importantes do projeto é a **sincronização automática** do meu portfólio com meus projetos no GitHub, garantindo que a seção de trabalho esteja sempre atualizada.

* **API Pública do GitHub:** O backend utiliza a API pública do GitHub para buscar informações sobre meus repositórios.
* **Processo de Sincronização:** Criei um **Schedule** (tarefa agendada) no Spring Boot que é executado periodicamente.
* **Filtro Inteligente:** Esse processo filtra meus projetos pela *tag* **"portfolio"** e, em seguida, os sincroniza, expondo as informações (como nome, descrição e tecnologias utilizadas) diretamente na interface do meu portfólio.

---

## 🛠️ Stack Tecnológica & Arquitetura Atual

O projeto foi construído, inicialmente, em uma arquitetura **monolítica (Monorepo)** para simplificar o desenvolvimento e o *deployment*.

### 💻 Desenvolvimento Full-Stack

| Categoria | Tecnologia | Detalhe |
| :--- | :--- | :--- |
| **Backend** | **Java 25** | A versão mais recente do Java para garantir performance e recursos modernos. |
| **Framework** | **Spring Boot** | Utilizado para criar serviços robustos e eficientes. |
| **Frontend Rendering** | **Thymeleaf** | Escolhido para renderização de HTML no servidor (SSR), aproveitando a força do Spring. |
| **Estilização (CSS)** | **Pico.css** | Um *framework* CSS minimalista e sem *boilerplate* para *design* simples e limpo ([https://picocss.com/](https://picocss.com/)). |
| **Interatividade** | **HTMX** | Adiciona interatividade dinâmica sem a complexidade de *frameworks* JavaScript pesados, permitindo focar no backend. |
| **Autenticação** | **JWT** | Usado na área de blog para gerenciar autenticação e permitir que usuários logados **favoritem artigos**. |

### 💾 Armazenamento Self-Hosted (Infraestrutura Própria)

| Serviço | Tecnologia | Modo de Uso em Produção |
| :--- | :--- | :--- |
| **Banco de Dados** | **PostgreSQL** | Utilizo um container Docker com **volumes persistentes** para garantir a durabilidade dos dados. |
| **Storage de Objetos** | **MinIO** | O MinIO é usado como infraestrutura própria (**Self-Hosted**) para armazenamento de imagens e *assets* grandes. Rodando em container e persistido por volumes ([https://www.min.io/](https://www.min.io/)). |

---

## 🚀 Estratégia de Infraestrutura e Deployment

A infraestrutura é um dos pontos focais do meu estudo neste projeto, onde estou fazendo o **deploy manual** e gerenciando a VM do zero para entender o passo a passo completo.

| Componente | Tecnologia | Propósito |
| :--- | :--- | :--- |
| **Servidor** | **Contabo VM** | Minha máquina virtual rodando Linux para *hosting* de Produção ([https://contabo.com/](https://contabo.com/)). |
| **Containerização** | **Docker** | Uso o Docker para empacotar o Spring Boot e todos os serviços de suporte (Postgres, MinIO), garantindo isolamento e portabilidade. |
| **Proxy Reverso** | **Nginx** | Gerencia o tráfego HTTP/HTTPS na VM e roteia as requisições para o meu container Java. |
| **CI/CD** | **GitHub Actions** | Automatiza os processos de *build*, testes e entrega contínua para a minha VM. |

---

## 🗺️ Visão de Futuro e Próximos Passos

Este projeto está apenas começando! Tenho grandes planos para evoluir a arquitetura e as funcionalidades:

1.  **Refatoração para Microserviços:** O plano imediato é evoluir o Monorepo para um modelo **Multirepo/Microservices**. Quero isolar o Portfólio e o Blog em serviços independentes para estudar e praticar a comunicação entre serviços.
2.  **Otimização:** Implementação de estratégias de *caching* (provavelmente com Redis) para melhorar a performance e reduzir a latência de acesso aos artigos e portfólio.
3.  **Funcionalidades do Blog:** Melhorar a área de autenticação e adicionar recursos como comentários e *likes* nos artigos.
4.  **Admin CMS:** Desenvolver uma interface administrativa simples, mas poderosa, para gerenciar o conteúdo do Blog e Portfólio.