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

------------------------------------------------------------------------------------------------------------

CREATE NONCLUSTERED INDEX FIND_MEM ON [Gym Membership](day_of_start, day_of_end)

DROP INDEX FIND_MEM ON [Gym Membership]

--Выделяем клиентов, у которых срок конца абонемента меньше 2018 года
SET STATISTICS IO ON
SET STATISTICS TIME ON

select day_of_start, day_of_end, first_name, last_name, price_of_membership 
	from [Gym Membership] 
		join [Client Directory] 
			on [Client Directory].id_client = id_gym_membership
where day_of_end < '2021-5-23'  AND day_of_start > '2015-10-3'

------------------------------------------------------------------------------------------------------------

CREATE NONCLUSTERED INDEX FIND_SALE ON [Gym Membership](sale, price_of_membership)

DROP INDEX FIND_SALE ON [Gym Membership]

SET STATISTICS IO ON
SET STATISTICS TIME ON
--Выводим клиентов где скидка больше двух и по крайней мере цена их больше 49279
select sale, price_of_membership from [Gym Membership] 
	where [Gym Membership].sale > 2 
		AND price_of_membership > 
			ANY (select price_of_membership from [Gym Membership] where
[Gym Membership].price_of_membership > 10000)

------------------------------------------------------------------------------------------------------------

CREATE NONCLUSTERED INDEX FIND_PEOPLE ON [Group Work](get_people)

DROP INDEX FIND_PEOPLE ON [Group Work]

SET STATISTICS IO ON
SET STATISTICS TIME ON
--Смотрим сколько человек в одной групповой тренировке Inner Join
select name_group FROM [Group work] 
	inner join [Client Directory] 
		on [Group work].get_people = [Client Directory].id_client 
			where get_people > 1

SELECT * FROM [Group work]

------------------------------------------------------------------------------------------------------------

CREATE NONCLUSTERED INDEX FIND_INDV ON [Client Directory](id_client)

DROP INDEX FIND_INDV ON [Client Directory]

SET STATISTICS IO ON
SET STATISTICS TIME ON
--Выводим список клиентов у которых есть индивидуальное занятие и их тренеров
select id_client FROM [Client Directory] 
	join [Individual Schedule] 
		on [Client Directory].id_client = [Individual Schedule].client_id 
	join [Trainer Directory]
		on [Individual Schedule].trainer_id = [Trainer Directory].id_trainer 
			where id_client > 300 AND gym_membership_id > 302

SELECT * FROM [Client Directory]
------------------------------------------------------------------------------------------------------------

CREATE NONCLUSTERED INDEX FINDDD ON [Individual Schedule](time_start, time_end)

DROP INDEX FINDDD ON [Individual Schedule]

SET STATISTICS IO ON
SET STATISTICS TIME ON
--Выводим время старта и время окончания индивидуального занятия где есть определенные тренера и начало строго больше 10 утра
select time_start, time_end FROM [Individual Schedule] 
	join [Trainer Directory] 
		on [Trainer Directory].id_trainer > 10 where time_start > '10:00'

SELECT * FROM [Individual Schedule]

------------------------------------------------------------------------------------------------------------
