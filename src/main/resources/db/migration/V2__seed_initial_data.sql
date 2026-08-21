-- 1. Inserir Pessoas (Passwords geradas com BCrypt para o valor: '123456')
-- Usando o padrão GUID/UUID para os IDs
INSERT INTO pessoa (id, nome, cpf, senha, cargo_funcao) VALUES
('a1b2c3d4-e5f6-4a1b-8c9d-0123456789ab', 'Administrador do Sistema', '83908221087', '$2a$10$.vdJrVayImsm8KCDTBvbxOmJqff6.Y1BebUJ3kD13c5dhmGx/bpmu', 'ADMIN'),
('b2c3d4e5-f6a1-4b2c-9d0e-123456789abc', 'Gestor de Operações', '31478207038', '$2a$10$.vdJrVayImsm8KCDTBvbxOmJqff6.Y1BebUJ3kD13c5dhmGx/bpmu', 'GESTOR'),
('c3d4e5f6-a1b2-4c3d-a1f2-23456789abcd', 'Operador de Campo', '65324508085', '$2a$10$.vdJrVayImsm8KCDTBvbxOmJqff6.Y1BebUJ3kD13c5dhmGx/bpmu', 'OPERADOR');

-- 2. Inserir Ocorrências
-- Ocorrência 1: Criada recentemente, ainda sem tratativa (Status: CRI)
INSERT INTO ocorrencia (id, descricao, modalidade_codigo, data_hora_ocorrencia, status_atual, pessoa_id) VALUES
('d4e5f6a1-b2c3-4d4e-b2a3-3456789abcde', 'Furto de equipamento na área externa', 'ROU', CURRENT_TIMESTAMP, 'CRI', 'c3d4e5f6-a1b2-4c3d-a1f2-23456789abcd');

-- Ocorrência 2: Já está em resolução (Status: EMR) devido a uma tratativa
INSERT INTO ocorrencia (id, descricao, modalidade_codigo, data_hora_ocorrencia, status_atual, pessoa_id) VALUES
('e5f6a1b2-c3d4-4e5f-c3b4-456789abcdef', 'Pneu da viatura furou na rodovia', 'ACI', CURRENT_TIMESTAMP, 'EMR', 'c3d4e5f6-a1b2-4c3d-a1f2-23456789abcd');

-- 3. Inserir Tratativas (Histórico)
-- Tratativa para a Ocorrência 2, feita pelo Gestor (b2c3d4e5...)
INSERT INTO tratativa_ocorrencia (id, descricao, status_codigo, data_hora_tratativa, ocorrencia_id, pessoa_id) VALUES
('f6a1b2c3-d4e5-4f6a-d4c5-56789abcdef0', 'Passou super bonder no pneu para tentar vedar provisoriamente.', 'EMR', CURRENT_TIMESTAMP, 'e5f6a1b2-c3d4-4e5f-c3b4-456789abcdef', 'b2c3d4e5-f6a1-4b2c-9d0e-123456789abc');