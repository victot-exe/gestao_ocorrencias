-- Tabela de Pessoas / Usuários
CREATE TABLE pessoa (
    id VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    senha VARCHAR(255),
    cargo_funcao VARCHAR(255),
    CONSTRAINT pk_pessoa PRIMARY KEY (id),
    CONSTRAINT uk_pessoa_cpf UNIQUE (cpf)
);

-- Tabela de Ocorrências
CREATE TABLE ocorrencia (
    id VARCHAR(255) NOT NULL,
    descricao VARCHAR(500) NOT NULL,
    modalidade_codigo VARCHAR(3) NOT NULL,
    data_hora_ocorrencia TIMESTAMP NOT NULL,
    pessoa_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ocorrencia PRIMARY KEY (id),
    CONSTRAINT fk_ocorrencia_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
);