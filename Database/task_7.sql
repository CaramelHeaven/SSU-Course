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

SELECT * FROM [Group work] 

SELECT * FROM [Trainer Directory]
SELECT * FROM [Client Directory]
SELECT * FROM [Gym Membership] where id_gym_membership = 500
DELETE [Gym Membership]
SELECT * FROM ClientTrace
SELECT * FROM [Sale]


--ЖИЗНЕННАЯ КОНСТРУКЦИЯ
set language english;


DELETE FROM [Gym Membership]
SELECT * FROM [Gym Membership]

SELECT * FROM ClientTrace where id_group_client = 339

--Working with procedures

--first


EXEC Count_People

GO
CREATE PROCEDURE Count_People AS
SELECT COUNT(id_client) From [Client Directory]

DROP PROCEDURE Count_People

---------------------------------------------------------------------------------------------------------

DROP proc checkName

---------------------------------------------------------------------------------------------------------
-- Normal procedures
---------------------------------------------------------------------------------------------------------

SELECT * FROM [Trainer Directory] inner join [Individual Schedule] on [Trainer Directory].id_trainer = [Individual Schedule].trainer_id
inner join [Client Directory] on [Client Directory].id_client = [Individual Schedule].client_id

DROP PROC getTrainerClientINDV

GO
create proc getTrainerClientINDV @firstName as varchar(20), @lastName as varchar(20)
  as
	select [Client Directory].first_name, [Client Directory].last_name FROM [Client Directory]
	inner join [Individual Schedule] on [Client Directory].id_client = [Individual Schedule].client_id
	inner join [Trainer Directory] on [Trainer Directory].id_trainer = [Individual Schedule].trainer_id where [Trainer Directory].first_name = @firstName AND
	[Trainer Directory].last_name = @lastName

EXEC getTrainerClientINDV 'Christoper', 'Juan'

---------------------------------------------------------------------------------------------------------

GO
CREATE PROCEDURE checkName @firstName varchar(20), @lastName as varchar(20)
	AS 
		IF @firstName 
			in (select [Client Directory].first_name from [Client Directory]) 
				AND @lastName 
					in (select [Client Directory].last_name from [Client Directory])
			PRINT 'This person is exists'
		else
			PRINT 'This person is not exists'


EXEC checkName 'Elon', 'Lieven'

SELECT * FROM [Client Directory] where first_name = 'Elon' AND last_name = 'Lieven'

---------------------------------------------------------------------------------------------------------

SELECT * FROM [Gym Membership]


DROP proc discount

GO
CREATE PROCEDURE discount @idSale INT
	AS
		IF (@idSale = 1) 
				SELECT id_gym_membership, sale, price_of_membership =  price_of_membership - price_of_membership * 1 / 100 FROM [Gym Membership] where sale = @idSale
			ELSE IF (@idSale = 2)
				SELECT id_gym_membership, sale, price_of_membership =  price_of_membership - price_of_membership * 4 / 100 FROM [Gym Membership] where sale = @idSale
			ELSE IF (@idSale = 3)
				SELECT id_gym_membership, sale, price_of_membership =  price_of_membership - price_of_membership * 9 / 100 FROM [Gym Membership] where sale = @idSale
			ELSE IF (@idSale = 4)
				SELECT id_gym_membership, sale, price_of_membership =  price_of_membership - price_of_membership * 12 / 100 FROM [Gym Membership] where sale = @idSale
			ELSE IF (@idSale = 5)
				SELECT id_gym_membership, sale, price_of_membership =  price_of_membership - price_of_membership * 16 / 100 FROM [Gym Membership] where sale = @idSale
			ELSE
				SELECT id_gym_membership, sale, price_of_membership FROM [Gym Membership] 

EXEC discount 3

---------------------------------------------------------------------------------------------------------
--day_of_start datetime NOT NULL,
--	day_of_end datetime NOT NULL,

SELECT * FROM [Gym Membership]

drop proc countingSumClient

GO
CREATE PROCEDURE countingSumClient @dayOfStart datetime, @dayOfEnd datetime
	AS IF
		@dayOfStart in (select [Gym Membership].day_of_start from [Gym Membership])
			DECLARE @totalPrice INT
			SELECT id_gym_membership, day_of_start, day_of_end FROM [Gym Membership] where day_of_start >= @dayOfStart AND day_of_end < @dayOfEnd
		--		 @dayOfEnd in (select [Gym Membership].day_of_end from [Gym Membership] where day_of_end < @dayOfEnd)


EXEC countingSumClient '2011-10-20 08:00:00', '2023-5-8 22:00:00'

select [Gym Membership].day_of_start from [Gym Membership] where day_of_start > '2011-10-20 08:00:00'
