INSERT INTO contatos (id, email, celular) VALUES
(1, 'pessoa1@example.com', '123456789'),
(2, 'pessoa2@example.com', '987654321'),
(3, 'pessoa3@example.com', '111111111');

INSERT INTO pessoas (active, id, nome, sobrenome, cpf, senha, papel, data_nascimento, contato_id) VALUES
(true, 1, 'Victor', 'Santos', '198.654.156-11', 'senha789', 'USUARIO', '1988-08-08', 1),
(true, 2, 'Victoria', 'Santos', '786.466.453-11', 'senha789', 'USUARIO', '1988-08-08', 2),
(true, 3, 'Beatriz', 'Santos', '111.232.333-11', 'senha789', 'USUARIO', '1988-08-08', 3);

INSERT INTO enderecos (numero, complemento, rua, bairro, cidade, estado, cep, pessoa_id) VALUES
('100', 'Apto 101', 'Rua A', 'Bairro 1', 'Cidade 1', 'ACRE', '12345-678', 1),
('200', 'Casa', 'Rua B', 'Bairro 2', 'Cidade 2', 'ALAGOAS', '98765-432', 2),
('300', 'Loja 1', 'Rua C', 'Bairro 3', 'Cidade 3', 'AMAPA', '13579-246', 3);