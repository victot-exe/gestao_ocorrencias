-- 1. Tabela de Pessoas / Usuários
CREATE TABLE pessoa (
    id VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    senha VARCHAR(255),
    cargo_funcao VARCHAR(50),
    CONSTRAINT pk_pessoa PRIMARY KEY (id),
    CONSTRAINT uk_pessoa_cpf UNIQUE (cpf)
);

-- 2. Tabela de Ocorrências
CREATE TABLE ocorrencia (
    id VARCHAR(255) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    modalidade_codigo VARCHAR(3) NOT NULL,
    data_hora_ocorrencia TIMESTAMP NOT NULL,
    status_atual VARCHAR(3) NOT NULL,
    pessoa_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ocorrencia PRIMARY KEY (id),
    CONSTRAINT fk_ocorrencia_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);

-- 3. Tabela de Tratativas da Ocorrência (Histórico)
CREATE TABLE tratativa_ocorrencia (
    id VARCHAR(255) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    status_codigo VARCHAR(3) NOT NULL,
    data_hora_tratativa TIMESTAMP NOT NULL,
    ocorrencia_id VARCHAR(255) NOT NULL,
    pessoa_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tratativa_ocorrencia PRIMARY KEY (id),
    CONSTRAINT fk_tratativa_ocorrencia FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencia(id),
    CONSTRAINT fk_tratativa_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);