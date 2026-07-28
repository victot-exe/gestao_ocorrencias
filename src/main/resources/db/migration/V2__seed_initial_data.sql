-- Inserindo Pessoas/Usuários de Teste (Senha padrão: 123456)
INSERT INTO pessoa (id, nome, cpf, senha, cargo_funcao) VALUES
('p-001', 'Administrador do Sistema', '11111111111', '$2a$10$e8O6.9IeM5Vv0R/764cZp.J77L4i908uXW0EIn.4dpt2Q71a39u8e', 'ROLE_ADMIN'),
('p-002', 'Gestor de Operações', '22222222222', '$2a$10$e8O6.9IeM5Vv0R/764cZp.J77L4i908uXW0EIn.4dpt2Q71a39u8e', 'ROLE_GESTOR'),
('p-003', 'Operador de Campo', '33333333333', '$2a$10$e8O6.9IeM5Vv0R/764cZp.J77L4i908uXW0EIn.4dpt2Q71a39u8e', 'ROLE_OPERADOR');

-- Inserindo Ocorrências Iniciais
INSERT INTO ocorrencia (id, descricao, modalidade_codigo, data_hora_ocorrencia, pessoa_id) VALUES
('o-001', 'Furto de equipamento na área externa', 'ROU', CURRENT_TIMESTAMP, 'p-003'),
('o-002', 'Manutenção preventiva no servidor de rede', 'MAN', CURRENT_TIMESTAMP, 'p-002');