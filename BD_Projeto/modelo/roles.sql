-- Criar as Funções (Roles)
CREATE ROLE 'staff';
CREATE ROLE 'empresa';
CREATE ROLE 'estudante';


-- RC01
GRANT ALL PRIVILEGES ON FENUM.* TO 'staff';


-- RC02
GRANT SELECT ON FENUM.v_candidatos_por_empresa TO 'empresa';


-- RC03
GRANT SELECT ON FENUM.Palestra TO 'estudante';
GRANT SELECT ON FENUM.Proposta TO 'estudante';
GRANT SELECT, INSERT ON FENUM.Inscricao TO 'estudante';
GRANT SELECT, INSERT ON FENUM.Candidatura TO 'estudante';


-- RC04
GRANT ALL PRIVILEGES ON FENUM.Banca TO 'staff';
GRANT SELECT ON FENUM.v_mapa_bancas TO 'empresa';
GRANT SELECT ON FENUM.v_mapa_bancas TO 'estudante';


CREATE USER 'organizacao'@'localhost' IDENTIFIED BY 'fenum_org_2026';
CREATE USER 'empresa_user'@'localhost' IDENTIFIED BY 'fenum_emp_2026'; 
CREATE USER 'estudante_user'@'localhost' IDENTIFIED BY 'fenum_est_2026';


GRANT 'staff' TO 'organizacao'@'localhost';
GRANT 'empresa' TO 'empresa_user'@'localhost';
GRANT 'estudante' TO 'estudante_user'@'localhost';


SET DEFAULT ROLE ALL TO 'organizacao'@'localhost';
SET DEFAULT ROLE ALL TO 'empresa_user'@'localhost';
SET DEFAULT ROLE ALL TO 'estudante_user'@'localhost';


FLUSH PRIVILEGES;