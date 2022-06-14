CREATE TABLE rol(
	id_rol varchar(6) NOT NULL PRIMARY KEY,
	rol varchar(20)  NOT NULL,
	codi integer  NOT NULL);
CREATE TABLE treballador(
	nom varchar(15) NOT NULL,
	cognom1 varchar(15) NOT NULl,
	cognom2 varchar(15),
	dni varchar(9)  NOT NULL PRIMARY KEY,
	data_naixement date  NOT NULL,
	data_alta date,
	telefon integer  NOT NULL,
	id_rol varchar(6) NOT NULL REFERENCES rol(id_rol),
	img text);
CREATE TABLE categoria(
	id_cat varchar(6)  NOT NULL PRIMARY KEY,
	nom varchar(20)  NOT NULL);
CREATE TABLE producte(
	id_prod integer  NOT NULL PRIMARY KEY,
	nom varchar(20)  NOT NULL,
	descripcio text,
	cant integer  NOT NULL,
	stock integer  NOT NULL,
	id_cat varchar(6) REFERENCES categoria(id_cat),
	preu_venda numeric,
	preu_compra numeric,
	iva integer,
	img text,
	consumible boolean,
	vendible boolean);
CREATE TABLE pdv(
	id_pdv varchar(6) NOT NULL PRIMARY KEY,
	nom varchar(20)  NOT NULL,
	num_tpv integer);
CREATE TABLE tpv(
	id_tpv varchar(6) NOT NULL,
	id_pdv varchar(6) NOT NULL REFERENCES pdv(id_pdv),
	PRIMARY KEY (id_tpv, id_pdv));
CREATE TABLE empresa(
	nom varchar(40) NOT NULL,
	cif varchar(9) NOT NULL PRIMARY KEY,
	forma_juridica varchar(30),
	prefix varchar(4),
	telefon integer NOT NULL,
	web	varchar(50),
	carrer varchar(40),
	ciutat varchar(15),
	area varchar(15),
	zip_code integer);