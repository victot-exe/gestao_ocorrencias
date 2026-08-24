 CREATE TABLE midia_ocorrencia (
        id VARCHAR(255) NOT NULL,
        ocorrencia_id VARCHAR(255) NOT NULL,
        pessoa_id VARCHAR(255) NOT NULL,
        nome_original VARCHAR(255) NOT NULL,
        nome_armazenado VARCHAR(255) NOT NULL,
        content_type VARCHAR(100) NOT NULL,
        tamanho_bytes BIGINT NOT NULL,
        data_hora_upload TIMESTAMP NOT NULL,
        CONSTRAINT pk_midia_ocorrencia PRIMARY KEY (id),
        CONSTRAINT fk_midia_ocorrencia FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencia(id) ON DELETE CASCADE,
        CONSTRAINT fk_midia_pessoa FOREIGN KEY (pessoa_id) REFERENCES pessoa(id)
    );