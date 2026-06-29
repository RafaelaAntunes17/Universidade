
-- RM01
UPDATE Candidatura
SET estado = 'Aceite'
WHERE id_candidatura = 1
  AND proposta_idProposta IN (
    SELECT id_proposta 
    FROM Proposta 
    WHERE id_empresa = 1 
);

UPDATE Candidatura
SET estado = 'Recusada'
WHERE id_candidatura = 2
  AND proposta_idProposta IN (
    SELECT id_proposta 
    FROM Proposta
    WHERE id_empresa = 1  
);

SELECT id_candidatura, estado, proposta_idProposta 
FROM Candidatura 
WHERE proposta_idProposta IN (
    SELECT id_proposta FROM Proposta WHERE id_empresa = 1
);


-- RM02
SELECT 
    e.nr_mecanografico,
    e.nome,
    e.curso,
    e.universidade
FROM Estudante e

LEFT JOIN Candidatura c 
    ON e.nr_mecanografico = c.nr_mecanografico
WHERE c.id_candidatura IS NULL;

-- RM03
SELECT 
    o.nome          AS orador,
    emp.nome        AS empresa,
    COUNT(*)        AS total_palestras
FROM Orador o
INNER JOIN Apresentacao a 
    ON o.id_orador = a.id_orador
INNER JOIN Empresa emp 
    ON o.id_empresa = emp.id_empresa
GROUP BY 
    o.id_orador, o.nome, emp.nome
HAVING COUNT(*) > 1
ORDER BY total_palestras DESC;


-- RM04
INSERT INTO Estudante (nr_mecanografico, email, curso, universidade, nome)
VALUES (10009, 'novo@alunos.uminho.pt', 'Eng. Informática', 'Universidade do Minho', 'Novo Estudante');

UPDATE Estudante
SET email = 'atualizado@alunos.uminho.pt',
    curso = 'Ciências da Computação'
WHERE nr_mecanografico = 10009;

DELETE FROM Estudante
WHERE nr_mecanografico = 10009;


-- RM05
UPDATE Avaliacao
SET resultado = 'Aceite'
WHERE id_avaliacao = 4
  AND data_Hora <= NOW()
  AND resultado = 'A definir';



-- RM06
INSERT INTO Banca (zona, id_empresa)
SELECT 'Zona C - Nova', 5
WHERE NOT EXISTS (
    SELECT 1 
    FROM Banca 
    WHERE id_empresa = 5
);


SELECT 
    e.nome      AS empresa,
    b.nr_banca,
    b.zona
FROM Empresa e
INNER JOIN Banca b 
    ON e.id_empresa = b.id_empresa
WHERE e.id_empresa = 1;


-- RM07
SELECT 
    p.hora_inicio               AS hora,
    p.titulo                    AS evento,
    'Palestra'                  AS tipo,
    CAST(s.nr_sala AS CHAR)     AS sala_ou_local
FROM Inscricao i
INNER JOIN Palestra p 
    ON i.id_palestra = p.id_palestra
INNER JOIN Sala s 
    ON p.nr_sala = s.nr_sala
WHERE i.nr_mecanografico = 10001
  AND p.data = '2026-04-15'
  AND i.estado = 'Aceite'

UNION


SELECT 
    TIME(av.data_Hora)          AS hora,
    CONCAT('Speed Interview - ', emp.nome) AS evento,
    'Speed Interview'           AS tipo,
    av.local                    AS sala_ou_local
FROM Candidatura c 
INNER JOIN Avaliacao av 
    ON c.id_candidatura = av.id_candidatura
INNER JOIN Proposta pr 
    ON c.proposta_idProposta = pr.id_proposta
INNER JOIN Empresa emp 
    ON pr.id_empresa = emp.id_empresa
WHERE c.nr_mecanografico = 10001
  AND DATE(av.data_Hora) = '2026-04-16'
ORDER BY hora;


-- RM08
SELECT 
    e.nome                      AS estudante,
    av.data_Hora,
    av.local,
    av.resultado
FROM Empresa emp
INNER JOIN Proposta p 
    ON emp.id_empresa = p.id_empresa
INNER JOIN Candidatura c 
    ON p.id_proposta = c.proposta_idProposta
INNER JOIN Avaliacao av 
    ON c.id_candidatura = av.id_candidatura
INNER JOIN Estudante e 
    ON c.nr_mecanografico = e.nr_mecanografico
WHERE emp.id_empresa = 1
ORDER BY av.data_Hora;


-- RM09
SELECT 
    p.titulo                    AS proposta,
    emp.nome                    AS empresa,
    COUNT(c.id_candidatura)     AS total_candidaturas
FROM Proposta p
INNER JOIN Empresa emp 
    ON p.id_empresa = emp.id_empresa
INNER JOIN Candidatura c 
    ON p.id_proposta = c.proposta_idProposta
GROUP BY 
    p.id_proposta, p.titulo, emp.nome
ORDER BY total_candidaturas DESC
LIMIT 10;


-- RM10
SELECT 
    e.id_empresa,
    e.nome,
    e.email,
    COUNT(p.id_proposta) AS nr_propostas_sem_candidaturas
FROM Empresa e
INNER JOIN Proposta p 
    ON e.id_empresa = p.id_empresa
WHERE NOT EXISTS (
    SELECT 1 
    FROM Candidatura c 
    WHERE c.proposta_idProposta = p.id_proposta
)
GROUP BY e.id_empresa, e.nome, e.email
ORDER BY e.nome;