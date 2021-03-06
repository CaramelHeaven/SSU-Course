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

--Added missing tables for used procedures
CREATE TABLE [Group Schedule]
(
	id_group_schedule int NOT NULL CONSTRAINT Pk_grp_schedule PRIMARY KEY,
	group_id int NOT NULL,
	place_id int NOT NULL,
	trainer_id int NOT NULL,
	time_start time NOT NULL,
	time_end time NOT NULL,
	day_of_week varchar(20) NOT NULL,
	type_of_sport_id int NOT NULL
)

CREATE TABLE [Kind of Sport]
(
	id_kind int NOT NULL CONSTRAINT PK_id_kind PRIMARY KEY,
	name_kind varchar(50) NOT NULL
)

CREATE TABLE Place
(
	id_place int NOT NULL CONSTRAINT PK_place PRIMARY KEY,
	name_place varchar(50) NOT NULL
)
--New tables after refactoring
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
----------------------------------------------------------------------------
ALTER TABLE [Group Schedule]
WITH CHECK ADD CONSTRAINT [GS_fk0_group_id] FOREIGN KEY (group_id)
REFERENCES [Group work](id_group_work)
ON UPDATE CASCADE
GO

ALTER TABLE [Group Schedule]
WITH CHECK ADD CONSTRAINT [GS_fk1_trainer_id] FOREIGN KEY (trainer_id)
REFERENCES [Trainer Directory](id_trainer)
ON UPDATE CASCADE
GO

ALTER TABLE [Group Schedule]
WITH CHECK ADD CONSTRAINT [GS_fk2_place_id] FOREIGN KEY (place_id)
REFERENCES Place(id_place)
ON UPDATE CASCADE
GO

ALTER TABLE [Group Schedule]
WITH CHECK ADD CONSTRAINT [GS_fk3_type_of_sport_id] FOREIGN KEY (type_of_sport_id)
REFERENCES [Kind of Sport](id_kind)
ON UPDATE CASCADE
GO
----------------------------------------------------------------------------
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

--ЖИЗНЕННАЯ КОНСТРУКЦИЯ - resolving problems with datetime or time  
set language english;

--Adding base things
--------------------------------------------------------------------------------------------------------------------
insert Sale(id_sale, procent_of_sale) values (1, 1);
insert Sale(id_sale, procent_of_sale) values (2, 4);
insert Sale(id_sale, procent_of_sale) values (3, 9);
insert Sale(id_sale, procent_of_sale) values (4, 12);
insert Sale(id_sale, procent_of_sale) values (5, 16);

insert into [Place] values (1, 'Aerobic Training Zone');
insert into [Place] values (2, 'Hall of free weights');
insert into [Place] values (3, 'Boxing Zone');
insert into [Place] values (4, 'Place for group works');

insert into [Kind of Sport] values (1, 'Power training');
insert into [Kind of Sport] values (2, 'Aerobics');
insert into [Kind of Sport] values (3, 'Jump-fit');
insert into [Kind of Sport] values (4, 'Body Sculpt');
insert into [Kind of Sport] values (5, 'Yoga');
insert into [Kind of Sport] values (6, 'Pilates');
insert into [Kind of Sport] values (7, 'Pump');
insert into [Kind of Sport] values (8, 'Bump + ABS');
insert into [Kind of Sport] values (9, 'Zumba');
--------------------------------------------------------------------------------------------------------------------
--Adding group schedule without generating data
--id, --group id, --place id, --trainer id, --time_start, --time end, --day_of_week, --type of sport
insert into [Group Schedule] values (1, 1, 2, 3, '10:00', '11:00', 'Saturday', 1);
insert into [Group Schedule] values (2, 2, 3, 16, '14:00', '16:00', 'Tuesday', 3);
insert into [Group Schedule] values (3, 3, 2, 2, '11:35', '12:50', 'Tuesday', 2);
insert into [Group Schedule] values (4, 1, 1, 8, '15:50', '16:50', 'Monday', 6);
insert into [Group Schedule] values (5, 3, 3, 4, '13:00', '14:00', 'Saturday', 1);
insert into [Group Schedule] values (6, 4, 3, 2, '18:00', '19:00', 'Thurday', 4);
--------------------------------------------------------------------------------------------------------------------

SELECT * FROM  [Group Schedule] 

SELECT * FROM [Group work] 

SELECT * FROM [Trainer Directory]
SELECT * FROM [Client Directory]
SELECT * FROM [Gym Membership] where id_gym_membership = 500
DELETE [Gym Membership]
SELECT * FROM ClientTrace
SELECT * FROM [Sale]

SELECT * FROM Place

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
	SELECT * FROM [Trainer Directory] inner join [Individual Schedule] on [Trainer Directory].id_trainer = [Individual Schedule].trainer_id
	inner join [Client Directory] on [Client Directory].id_client = [Individual Schedule].client_id 
		AND [Client Directory].first_name = @firstName 
			AND [Client Directory].last_name = @lastName

EXEC getTrainerClientINDV 'Kris', 'Juan'

---------------------------------------------------------------------------------------------------------

DROP proc checkName

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


EXEC checkName 'Elon', 'Lieytuven'

SELECT * FROM [Client Directory] where first_name = 'Elon' AND last_name = 'Liyuytueven'

---------------------------------------------------------------------------------------------------------
drop proc countingSumClients

GO
CREATE PROCEDURE countingSumClients @dayOfStart datetime, @dayOfEnd datetime
	AS IF
		@dayOfStart in (select [Gym Membership].day_of_start from [Gym Membership])
			DECLARE @totalPrice INT
			SELECT SUM(price_of_membership) from [Gym Membership] where day_of_start >= @dayOfStart AND day_of_end < @dayOfEnd
		--		 @dayOfEnd in (select [Gym Membership].day_of_end from [Gym Membership] where day_of_end < @dayOfEnd)


EXEC countingSumClients '2015-10-20 08:00:00', '2020-5-8 22:00:00'
--------------------------------------------------------------------------------------------------------

drop proc checkGroupInDay
--Пользователь вводит день недели и ему отображатся все групповые занятия в этот день
GO
CREATE PROCEDURE checkGroupInDay @day varchar(50) as
	SELECT day_of_week, time_start, time_end, name_place, name_kind, first_name, last_name 
		FROM [Group Schedule] inner join Place on [Group Schedule].place_id = Place.id_place 
			inner join [Kind of Sport] on [Group Schedule].type_of_sport_id = [Kind of Sport].id_kind
				AND [Group Schedule].day_of_week = @day 
					inner join [Trainer Directory] on [Group Schedule].trainer_id = [Trainer Directory].id_trainer

EXEC checkGroupInDay 'Saturday'
--------------------------------------------------------------------------------------------------------

drop proc [dbo].[MyProcedure]

--Курсор пробегает по всем строчкам и относит с процентами
exec [dbo].[MyProcedure] 2

GO
CREATE PROCEDURE [dbo].[MyProcedure] @idSendedSale int AS
	DECLARE @ID INT
	DECLARE @idSale int
	DECLARE @currentIdSale int = @idSendedSale
	DECLARE @procentSale int 
	DECLARE @temp int = 0
	SELECT @procentSale = procent_of_sale FROM Sale where id_sale = @currentIdSale
	/*Объявляем курсор*/
	DECLARE @CURSOR CURSOR
	/*Заполняем курсор*/
	SET @CURSOR  = CURSOR SCROLL
	FOR
	SELECT id_client, gym_membership_id FROM [Client Directory]
	/*Открываем курсор*/
	OPEN @CURSOR
	/*Выбираем первую строку*/
	FETCH NEXT FROM @CURSOR INTO @ID, @idSale
	/*Выполняем в цикле перебор строк*/
	WHILE @@FETCH_STATUS = 0
		BEGIN
			IF (@currentIdSale = @idSale)
				BEGIN
					SELECT id_client, first_name, last_name, id_gym_membership, sale, 
					price_of_membership =  price_of_membership - price_of_membership * @procentSale / 100 FROM [Gym Membership]
					inner join [Client Directory] on [Gym Membership].id_gym_membership = [Client Directory].gym_membership_id where sale = @currentIdSale
				END
		/*Выбираем следующую строку*/
			FETCH NEXT FROM @CURSOR INTO @ID, @idSale
		END
CLOSE @CURSOR
