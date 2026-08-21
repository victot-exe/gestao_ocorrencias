# 📋 Planejamento Completo de Endpoints - Gestão de Ocorrências

Este documento apresenta o mapeamento consolidado de todos os endpoints necessários para um sistema robusto, completo e escalável de **Gestão de Ocorrências**.

---

## 🏷️ Legenda de Status

- `[x] IMPLEMENTADO`: Endpoint já desenvolvido e operacional no código backend.
- `[ ] PENDENTE`: Endpoint planejado e recomendado para futuras implementações.

---

## 📊 Resumo Executivo de Progresso

| Módulo | Endpoints Implementados | Endpoints Pendentes | Total |
| :--- | :---: | :---: | :---: |
| **1. Autenticação & Perfil (`/auth`)** | 1 | 3 | 4 |
| **2. Gestão de Pessoas (`/pessoas`)** | 4 | 3 | 7 |
| **3. Gestão de Ocorrências (`/ocorrencias`)** | 4 | 6 | 10 |
| **4. Tratativas & Histórico (`/ocorrencias/{id}/tratativas`)** | 0 | 3 | 3 |
| **5. Anexos & Evidências (`/ocorrencias/{id}/anexos`)** | 0 | 3 | 3 |
| **6. Dashboard & Relatórios (`/ocorrencias/estatisticas`)** | 0 | 3 | 3 |
| **7. Domínios & Auxiliares (`/dominios` ou `/ocorrencias/...`)** | 0 | 2 | 2 |
| **8. Diagnóstico & Testes (`/test`)** | 1 | 0 | 1 |
| **TOTAL GERAL** | **10** | **23** | **33** |

---

## 🔑 1. Autenticação e Perfil de Usuário (`/auth`)

- `[x] IMPLEMENTADO` **`POST /auth/login`**
  - **Descrição**: Autentica o usuário via CPF e senha, retornando o token JWT.
  - **Permissão**: Pública

- `[ ] PENDENTE` **`POST /auth/refresh-token`**
  - **Descrição**: RENOVA o token JWT expirado enviando um Refresh Token válido.
  - **Permissão**: Pública

- `[ ] PENDENTE` **`GET /auth/me`**
  - **Descrição**: Retorna os dados cadastrais e perfil de acesso do usuário atualmente autenticado.
  - **Permissão**: Qualquer usuário autenticado

- `[ ] PENDENTE` **`PATCH /auth/alterar-senha`**
  - **Descrição**: Permite que o próprio usuário logado altere sua senha informando a senha antiga e a nova.
  - **Permissão**: Qualquer usuário autenticado

---

## 👤 2. Gestão de Pessoas / Usuários (`/pessoas`)

- `[x] IMPLEMENTADO` **`POST /pessoas`**
  - **Descrição**: Cadastra uma nova pessoa no sistema.
  - **Permissão**: Pública / `ADMIN`

- `[x] IMPLEMENTADO` **`PUT /pessoas`**
  - **Descrição**: Atualiza os dados cadastrais de uma pessoa existente (Nome, CPF, Perfil).
  - **Permissão**: `ADMIN` ou Próprio Usuário

- `[x] IMPLEMENTADO` **`GET /pessoas/{id}`**
  - **Descrição**: Busca os detalhes completos de uma pessoa pelo ID (UUID).
  - **Permissão**: Autenticado (`ADMIN` ou Próprio Usuário)

- `[x] IMPLEMENTADO` **`GET /pessoas/retornar-paginado`**
  - **Descrição**: Lista paginada de todas as pessoas cadastradas.
  - **Permissão**: `hasRole('ADMIN')`

- `[ ] PENDENTE` **`PATCH /pessoas/{id}/status`**
  - **Descrição**: Inativa ou reativa o acesso de um usuário no sistema (Inativação lógica).
  - **Permissão**: `hasRole('ADMIN')`

- `[ ] PENDENTE` **`PATCH /pessoas/{id}/reset-senha`**
  - **Descrição**: Redefinição forçada de senha de um usuário efetuada por um administrador.
  - **Permissão**: `hasRole('ADMIN')`

- `[ ] PENDENTE` **`DELETE /pessoas/{id}`**
  - **Descrição**: Exclusão física ou lógica definitiva do cadastro de uma pessoa.
  - **Permissão**: `hasRole('ADMIN')`

---

## 🚨 3. Gestão de Ocorrências (`/ocorrencias`)

- `[x] IMPLEMENTADO` **`POST /ocorrencias`**
  - **Descrição**: Registra uma nova ocorrência no sistema.
  - **Permissão**: `hasRole('OPERADOR')`

- `[x] IMPLEMENTADO` **`GET /ocorrencias/{id}`**
  - **Descrição**: Retorna os detalhes completos de uma ocorrência específica pelo ID.
  - **Permissão**: `hasRole('OPERADOR')` (própria) / `GESTOR` / `ADMIN`

- `[x] IMPLEMENTADO` **`GET /ocorrencias/retornar-paginado/{idPessoaAbertura}`**
  - **Descrição**: Retorna a lista paginada de ocorrências associadas a uma pessoa de abertura específica.
  - **Permissão**: `hasRole('OPERADOR')` / `GESTOR` / `ADMIN`

- `[x] IMPLEMENTADO` **`GET /ocorrencias/retornar-paginado`**
  - **Descrição**: Retorna a lista paginada com todas as ocorrências do sistema.
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

- `[x] IMPLEMENTADO` **`GET /ocorrencias/minhas`**
  - **Descrição**: Retorna as ocorrências registradas pelo próprio usuário logado (extraindo o ID via Token JWT).
  - **Permissão**: `hasRole('OPERADOR')`

- `[x] IMPLEMENTADO` **`PUT /ocorrencias/{id}`**
  - **Descrição**: Permite editar os dados de uma ocorrência (descrição, modalidade, etc.).
  - **Permissão**: `OPERADOR` (autor) / `GESTOR` / `ADMIN`

- `[ ] PENDENTE` **`PATCH /ocorrencias/{id}/status`**
  - **Descrição**: Altera a situação/status da ocorrência (`ABERTA`, `EM_ANDAMENTO`, `RESOLVIDA`, `CANCELADA`).
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

- `[ ] PENDENTE` **`PATCH /ocorrencias/{id}/atribuir`**
  - **Descrição**: Atribui um responsável (operador ou gestor) para condução e resolução da ocorrência.
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

- `[x] IMPLEMENTADO` **`GET /ocorrencias/filtrar`**
  - **Descrição**: Consulta avançada com filtros múltiplos (intervalo de datas, modalidade, status, pessoa abertura e responsável).
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

- `[ ] PENDENTE` **`DELETE /ocorrencias/{id}`**
  - **Descrição**: Cancela ou remove uma ocorrência do sistema.
  - **Permissão**: `hasRole('ADMIN')`

---

## 📝 4. Tratativas e Histórico de Acompanhamento (`/ocorrencias/{id}/tratativas`)

- `[ ] PENDENTE` **`POST /ocorrencias/{id}/tratativas`**
  - **Descrição**: Adiciona uma nova tratativa/parecer/andamento na resolução da ocorrência.
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('OPERADOR')`

- `[ ] PENDENTE` **`GET /ocorrencias/{id}/tratativas`**
  - **Descrição**: Lista o histórico completo de tratativas registradas para a ocorrência em ordem cronológica.
  - **Permissão**: Usuários com permissão de visualização da ocorrência

- `[ ] PENDENTE` **`DELETE /ocorrencias/{id}/tratativas/{idTratativa}`**
  - **Descrição**: Remove um registro de tratativa (ex: em caso de lançamento indevido).
  - **Permissão**: Autor da tratativa ou `hasRole('ADMIN')`

---

## 📎 5. Anexos e Evidências (`/ocorrencias/{id}/anexos`)

- `[ ] PENDENTE` **`POST /ocorrencias/{id}/anexos`**
  - **Descrição**: Realiza o upload de arquivos/fotos/evidências vinculadas à ocorrência.
  - **Permissão**: Autor da ocorrência / `GESTOR`

- `[ ] PENDENTE` **`GET /ocorrencias/{id}/anexos`**
  - **Descrição**: Retorna a lista e os links/metadados dos arquivos anexados à ocorrência.
  - **Permissão**: Usuários com acesso à ocorrência

- `[ ] PENDENTE` **`DELETE /ocorrencias/{id}/anexos/{idAnexo}`**
  - **Descrição**: Exclui um anexo vinculado à ocorrência.
  - **Permissão**: Autor da ocorrência ou `hasRole('ADMIN')`

---

## 📊 6. Dashboard, Métricas e Relatórios (`/ocorrencias/estatisticas`)

- `[ ] PENDENTE` **`GET /ocorrencias/estatisticas/resumo`**
  - **Descrição**: Retorna totalizadores executivos (total de ocorrências ativas, resolvidas no mês, tempo médio de atendimento).
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

- `[ ] PENDENTE` **`GET /ocorrencias/estatisticas/por-modalidade`**
  - **Descrição**: Quantidade de ocorrências agrupadas por modalidade (`ROU`, `ACI`, `MAN`, `OUT`).
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

- `[ ] PENDENTE` **`GET /ocorrencias/relatorio/exportar`**
  - **Descrição**: Exporta o relatório consolidado de ocorrências em formatos PDF ou CSV/Excel.
  - **Permissão**: `hasRole('GESTOR')` / `hasRole('ADMIN')`

---

## 🏷️ 7. Domínios e Utilitários (`/dominios` ou `/ocorrencias/...`)

- `[ ] PENDENTE` **`GET /ocorrencias/modalidades`**
  - **Descrição**: Retorna a lista de modalidades com códigos e descrições para exibição em seletores no frontend.
  - **Permissão**: Qualquer usuário autenticado

- `[ ] PENDENTE` **`GET /ocorrencias/status-disponiveis`**
  - **Descrição**: Retorna os possíveis status de uma ocorrência para preenchimento em telas de filtro e edição.
  - **Permissão**: Qualquer usuário autenticado

---

## 🛠️ 8. Diagnóstico e Testes (`/test`)

- `[x] IMPLEMENTADO` **`GET /test/hello-world`**
  - **Descrição**: Endpoint simples para validação do status e execução do servidor.
  - **Permissão**: Pública
