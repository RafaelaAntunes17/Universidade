DELIMITER $$
CREATE TRIGGER verificar_lotacao_palestra
BEFORE INSERT ON Inscricao
FOR EACH ROW
BEGIN
    DECLARE v_lotacao_maxima INT;
    DECLARE v_inscritos_atuais INT;

   
    SELECT s.lota_max INTO v_lotacao_maxima
    FROM Palestra p
    JOIN Sala s ON p.nr_sala = s.nr_sala
    WHERE p.id_palestra = NEW.id_palestra;

    SELECT COUNT(*) INTO v_inscritos_atuais
    FROM Inscricao
    WHERE id_palestra = NEW.id_palestra 
      AND estado = 'Aceite';

    IF v_inscritos_atuais < v_lotacao_maxima THEN
        SET NEW.estado = 'Aceite';
    ELSE
        SET NEW.estado = 'Recusado'; 
    END IF;
END$$
DELIMITER ;


DELIMITER $$

CREATE TRIGGER trg_criar_avaliacao_entrevista
AFTER UPDATE ON Candidatura
FOR EACH ROW
BEGIN
    -- Se a candidatura foi atualizada para 'Aceite'
    IF NEW.estado = 'Aceite' AND OLD.estado != 'Aceite' THEN
        
        
        INSERT INTO Avaliacao (resultado, local, data_Hora, id_candidatura)
        VALUES ('A definir', NULL, NULL, NEW.id_candidatura);
        
    END IF;
END$$

DELIMITER ;

-- FUNÇÃO: Contar candidaturas aceites de um estudante
DELIMITER $$
CREATE FUNCTION contar_candidaturas_aceites(p_nr_mecanografico INT)
RETURNS INT
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_total INT;
    SELECT COUNT(*) INTO v_total
    FROM Candidatura
    WHERE nr_mecanografico = p_nr_mecanografico
    AND estado = 'Aceite';
    RETURN v_total;
END$$
DELIMITER ;

-- PROCEDIMENTO: Listar propostas disponíveis por setor
DELIMITER $$
CREATE PROCEDURE listar_propostas_por_setor(IN p_setor VARCHAR(50))
BEGIN
    SELECT 
        p.id_proposta,
        p.titulo,
        p.descricao,
        p.tipo_trabalho,
        p.perfil_procurado,
        e.nome AS empresa
    FROM Proposta p
    JOIN Empresa e ON p.id_empresa = e.id_empresa
    JOIN Setores s ON e.id_empresa = s.id_empresa
    WHERE s.setores = p_setor
    ORDER BY e.nome;
END$$
DELIMITER ;

-- Demostração trigger de lotação SELECT 
SELECT 
    av.id_avaliacao,
    av.resultado,
    av.local,
    av.data_Hora,
    c.estado AS estado_candidatura,
    c.id_candidatura
FROM Avaliacao av
JOIN Candidatura c ON av.id_candidatura = c.id_candidatura;


INSERT INTO Inscricao (nr_mecanografico, id_palestra, data_registo) VALUES (10004, 1, CURDATE());

SELECT * FROM Inscricao WHERE id_palestra = 1; 