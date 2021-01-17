CREATE DATABASE IF NOT EXISTS tahiti;

USE tahiti;

CREATE TABLE coordinates (
	id_coordinates INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	latitude FLOAT NOT NULL,
	longitude FLOAT NOT NULL
)ENGINE=InnnoDB;

CREATE TABLE transport (
	id_transport INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	type ENUM('bus', 'boat') NOT NULL,
	price INT NOT NULL
)ENGINE=InnnoDB;

CREATE TABLE site (
	id_site INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	type ENUM('historic', 'activity') NOT NULL,
	price INT NOT NULL,
	id_coordinates INT NOT NULL,
	FOREIGN KEY (id_coordinates) REFERENCES coordinates(id_coordinates) ON DELETE CASCADE
)ENGINE=InnnoDB;

CREATE TABLE hotel (
	id_hotel INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	price INT NOT NULL,
	beach_name VARCHAR(60) NOT NULL,
	id_coordinates INT NOT NULL,
	FOREIGN KEY (id_coordinates) REFERENCES coordinates(id_coordinates) ON DELETE CASCADE
)ENGINE=InnnoDB;

CREATE TABLE ride (
	id_ride INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	departure_site INT NOT NULL,
	arrival_site INT NOT NULL,
	id_transport INT NOT NULL,
	FOREIGN KEY (departure_site) REFERENCES site(id_site) ON DELETE CASCADE,
	FOREIGN KEY (arrival_site) REFERENCES site(id_site) ON DELETE CASCADE,
	FOREIGN KEY (id_transport) REFERENCES transport(id_transport) ON DELETE CASCADE
)ENGINE=InnnoDB;