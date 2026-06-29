
INSERT INTO Sala (nr_sala, lota_max) VALUES
(101, 3),   
(102, 50),
(103, 100),
(104, 30);


INSERT INTO Empresa (nome, email) VALUES
('TechCorp Lda',       'rh@techcorp.pt'),       
('DataSoft SA',        'careers@datasoft.pt'),   
('CloudBase Lda',      'jobs@cloudbase.pt'),    
('InovaTech SA',       'rh@inovatech.pt'),      
('SemPropostas Lda',   'rh@sempropostas.pt');   


INSERT INTO Contacto (contacto, id_empresa) VALUES
('912000001', 1),
('912000002', 2),
('912000003', 3),
('912000004', 4);

INSERT INTO Setores (setores, id_empresa) VALUES
('Tecnologia', 1),
('Saúde', 2),
('Tecnologia', 3),
('Finanças', 4);


INSERT INTO Banca (zona, id_empresa) VALUES
('Zona A - Tech',    1),  
('Zona A - Data',    2),   
('Zona B - Cloud',   3),   
('Zona B - Inova',   4);   


INSERT INTO Staff (nome, email, contacto) VALUES
('Ana Costa',    'ana@fenum.pt',    '910000001'), 
('Rui Ferreira', 'rui@fenum.pt',    '910000002'),  
('Marta Lima',   'marta@fenum.pt',  '910000003');  


INSERT INTO Estudante (nr_mecanografico, email, curso, universidade, nome) VALUES
(10001, 'joao@alunos.uminho.pt',    'Eng. Informática',       'Universidade do Minho', 'João Silva'),
(10002, 'maria@alunos.uminho.pt',   'Ciências da Computação', 'Universidade do Minho', 'Maria Santos'),
(10003, 'pedro@alunos.uminho.pt',   'Eng. Informática',       'Universidade do Minho', 'Pedro Alves'),
(10004, 'ana@alunos.uminho.pt',     'Matemática',             'outro',                 'Ana Pereira'),
(10005, 'carlos@alunos.uminho.pt',  'Eng. Informática',       'Universidade do Minho', 'Carlos Mendes'),
(10006, 'sofia@alunos.uminho.pt',   'Ciências da Computação', 'Universidade do Minho', 'Sofia Costa'),
(10007, 'luis@alunos.uminho.pt',    'Eng. Informática',       'outro',                 'Luís Rodrigues'),
(10008, 'inês@alunos.uminho.pt',    'Design',                 'outro',                 'Inês Ferreira');


INSERT INTO AreasdeInteresse (areas_interesse, nr_mecanografico) VALUES
('Inteligência Artificial', 10001),
('Desenvolvimento Web',     10001),
('Base de Dados',           10002),
('Cibersegurança',          10003),
('Cloud Computing',         10004),
('Machine Learning',        10005);

INSERT INTO Competencias (competencias, nr_mecanografico) VALUES
('Python',  10001),
('Java',    10001),
('SQL',     10002),
('C++',     10003),
('Docker',  10004),
('React',   10005);


INSERT INTO Orador (nome, profissao, email, linkedIn, id_empresa) VALUES
('Carlos Ribeiro',  'Data Scientist',       'cribeiro@techcorp.pt',   '/in/cribeiro',  1),  
('Filipa Sousa',    'Cloud Architect',      'fsousa@datasoft.pt',     '/in/fsousa',    2),  
('Bruno Dias',      'Software Engineer',    'bdias@cloudbase.pt',     NULL,            3),  
('Leonor Pinto',    'Product Manager',      'lpinto@inovatech.pt',    '/in/lpinto',    4);  


INSERT INTO Palestra (titulo, data, hora_inicio, hora_fim, nr_sala) VALUES
('IA no Futuro do Trabalho',     '2026-04-15', '09:00:00', '10:00:00', 101),  
('Cloud Computing Avançado',     '2026-04-15', '10:30:00', '11:30:00', 102), 
('Base de Dados Modernas',       '2026-04-15', '14:00:00', '15:00:00', 103),  
('Cibersegurança Empresarial',   '2026-04-16', '09:00:00', '10:00:00', 104),  
('Dev Web com React',            '2026-04-16', '11:00:00', '12:00:00', 102);  


INSERT INTO Organizacao (palestra_idPalestra, staff_idStaff) VALUES
(1, 1), 
(1, 2),
(2, 1),
(3, 2),
(3, 3),
(4, 1),
(5, 3);

INSERT INTO Apresentacao (id_orador, palestra_idPalestra) VALUES
(1, 1), 
(1, 3), 
(2, 2),  
(2, 4),  
(3, 5),  
(4, 1);  


INSERT INTO Inscricao (nr_mecanografico, id_palestra, data_registo, estado) VALUES
(10001, 1, '2026-04-01', 'Em espera'),
(10002, 1, '2026-04-01', 'Em espera'),
(10003, 1, '2026-04-01', 'Em espera'),
(10001, 2, '2026-04-02', 'Em espera'),
(10004, 2, '2026-04-02', 'Em espera'),
(10005, 2, '2026-04-02', 'Em espera'),
(10002, 3, '2026-04-02', 'Em espera'),
(10003, 3, '2026-04-03', 'Em espera'),
(10006, 4, '2026-04-03', 'Em espera'),
(10001, 5, '2026-04-04', 'Em espera'),
(10005, 5, '2026-04-04', 'Em espera');


INSERT INTO Proposta (titulo, descricao, tipo_trabalho, perfil_procurado, id_empresa) VALUES
('Estágio Backend Java',      'Desenvolvimento de APIs REST.',          'Estágio',  'Aluno 3º ano Informática', 1), 
('Dev Frontend React',        'Criação de interfaces modernas.',        'Emprego',  'Licenciado em Informática', 1), 
('Data Engineer',             'Pipelines de dados em Python.',          'Emprego',  'Mestre em Informática',     2), 
('Cloud DevOps',              'Gestão de infraestrutura cloud.',        'Estágio',  'Aluno de Eng. Informática', 3), 
('Analista de Segurança',     'Auditoria e testes de penetração.',      'Emprego',  'Licenciado em Informática', 4), 
('ML Engineer',               'Modelos de machine learning.',           'Projeto',  'Mestre em CC ou IA',        2), 
('Gestor de Produto',         'Coordenação de projetos digitais.',      'Emprego',  'Qualquer área',             5); 


INSERT INTO Candidatura (data_candidatura, estado, proposta_idProposta, nr_mecanografico) VALUES
('2026-04-05', 'Em espera', 1, 10001),  
('2026-04-05', 'Em espera', 1, 10002),  
('2026-04-06', 'Em espera', 1, 10003),  
('2026-04-06', 'Em espera', 1, 10004),  
('2026-04-07', 'Em espera', 2, 10001),  
('2026-04-07', 'Em espera', 3, 10002),  
('2026-04-08', 'Em espera', 3, 10003),  
('2026-04-08', 'Em espera', 4, 10004),  
('2026-04-09', 'Em espera', 5, 10005),  
('2026-04-09', 'Em espera', 6, 10005),  
('2026-04-10', 'Em espera', 6, 10006);


UPDATE Candidatura SET estado = 'Aceite' WHERE id_candidatura = 1;
UPDATE Candidatura SET estado = 'Aceite' WHERE id_candidatura = 2;
UPDATE Candidatura SET estado = 'Aceite' WHERE id_candidatura = 6;
UPDATE Candidatura SET estado = 'Aceite' WHERE id_candidatura = 9;
UPDATE Candidatura SET estado = 'Aceite' WHERE id_candidatura = 3;


