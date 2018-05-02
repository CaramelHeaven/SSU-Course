IF EXISTS (SELECT * FROM SYS.DATABASES WHERE NAME='GymDb')
DROP DATABASE GymDb
CREATE DATABASE GymDb
USE GymDb

CREATE TABLE [Client Directory]
(
	id_client int NOT NULL CONSTRAINT PK_client PRIMARY KEY, 
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	gym_membership_id int NULL,
)
GO

CREATE TABLE [Trainer Directory]
(
	id_trainer int NOT NULL CONSTRAINT PK_trainer PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
)
GO

CREATE TABLE [Gym Membership]
(
	id_gym_membership int NOT NULL CONSTRAINT PK_gym PRIMARY KEY,
	day_of_start datetime NOT NULL,
	day_of_end datetime NOT NULL,
	price_of_membership int NOT NULL,
	sale int NOT NULL,
)
GO

CREATE TABLE Sale
(
	id_sale int NOT NULL CONSTRAINT PK_sale PRIMARY KEY, 
	procent_of_sale int NOT NULL,
)
GO

CREATE TABLE [Individual Schedule]
(
	id_individual_work int NOT NULL CONSTRAINT PK_ind_work PRIMARY KEY,
	time_start time NOT NULL,
	time_end time NOT NULL,
	client_id int NULL,
	trainer_id int NULL
)
GO

CREATE TABLE ClientTrace
(
	id_group_client int NOT NULL CONSTRAINT PK_id_grp_client PRIMARY KEY,
	client_id int NOT NULL,
	client_date_start date NULL,
	client_date_end date NULL
)
GO

CREATE TABLE [Group work]
(
	id_group_work int NOT NULL CONSTRAINT PK_id_grp_work PRIMARY KEY,
	id_group int NOT NULL,
	name_group varchar(50) NOT NULL,
	get_people int NOT NULL
)
GO

--CREATE TABLE [People Quantity in the group]
--(
--	id int NOT NULL CONSTRAINT PK_id_PQ_in_TG PRIMARY KEY,
--	client int NOT NULL
--)
--GO

ALTER TABLE [Gym Membership]
--WITH CHECK 
WITH CHECK ADD CONSTRAINT [Gym_fk] FOREIGN KEY (sale) 
REFERENCES Sale(id_sale)
ON UPDATE CASCADE
GO

ALTER TABLE [Client Directory]
WITH CHECK ADD CONSTRAINT [Client_fk] FOREIGN KEY (gym_membership_id)
REFERENCES [Gym Membership]([id_gym_membership])
ON UPDATE CASCADE
GO

ALTER TABLE [Individual Schedule]
WITH CHECK ADD CONSTRAINT [IS_fk0_client_id] FOREIGN KEY (client_id)
REFERENCES [Client Directory](id_client)
ON UPDATE CASCADE
GO

ALTER TABLE [Individual Schedule]
WITH CHECK ADD CONSTRAINT [IS_fk1_trainer_id] FOREIGN KEY (trainer_id)
REFERENCES [Trainer Directory](id_trainer)
ON UPDATE CASCADE
GO

--ALTER TABLE [Group Schedule]
--WITH CHECK ADD CONSTRAINT [GS_fk0_group_id] FOREIGN KEY (group_id)
--REFERENCES [Group work](id_group_work)
--ON UPDATE CASCADE
--GO

ALTER TABLE [Group work]
WITH CHECK ADD CONSTRAINT [GW_fk_get_people] FOREIGN KEY (get_people)
REFERENCES ClientTrace(id_group_client)
ON UPDATE CASCADE
GO

ALTER TABLE ClientTrace
WITH CHECK ADD CONSTRAINT [CT_fk_client_id] FOREIGN KEY (client_id)
REFERENCES [Client Directory](id_client)
ON UPDATE CASCADE
GO

--------------------------------------------------------------------------------------------------------------------
insert Sale(id_sale, procent_of_sale) values (1, 1);
insert Sale(id_sale, procent_of_sale) values (2, 4);
insert Sale(id_sale, procent_of_sale) values (3, 9);
insert Sale(id_sale, procent_of_sale) values (4, 12);
insert Sale(id_sale, procent_of_sale) values (5, 16);

--------------------------------------------------------------------------------------------------------------------

SELECT * FROM [Group Schedule]
DELETE FROM [Group Schedule]
DELETE FROM Place
SELECT * FROM [Group work] 
SELECT * FROM [Place] 
SELECT * FROM [Kind of Sport]
SELECT * FROM [Trainer Directory]
SELECT * FROM [Client Directory]
SELECT * FROM [Gym Membership] where id_gym_membership = 500
DELETE [Gym Membership]
SELECT * FROM ClientTrace
SELECT * FROM [Sale]


--ЖИЗНЕННАЯ КОНСТРУКЦИЯ
set language english;
--------------------------------------------------------------------------------------------------
--Начальная работа с представлением.

SELECT * FROM [Client Directory] where first_name = 'Elon'

GO
DROP VIEW myView 

GO
CREATE VIEW myView 
	AS SELECT id_client, first_name, last_name FROM [Client Directory]

GO
SELECT * FROM myView

--------------------------------------------------------------------------------------------------
--Создание простого обновления

SELECT * FROM [Client Directory] where id_client = 500


UPDATE myView SET first_name = 'Elon' WHERE id_client = 500

GO
SELECT * FROM myView where id_client = 500

--------------------------------------------------------------------------------------------------
--Смотрим сколько человек находятся в определенной группе по названию последнего

select * from [Client Directory] join [Individual Schedule] on [Client Directory].id_client = [Individual Schedule].client_id join [Trainer Directory]
on [Individual Schedule].trainer_id = [Trainer Directory].id_trainer 

GO
CREATE VIEW FindAllClients
	AS select * from [Group work] 
		inner join [Client Directory] 
			on [Group work].get_people = [Client Directory].id_client 


GO
DROP VIEW FindAllClients

SELECT * FROM FindAllClients where name_group = 'Group 1'

--------------------------------------------------------------------------------------------------
--Выделяем клиентов, у которых срок конца абонемента (выбирается нами)

SET NUMERIC_ROUNDABORT OFF;
SET ANSI_PADDING, ANSI_WARNINGS, CONCAT_NULL_YIELDS_NULL,
ARITHABORT, QUOTED_IDENTIFIER, ANSI_NULLS ON;
GO
CREATE VIEW ClientWithGymMembership  WITH SCHEMABINDING 
	AS SELECT day_of_start, day_of_end, 
		dbo.[Gym Membership].price_of_membership from dbo.[Gym Membership] 
			join dbo.[Client Directory] on dbo.[Client Directory].id_client = id_gym_membership 
				where day_of_end < '2025-5-11'

GO
DROP VIEW ClientWithGymMembership

SELECT * FROM ClientWithGymMembership
----------------------------------------------
--Создание индекса

CREATE NONCLUSTERED INDEX FIND_MEM ON [Gym Membership](day_of_start, day_of_end)

DROP INDEX FIND_MEM ON [Gym Membership]

select day_of_start, day_of_end, 
		[Gym Membership].price_of_membership from [Gym Membership] 
			join [Client Directory] on [Client Directory].id_client = id_gym_membership 
				where day_of_end < '2025-5-11'

