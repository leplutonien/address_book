create database carnet_adresse default character set utf8 collate utf8_general_ci;
use carnet_adresse; 
drop table if exists personne;
Create table personne(id int(3) primary key , nom varchar(150) not null, 
prenom varchar(150) not null,tel_domicile varchar(20), tel_bureau varchar(20), 
tel_portable varchar(20) not null, email varchar(100) not null, remarque Text);

DROP procedure if exists rechercher;
DELIMITER $$
CREATE PROCEDURE rechercher(IN  input VARCHAR(255))
begin
SELECT * FROM personne WHERE (nom LIKE CONCAT('%',input,'%') OR prenom LIKE CONCAT('%',input,'%') ) ORDER BY nom asc;
END

DROP procedure if exists nombreAbonnes;
DELIMITER $$
CREATE PROCEDURE nombreAbonnes(OUT nb INTEGER)
BEGIN
 SELECT COUNT(*) INTO nb FROM personne;
END