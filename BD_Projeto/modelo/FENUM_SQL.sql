CREATE DATABASE IF NOT EXISTS FENUM;
USE FENUM;

CREATE TABLE Estudante(
	nr_mecanografico INT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    curso VARCHAR(100) NOT NULL,
    universidade ENUM('Universidade do Minho','outro')
		NOT NULL DEFAULT 'outro',
	nome VARCHAR(45) NOT NULL
);
CREATE TABLE Empresa(
	id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR (45) NOT NULL,
    email VARCHAR(100) NOT NULL
);
CREATE TABLE Staff(
	id_staff INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    email VARCHAR(100),
    contacto VARCHAR(45) NOT NULL
);
CREATE TABLE Sala(
	nr_sala INT PRIMARY KEY,
    lota_max INT NOT NULL CHECK(lota_max > 0) 
);
CREATE TABLE AreasdeInteresse(
	areas_interesse VARCHAR(200)  NOT NULL,
    nr_mecanografico INT NOT NULL,
    PRIMARY KEY(areas_interesse,nr_mecanografico),
    FOREIGN KEY(nr_mecanografico) REFERENCES Estudante(nr_mecanografico)
);
CREATE TABLE Competencias(
	competencias VARCHAR(200) NOT NULL,
    nr_mecanografico INT NOT NULL,
    PRIMARY KEY(competencias,nr_mecanografico),
    FOREIGN KEY(nr_mecanografico) REFERENCES Estudante(nr_mecanografico)
);
CREATE TABLE Palestra(
	id_palestra INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(45) NOT NULL,
    data DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    nr_sala INT NOT NULL,
    FOREIGN KEY(nr_sala) REFERENCES Sala(nr_sala)
    
);
CREATE TABLE Organizacao(
	palestra_idPalestra INT NOT NULL,
    staff_idStaff INT NOT NULL,
    PRIMARY KEY(palestra_idPalestra,staff_idStaff),
    FOREIGN KEY (palestra_idPalestra) REFERENCES Palestra(id_palestra),
    FOREIGN KEY (staff_idStaff) REFERENCES Staff(id_staff)
    
);
CREATE TABLE Inscricao(
	nr_mecanografico INT NOT NULL,
    id_palestra INT NOT NULL,
    data_registo DATE NOT NULL,
    estado ENUM('Aceite', 'Em espera', 'Recusado') 
		NOT NULL DEFAULT 'Em espera',
	PRIMARY KEY(nr_mecanografico,id_palestra),
    FOREIGN KEY (nr_mecanografico) REFERENCES Estudante(nr_mecanografico),
    FOREIGN KEY (id_palestra) REFERENCES Palestra(id_palestra)
    
);
CREATE TABLE Orador(
	id_orador INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(45) NOT NULL,
    profissao VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL,
    linkedIn VARCHAR(45),
    id_empresa INT NOT NULL,
	FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);
CREATE TABLE Apresentacao(
	id_orador INT NOT NULL,
    palestra_idPalestra INT NOT NULL,
	PRIMARY KEY(id_orador,palestra_idPalestra),
    FOREIGN KEY (id_orador) REFERENCES Orador(id_orador),
    FOREIGN KEY (palestra_idPalestra) REFERENCES Palestra(id_palestra)
);
CREATE TABLE Banca(
	nr_banca INT AUTO_INCREMENT PRIMARY KEY,
    zona VARCHAR(45) NOT NULL,
    id_empresa INT NOT NULL,
    FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);
CREATE TABLE Contacto(
	contacto VARCHAR(45) NOT NULL,
    id_empresa INT NOT NULL,
    PRIMARY KEY(contacto,id_empresa),
	FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);

CREATE TABLE Setores (
    setores VARCHAR(50) NOT NULL,
    id_empresa INT NOT NULL,
    PRIMARY KEY(setores, id_empresa),
    FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);
CREATE TABLE Proposta(
	id_proposta INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(45) NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    tipo_trabalho VARCHAR(45) NOT NULL,
    perfil_procurado VARCHAR(100) NOT NULL,
    id_empresa INT NOT NULL,
	FOREIGN KEY (id_empresa) REFERENCES Empresa(id_empresa)
);


CREATE TABLE Candidatura(
	id_candidatura INT AUTO_INCREMENT PRIMARY KEY,
    data_candidatura DATE NOT NULL,
    estado ENUM('Em espera', 'Aceite', 'Recusada')
		NOT NULL DEFAULT 'Em espera',
	proposta_idProposta INT NOT NULL,
    nr_mecanografico INT NOT NULL,
	FOREIGN KEY (proposta_idProposta) REFERENCES Proposta(id_proposta),
    FOREIGN KEY (nr_mecanografico) REFERENCES Estudante(nr_mecanografico)
);

CREATE TABLE Avaliacao(
	id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
    resultado ENUM('Aceite', 'Não Aceite','A definir')
		NOT NULL DEFAULT 'A definir',
    local VARCHAR(45) ,
    data_Hora DATETIME,
    id_candidatura INT NOT NULL,
	FOREIGN KEY (id_candidatura) REFERENCES Candidatura(id_candidatura)
);




