-- VISTA 1: Candidatos por Empresa (Nome ajustado para bater certo com o teste)
CREATE VIEW v_candidatos_por_empresa AS
SELECT 
    c.id_candidatura,
    c.estado,
    c.data_candidatura,
    p.titulo AS proposta,
    est.nome AS candidato,
    e.nome AS Empresa
FROM Candidatura c
JOIN Proposta p ON c.proposta_idProposta = p.id_proposta
JOIN Empresa e ON p.id_empresa = e.id_empresa
JOIN Estudante est ON c.nr_mecanografico = est.nr_mecanografico;


-- VISTA 2: Propostas Mais Populares
CREATE VIEW v_propostas_populares AS
SELECT 
    p.titulo AS Proposta, 
    emp.nome AS Empresa, 
    COUNT(c.id_candidatura) AS Total_Candidaturas
FROM Proposta p
JOIN Empresa emp ON p.id_empresa = emp.id_empresa
LEFT JOIN Candidatura c ON p.id_proposta = c.proposta_idProposta
GROUP BY 
    p.id_proposta, p.titulo, emp.nome
ORDER BY Total_Candidaturas DESC;


-- VISTA 3: Mapa de Bancas das Empresas
CREATE VIEW v_mapa_bancas AS
SELECT 
    b.zona AS Zona, 
    b.nr_banca AS Numero_Banca, 
    e.nome AS Empresa, 
    e.email AS Contacto_Empresa
FROM Banca b
JOIN Empresa e ON b.id_empresa = e.id_empresa
ORDER BY b.zona, b.nr_banca;

-- 1. Ver todos os candidatos de todas as empresas
SELECT * FROM v_candidatos_por_empresa;

-- 2. Filtrar candidatos apenas para a empresa 'TechCorp Lda'
SELECT * FROM v_candidatos_por_empresa WHERE Empresa = 'TechCorp Lda';

-- 3. Ver quais as propostas com mais candidaturas ordenadas
SELECT * FROM v_propostas_populares;

-- 4. Ver a distribuição das bancas pelo recinto (Mapa)
SELECT * FROM v_mapa_bancas;