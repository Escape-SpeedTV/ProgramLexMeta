# ⚖️ LexMeta - Sistema de Gestão Jurídica

Sistema web desenvolvido para automatizar o controle de processos, metas e relatórios de um escritório de advocacia.

## 📋 Sobre o Projeto

O LexMeta foi criado para substituir um processo manual de gestão jurídica, centralizando as informações em um único ambiente. O sistema permite:

- Cadastro e acompanhamento de processos jurídicos.
- Dashboard com indicadores em tempo real (processos concluídos, em andamento, pendentes, valores recebidos, etc.).
- Gestão de metas mensais.
- Gerenciamento de colaboradores (com controle de permissões por cargo).
- Upload de documentos e fotos de perfil.
- Importação de PDFs para preenchimento automático de dados.

## 🚀 Tecnologias Utilizadas

**Back-end:**
- Java 21
- Spring Boot
- Spring Data JPA
- Thymeleaf
- MySQL
- Apache PDFBox (extração de dados de PDF)

**Front-end:**
- HTML5
- CSS3
- JavaScript
- Chart.js (gráficos)

## 🖥️ Funcionalidades

### 🔐 Autenticação
- Login com e-mail e senha.
- Controle de acesso por cargo (T.I, ADM Geral, ADM, Advogado, Estagiário).

### 📊 Dashboard
- Indicadores de processos (total, concluídos, em andamento, pendentes).
- Gráfico de progresso mensal.
- Gráfico de evolução da meta.
- Valores financeiros (total recebido, média por processo, ticket médio).

### 📁 Processos
- Cadastro completo (dados gerais, partes envolvidas, documentos).
- Edição e exclusão de processos.
- Filtros por status, responsável e busca por número/cliente.

### 👥 Colaboradores
- Cadastro de usuários com cargos e permissões.
- Ativação/desativação de usuários.
- Edição de dados e cargos.

### ⚙️ Configurações
- Dados do escritório (nome, CNPJ, endereço, etc.).
- Upload de logo personalizada.
- Gerenciamento de metas mensais.

### 📄 Importação de PDF
- Upload de contratos e procurações.
- Extração automática de dados (nome, CPF, advogado, OAB).

## 🛠️ Como Executar o Projeto

### Pré-requisitos
- Java 21 (JDK)
- MySQL (ou XAMPP)
- Maven
- IntelliJ IDEA (recomendado)

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/lexmeta.git
   
2. Crie o Banco de dados:
   ```bash
   CREATE DATABASE lexmeta_db;
   
3. Configure o application.properties com suas credenciais:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/lexmeta_db
   spring.datasource.username=root
   spring.datasource.password=sua_senha
   
4. Execute o Projeto:
   ```bash
   mvn spring-boot:run

5. Acesse no navegador:
   ```bash
   http://localhost:8080

### 📌 Status do Projeto
- ✅ Versão 1.0 — Funcionalidades principais concluídas.

### 👨‍💻 Desenvolvedor
- William Nakano

-  Estudante de Análise e Desenvolvimento de Sistemas (ADS)
-  LinkedIn : **https://www.linkedin.com/in/william-oliveira-1745ba369/**

### 📄 Licença
Este projeto foi desenvolvido para uso exclusivo do cliente. É proibida a cópia, distribuição ou uso não autorizado.
