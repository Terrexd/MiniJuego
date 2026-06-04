CREATE DATABASE IF NOT EXISTS penaltis_db;

USE penaltis_db;
-- Tabla Equipo
CREATE TABLE equipos(
    id_equipo INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    CONSTRAINT pk_equipos PRIMARY KEY (id_equipo)
);

-- Añadir los equipos a la lista
INSERT INTO equipos (nombre) VALUES ('Real Madrid');
INSERT INTO equipos (nombre) VALUES ('Barcelona');
INSERT INTO equipos (nombre) VALUES ('Atletico de Madrid');
INSERT INTO equipos (nombre) VALUES ('Sevilla');
INSERT INTO equipos (nombre) VALUES ('Betis');
INSERT INTO equipos (nombre) VALUES ('Athletic Club');
-- Tabla Jugadores
CREATE TABLE jugadores (
    id_jugador  INT         NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(50) NOT NULL UNIQUE,
    dorsal      INT,
    id_equipo   INT,
    CONSTRAINT pk_jugadores PRIMARY KEY (id_jugador),
    CONSTRAINT fk_jugadores_equipo FOREIGN KEY (id_equipo) 
        REFERENCES equipos(id_equipo)
        ON DELETE SET NULL ON UPDATE CASCADE
);
-- Tabla partidas
CREATE TABLE partidas (
    id_partida   INT              NOT NULL AUTO_INCREMENT,
    id_jugador1  INT              NOT NULL,
    id_jugador2  INT              NULL,
    modo         ENUM('CPU','PVP') NOT NULL,
    ganador      VARCHAR(50)      NULL,
    CONSTRAINT pk_partidas PRIMARY KEY (id_partida), 
    CONSTRAINT fk_partidas_j1 FOREIGN KEY (id_jugador1) REFERENCES jugadores(id_jugador) ON DELETE CASCADE ON UPDATE CASCADE, -- si se borra el jugador, se borran sus estadísticas también
    CONSTRAINT fk_partidas_j2 FOREIGN KEY (id_jugador2) REFERENCES jugadores(id_jugador) ON DELETE SET NULL ON UPDATE CASCADE -- si se borra el jugador 2 se pone a nulo y se actualiza solo el id jugador_2
);
-- Tabla estadisticas
CREATE TABLE estadisticas (
    id_estadistica   INT NOT NULL AUTO_INCREMENT,
    id_jugador       INT NOT NULL UNIQUE,
    partidas_jugadas INT NOT NULL DEFAULT 0,
    partidas_ganadas INT NOT NULL DEFAULT 0,
    goles_marcados   INT NOT NULL DEFAULT 0,
    goles_parados    INT NOT NULL DEFAULT 0,
    CONSTRAINT pk_estadisticas PRIMARY KEY (id_estadistica),
    CONSTRAINT fk_estadisticas_jugador FOREIGN KEY (id_jugador) REFERENCES jugadores(id_jugador)ON DELETE CASCADE ON UPDATE CASCADE -- si se borra el jugador, se borran sus estadísticas también
);