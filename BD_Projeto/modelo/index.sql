-- Acelera pesquisa de palestras por data (Corrigido para "data")
CREATE INDEX idx_palestra_data ON Palestra(data);

-- Acelera contagem de candidaturas por estado (Correto)
CREATE INDEX idx_candidatura_estado ON Candidatura(estado);