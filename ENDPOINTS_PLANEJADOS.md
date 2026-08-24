# 📚 Documentação da API REST - Gestão de Ocorrências (Guia do Frontend)

Este documento contém a especificação completa de todos os endpoints disponíveis no backend para consumo e integração com a aplicação **Frontend**.

---

## 🔐 1. Autenticação e Padrões Globais

### 🌐 Base URL
`http://localhost:8080`

### 🔑 Cabeçalho de Autenticação (Header)
Para todos os endpoints protegidos, envie o token JWT retornado no login:
```http
Authorization: Bearer <seu_token_jwt_aqui>
```

### 👤 Perfis de Acesso (Roles)
* `ADMIN`: Acesso total ao sistema e exclusões administrativas.
* `GESTOR`: Visualização global de ocorrências, pessoas e edição de tratativas.
* `OPERADOR`: Abertura e edição de ocorrências próprias, envio de tratativas e upload de anexos.

### 🏷️ Enums / Domínios do Sistema

#### **Status da Ocorrência (`StatusOcorrencia`)**
* `CRI`: Criada
* `EMR`: Em resolução
* `RES`: Resolvida
* `NRE`: Não Resolvida

#### **Modalidade da Ocorrência (`TipoModalidade`)**
* `ROU`: Roubo
* `ACI`: Acidente
* `MAN`: Manutenção
* `OUT`: Outro

#### **Perfil de Usuário (`PerfilUsuario`)**
* `ADMIN`
* `GESTOR`
* `OPERADOR`

---

### 📄 Padrão de Paginação Spring Data
Endpoints paginados aceitam via Query Parameters:
* `page` (int, default `0`): Índice da página (inicia em 0).
* `size` (int, default `15`): Quantidade de itens por página.
* `sort` (string): Campo e direção de ordenação (ex: `dataHoraOcorrencia,desc` ou `nome,asc`).

#### Estrutura de Retorno Paginado:
```json
{
  "content": [ ... ],
  "totalElements": 50,
  "totalPages": 4,
  "size": 15,
  "number": 0,
  "first": true,
  "last": false,
  "empty": false
}
```

---

## 🚪 2. Módulo de Autenticação (`/auth`)

### 1. Login do Usuário
* **Rota:** `POST /auth/login`
* **Permissão:** Pública
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "cpf": "12345678901",
  "password": "minhasenha123"
}
```

#### Response Body (`200 OK`):
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwMSIsImlkIjoiZjRhMWIyY... ",
  "type": "Bearer",
  "role": "OPERADOR"
}
```

---

## 👤 3. Módulo de Pessoas / Usuários (`/pessoas`)

### 1. Cadastrar Nova Pessoa
* **Rota:** `POST /pessoas`
* **Permissão:** Pública / `ADMIN`
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "nome": "João da Silva",
  "cpf": "12345678901",
  "perfilUsuario": "OPERADOR",
  "password": "senhaSegura123",
  "confirmPassword": "senhaSegura123"
}
```

#### Response Body (`201 Created`):
```json
{
  "id": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
  "nome": "João da Silva",
  "cpf": "12345678901",
  "perfil": "OPERADOR"
}
```

---

### 2. Editar Pessoa
* **Rota:** `PUT /pessoas`
* **Permissão:** `GESTOR` ou Próprio `OPERADOR`
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "id": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
  "nome": "João da Silva Atualizado",
  "cpf": "12345678901",
  "perfil": "OPERADOR"
}
```

#### Response Body (`200 OK`):
```json
{
  "id": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
  "nome": "João da Silva Atualizado",
  "cpf": "12345678901",
  "perfil": "OPERADOR"
}
```

---

### 3. Buscar Pessoa por ID
* **Rota:** `GET /pessoas/{id}`
* **Permissão:** `GESTOR` ou Próprio `OPERADOR`

#### Response Body (`200 OK`):
```json
{
  "id": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
  "nome": "João da Silva",
  "cpf": "12345678901",
  "perfil": "OPERADOR"
}
```

---

### 4. Listar Pessoas Paginado
* **Rota:** `GET /pessoas/retornar-paginado?page=0&size=15&sort=nome,asc`
* **Permissão:** `GESTOR` ou `ADMIN`

#### Response Body (`200 OK`):
```json
{
  "content": [
    {
      "id": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
      "nome": "João da Silva",
      "cpf": "12345678901",
      "perfil": "OPERADOR"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 15,
  "number": 0
}
```

---

## 🚨 4. Módulo de Ocorrências (`/ocorrencias`)

### 1. Criar Ocorrência
* **Rota:** `POST /ocorrencias`
* **Permissão:** `OPERADOR` (ou superior)
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "tipoModalidade": "ROU",
  "descricao": "Furto de cabos elétricos no galpão norte.",
  "dataHoraOcorrencia": "2026-08-24T10:30:00"
}
```

#### Response Body (`201 Created`):
```json
{
  "id": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
  "nomePessoa": "Operador de Campo",
  "codigoModalidade": "ROU",
  "dataHoraOcorrencia": "2026-08-24T10:30:00"
}
```

---

### 2. Buscar Ocorrência por ID (Detalhada)
* **Rota:** `GET /ocorrencias/{id}`
* **Permissão:** `OPERADOR`

#### Response Body (`200 OK`):
```json
{
  "id": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
  "nomePessoa": "Operador de Campo",
  "codigoModalidade": "ROU",
  "dataHoraOcorrencia": "2026-08-24T10:30:00",
  "descricao": "Furto de cabos elétricos no galpão norte."
}
```

---

### 3. Listar Minhas Ocorrências (Usuário Logado)
* **Rota:** `GET /ocorrencias/minhas?dataInicial=2026-08-01T00:00:00&dataFinal=2026-08-31T23:59:59&statusAtual=CRI&page=0&size=15&sort=dataHoraOcorrencia,desc`
* **Permissão:** `OPERADOR`
* **Query Parameters Opcionais:** `dataInicial`, `dataFinal`, `statusAtual`

#### Response Body (`200 OK`):
```json
{
  "content": [
    {
      "id": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
      "nomePessoa": "Operador de Campo",
      "codigoModalidade": "ROU",
      "dataHoraOcorrencia": "2026-08-24T10:30:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 15,
  "number": 0
}
```

---

### 4. Listar Todas as Ocorrências (Visão Gestor)
* **Rota:** `GET /ocorrencias?page=0&size=15&sort=dataHoraOcorrencia,desc`
* **Permissão:** `GESTOR` ou `ADMIN`

#### Response Body (`200 OK`):
```json
{
  "content": [
    {
      "id": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
      "nomePessoa": "Operador de Campo",
      "codigoModalidade": "ROU",
      "dataHoraOcorrencia": "2026-08-24T10:30:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 15,
  "number": 0
}
```

---

### 5. Listar Ocorrências Filtradas por Pessoa (Visão Gestor)
* **Rota:** `GET /ocorrencias/por-pessoa?pessoaAberturaId={idPessoa}&statusAtual=EMR&dataInicial=...&dataFinal=...&page=0&size=15`
* **Permissão:** `GESTOR` ou `ADMIN`

#### Response Body (`200 OK`):
Retorna a lista paginada de ocorrências filtradas pela pessoa e critérios opcionais.

---

### 6. Editar Ocorrência
* **Rota:** `PUT /ocorrencias`
* **Permissão:** `OPERADOR` (autor)
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "id": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
  "tipoModalidade": "ROU",
  "descricao": "Descrição atualizada dos fatos.",
  "dataHoraOcorrencia": "2026-08-24T11:00:00"
}
```

#### Response Body (`200 OK`):
```json
{
  "id": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
  "nomePessoa": "Operador de Campo",
  "codigoModalidade": "ROU",
  "dataHoraOcorrencia": "2026-08-24T11:00:00"
}
```

---

### 7. Excluir Ocorrência
* **Rota:** `DELETE /ocorrencias/{id}`
* **Permissão:** `ADMIN`

#### Response Body (`204 No Content`):
Sem corpo de resposta.

---

## 📝 5. Módulo de Tratativas da Ocorrência (`/tratativas`)

### 1. Adicionar Nova Tratativa
* **Rota:** `POST /tratativas`
* **Permissão:** `OPERADOR` (ou superior)
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "ocorrenciaId": "d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde",
  "dataHoraTratativa": "2026-08-24T14:00:00",
  "statusOcorrencia": "EMR",
  "descricao": "Equipe de segurança acionada no local para verificação."
}
```

#### Response Body (`201 Created`):
Retorna o ID da tratativa criada:
```json
"f6a1b2c3-d4e5-4f6a-d4c5-56789abcdef0"
```

---

### 2. Listar Tratativas de uma Ocorrência (Paginado)
* **Rota:** `GET /tratativas?idOcorrencia={idOcorrencia}&page=0&size=15&sort=dataHoraTratativa,asc`
* **Permissão:** `OPERADOR` (ou superior)

#### Response Body (`200 OK`):
```json
{
  "content": [
    {
      "id": "f6a1b2c3-d4e5-4f6a-d4c5-56789abcdef0",
      "descricao": "Equipe de segurança acionada no local para verificação.",
      "dataHoraTratativa": "2026-08-24T14:00:00",
      "nomePessoaTratativa": "Operador de Campo",
      "statusOcorrencia": "EMR"
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "size": 15,
  "number": 0
}
```

---

### 3. Editar Tratativa
* **Rota:** `PUT /tratativas`
* **Permissão:** `GESTOR`
* **Content-Type:** `application/json`

#### Request Body:
```json
{
  "idTratativaOcorrencia": "f6a1b2c3-d4e5-4f6a-d4c5-56789abcdef0",
  "descricao": "Texto corrigido da tratativa.",
  "dataHoraTratativa": "2026-08-24T14:15:00"
}
```

#### Response Body (`200 OK`):
Retorna o ID da tratativa editada.

---

### 4. Excluir Tratativa
* **Rota:** `DELETE /tratativas/{id}`
* **Permissão:** `GESTOR`

#### Response Body (`204 No Content`):
Sem corpo de resposta.

---

## 📎 6. Módulo de Mídias e Anexos (`/ocorrencias/{idOcorrencia}/anexos`)

### 1. Upload de Mídia / Anexo
* **Rota:** `POST /ocorrencias/{idOcorrencia}/anexos`
* **Permissão:** `OPERADOR` (ou superior)
* **Content-Type:** `multipart/form-data`

#### Form-Data Parameters:
* `arquivo` (File / Binary): Arquivo da imagem, PDF ou vídeo.
  * Formatos aceitos: `image/png`, `image/jpeg`, `image/jpg`, `image/webp`, `application/pdf`, `video/mp4`, `video/webm`, `video/quicktime`.
  * Tamanho máximo: 50MB.

#### Response Body (`201 Created`):
```json
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "nomeOriginal": "foto_evidencia.png",
  "contentType": "image/png",
  "tamanhoBytes": 204850,
  "dataHoraUpload": "2026-08-24T15:20:00",
  "idPessoaUpload": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
  "nomePessoaUpload": "Operador de Campo"
}
```

---

### 2. Listar Mídias da Ocorrência
* **Rota:** `GET /ocorrencias/{idOcorrencia}/anexos`
* **Permissão:** `OPERADOR` (ou superior)

#### Response Body (`200 OK`):
```json
[
  {
    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "nomeOriginal": "foto_evidencia.png",
    "contentType": "image/png",
    "tamanhoBytes": 204850,
    "dataHoraUpload": "2026-08-24T15:20:00",
    "idPessoaUpload": "a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab",
    "nomePessoaUpload": "Operador de Campo"
  }
]
```

---

### 3. Visualizar / Baixar Mídia
* **Rota:** `GET /ocorrencias/{idOcorrencia}/anexos/{idMidia}/download`
* **Permissão:** `OPERADOR` (ou superior)

#### Headers de Resposta:
* `Content-Type`: MIME type original do arquivo (ex: `image/png`, `application/pdf`).
* `Content-Disposition`: `inline; filename="foto_evidencia.png"`

#### Response Body (`200 OK`):
Binário / Stream do arquivo.

---

### 4. Excluir Mídia
* **Rota:** `DELETE /ocorrencias/{idOcorrencia}/anexos/{idMidia}`
* **Permissão:** `OPERADOR` (ou superior)

#### Response Body (`204 No Content`):
Sem corpo de resposta.

---

## 🛠️ 7. Diagnóstico & Testes (`/test`)

### 1. Teste de Conectividade
* **Rota:** `GET /test/hello-world`
* **Permissão:** Pública
* **Response Body (`200 OK`):** `"Hello World"`
