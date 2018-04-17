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
	birthday date NOT NULL,
	bank_account varchar(16) NOT NULL
)
GO

CREATE TABLE [Trainer Directory]
(
	id_trainer int NOT NULL CONSTRAINT PK_trainer PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	birthday date NOT NULL,
	salary money NOT NULL
)
GO

CREATE TABLE [Gym Membership]
(
	id_gym_membership int NOT NULL CONSTRAINT PK_gym PRIMARY KEY,
	day_of_start date NOT NULL,
	day_of_end date NOT NULL,
	price_of_membership money NOT NULL,
	sale int NOT NULL,
	type_of_time varchar(50) NOT NULL
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
	date_individual date NOT NULL,
	client_id int NULL,
	trainer_id int NULL
)
GO

CREATE TABLE [Group Schedule]
(
	id_group_schedule int NOT NULL CONSTRAINT PK_grp_schedule PRIMARY KEY,
	group_id int NOT NULL,
	place_id int NOT NULL,
	trainer_id int NOT NULL,
	time_start time NOT NULL,
	time_end time NOT NULL,
	day_of_week varchar(20) NOT NULL,
	type_of_sport_id int NOT NULL
)
GO

CREATE TABLE [Kind of Sport]
(
	id_kind int NOT NULL CONSTRAINT PK_id_kind PRIMARY KEY,
	name_kind varchar(50) NOT NULL
)
GO

CREATE TABLE Place
(
	id_place int NOT NULL CONSTRAINT PK_place PRIMARY KEY,
	name_place varchar(50) NOT NULL,
	quantity_place int NOT NULL
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
insert Sale(id_sale, procent_of_sale) values (5, 0);
--------------------------------------------------------------------------------------------------------------------
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (1, '2014-4-27', '2014-6-25', 4023, 1, '2 month');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (2, '2010-1-11', '2018-1-11', 80043, 4, '8 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
 values (3, '2012-8-25', '2013-1-24', 5040, 5, '5 month');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (4, '2004-1-6', '2007-6-3', 4330, 2, '3 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (5, '2009-3-2', '2013-5-23', 20432, 2, '4 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (6, '2018-3-5', '2020-12-2', 16023, 4, '2 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (7, '2015-4-27', '2016-6-25', 12034, 3, '1 year');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (8, '2017-11-11', '2018-11-11', 16888, 4, '1 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (9, '2010-8-5', '2013-11-24', 14141, 5, '5 month');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (10, '2014-1-6', '2016-6-3', 14040, 2, '2 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (11, '2007-3-2', '2013-5-23', 20340, 2, '6 years');
insert[Gym Membership](id_gym_membership, day_of_start, day_of_end, price_of_membership, sale, type_of_time) 
values (12, '2016-3-5', '2022-12-2', 46520, 5, '6 years');

--------------------------------------------------------------------------------------------------------------------
SELECT * FROM [Gym Membership]
insert [Client Directory](id_client, first_name, last_name, gym_membership_id, birthday, bank_account) 
values (1, 'Peter', 'Black', 2, '1985-1-23', '1521243432334524');
insert into [Client Directory] values (2, 'Jack', 'Warthon', 3, '1985-11-6', '1331643239834444');
insert into [Client Directory] values (3, 'Nil', 'White', 1, '1967-12-12', '1421222236434854');
insert into [Client Directory] values (4, 'Semuel', 'Folker', 5, '2000-12-3', '1641222263434954');
insert into [Client Directory] values (5, 'Willy', 'Silk', 4, '1976-5-2', '1261222274434444');
insert into [Client Directory] values (6, 'Dave', 'Morrison', 6, '1984-3-28', '3641325233294552');
insert into [Client Directory] values (7, 'Clara', 'Voer', 8, '2003-1-3', '6383523435334524');
insert into [Client Directory] values (8, 'Rose', 'Jelly', 7, '1975-1-16', '1331325557475482');
insert into [Client Directory] values (9, 'Jilly', 'W', 9, '1969-2-2', '8432252335534324');
insert into [Client Directory] values (10, 'Molly', 'Fier', 10, '2002-2-13', '5474222263433254');
insert into [Client Directory] values (11, 'Rose', 'Deksin', 11, '1988-7-2', '1261-5292-99934334');
insert into [Client Directory] values (12, 'Suzan', 'Xenryd', 12, '1999-3-8', '6371963237595232');
insert into [Client Directory] values (13, 'Dylan', 'Thompson', 1, '1947-12-12', '1424341222236854');
insert into [Client Directory] values (14, 'Ashton', 'Hall', 4, '1977-2-6', '1443212222364854');
insert into [Client Directory] values (15, 'Tom', 'Perry', 1, '1987-4-3', '1426431222234854');
insert into [Client Directory] values (16, 'Nil', 'Nash', 2, '1973-5-15', '1421222236434854');
insert into [Client Directory] values (17, 'Eftain', 'Maldonado', 3, '1963-1-12', '1222433642124854');
insert into [Client Directory] values (18, 'Tom', 'Adams', 1, '1955-2-3', '1422136434222854');
insert into [Client Directory] values (19, 'Hugh', 'Garcia', 2, '1997-2-8', '1422223643214854');
insert into [Client Directory] values (20, 'Israel', 'Adams', 5, '1999-2-7', '1421434822223654');
SELECT * FROM [Client Directory]
--------------------------------------------------------------------------------------------------------------------
insert into [Trainer Directory] values (1, 'Cuthbert', 'Taylor', '1987-1-16', 30023);
insert into [Trainer Directory] values (2, 'William', 'Harris', '1945-2-12', 40342.3);
insert into [Trainer Directory] values (3, 'Maximillian', 'Young', '1983-2-13', 20324.56);
insert into [Trainer Directory] values (4, 'Stephen', 'Ross', '1969-2-27', 24000);
insert into [Trainer Directory] values (5, 'Scot', 'Jackson', '1975-6-14', 31000);
SELECT * FROM [Trainer Directory]
--------------------------------------------------------------------------------------------------------------------
insert into [Kind of Sport] values (1, 'Power training');
insert into [Kind of Sport] values (2, 'Aerobics');
insert into [Kind of Sport] values (3, 'Jump-fit');
insert into [Kind of Sport] values (4, 'Body Sculpt');
insert into [Kind of Sport] values (5, 'Yoga');
insert into [Kind of Sport] values (6, 'Pilates');
insert into [Kind of Sport] values (7, 'Pump');
insert into [Kind of Sport] values (8, 'Bump + ABS');
insert into [Kind of Sport] values (9, 'Zumba');
SELECT * FROM [Kind of Sport]

insert into [Place] values (1, 'Aerobic Training Zone', 10);
insert into [Place] values (2, 'Hall of free weights', 30);
insert into [Place] values (3, 'Boxing Zone', 5);
insert into [Place] values (4, 'Place for group works', 20);
SELECT * FROM [Place]

insert into ClientTrace values(1, 1, '2014-2-26', NULL);
insert into ClientTrace values(2, 2, '2005-12-5', NULL);
insert into ClientTrace values(3, 3, '2009-1-7', NULL);
insert into ClientTrace values(4, 4, '2006-4-3', NULL);
insert into ClientTrace values(5, 5, '2005-3-5', NULL);
insert into ClientTrace values(6, 6, '2007-7-7', NULL);
insert into ClientTrace values(7, 7, '2003-4-2', NULL);
insert into ClientTrace values(8, 8, '2005-9-13', NULL);
insert into ClientTrace values(9, 9, '2005-9-13', NULL);

SELECT * FROM ClientTrace
--DELETE FROM ClientTrace
--DELETE FROM [Group work]


insert into [Group work] values (1, 1, 'Group № 1', 1);
insert into [Group work] values (2, 2, 'Group № 2', 2);
insert into [Group work] values (3, 1, 'Group № 1', 3);

insert into [Group work] values (4, 1, 'Group № 1', 8);
insert into [Group work] values (5, 1, 'Group № 1', 4);
insert into [Group work] values (6, 1, 'Group № 1', 5);
insert into [Group work] values (7, 2, 'Group № 2', 6);
insert into [Group work] values (8, 2, 'Group № 2', 9);

SELECT * FROM [Group work]

insert into [Group Schedule] values (1, 1, 2, 3, '10:00', '11:00', 'Saturday', 1);
insert into [Group Schedule] values (2, 2, 3, 1, '14:00', '16:00', 'Tuesday', 3);

insert into [Individual Schedule] values (1, '7:00', '8:15', '2014-3-27', 10, 3);
insert into [Individual Schedule] values (2, '14:35', '15:45', '2016-4-22', 12, 4);
insert into [Individual Schedule] values (3, '20:35', '22:25', '2018-7-2', 14, 5);


select * from [Individual Schedule]
	--id_group_schedule int NOT NULL CONSTRAINT PK_grp_schedule PRIMARY KEY,
	--group_id int NOT NULL,
	--place_id int NOT NULL,
	--trainer_id int NOT NULL,
	--time_start time NOT NULL,
	--time_end time NOT NULL,
	--day_of_week varchar(20) NOT NULL,
	--type_of_sport_id int NOT NULL

SELECT * FROM [Group Schedule]
DELETE FROM [Group Schedule]
DELETE FROM Place
SELECT * FROM [Group work] 
SELECT * FROM [Place] 
SELECT * FROM [Kind of Sport]
SELECT * FROM [Trainer Directory]
SELECT * FROM [Client Directory]
SELECT * FROM [Gym Membership]
SELECT * FROM ClientTrace
SELECT * FROM [Sale]

DROP TRIGGER delete_user

DELETE FROM [Client Directory] where id_client = 1 


DROP TRIGGER DELETE_USER_INDIV
-----------------------------------------------------------------------------------------------------------------------— 
GO
CREATE TRIGGER DELETE_USER_INDIV ON [Client Directory] 
INSTEAD OF DELETE
AS
	DECLARE @idClientTrace int
	DECLARE @baseId int
	SELECT @baseId = id_client FROM [Client Directory] where id_client in (SELECT id_client FROM deleted)
	SELECT @idClientTrace = id_group_client FROM ClientTrace where id_group_client in (SELECT id_client FROM deleted)
BEGIN
	IF NOT EXISTS (SELECT * FROM deleted)
		BEGIN
			RAISERROR('Client not found', 10, 1)
			ROLLBACK TRANSACTION
		END
	ELSE IF @idClientTrace = @baseId
		BEGIN
			PRINT 'Client also deleted from group work'
			DELETE [Group work] from deleted WHERE deleted.id_client = [Group work].get_people
			DELETE ClientTrace from deleted WHERE deleted.id_client = ClientTrace.client_id
		end 
	ELSE
		BEGIN
			PRINT 'Client also deleted from individual schedule'
			DELETE [Individual Schedule] from deleted WHERE deleted.id_client = [Individual Schedule].client_id 
			DELETE [Client Directory] from deleted WHERE deleted.id_client = [Client Directory].id_client
			DELETE [Gym Membership] from deleted WHERE deleted.gym_membership_id = [Gym Membership].id_gym_membership
		END
END

DELETE FROM [Client Directory] where id_client = 12

SELECT * FROM [Individual Schedule] 
SELECT * FROM [Client Directory]
SELECT * FROM [Gym Membership]

SELECT * FROM ClientTrace
SELECT * FROM [Group work]
-----------------------------------------------------------------------------------------------------------------------— 

DROP TRIGGER DELETE_TRAINER

GO
CREATE TRIGGER DELETE_TRAINER ON [Trainer Directory]
INSTEAD OF DELETE
AS
BEGIN
	DECLARE @idTrainer int
	DECLARE @randTrainer int
	DECLARE @randTrainer2 int
	DECLARE @idIndivTrainer int
	DECLARE @idGroupTrainer int
	SELECT @idIndivTrainer = trainer_id FROM [Individual Schedule] WHERE trainer_id in (SELECT id_trainer FROM deleted)
	SELECT @idGroupTrainer = trainer_id FROM [Group Schedule] WHERE trainer_id in (SELECT id_trainer FROM deleted)
	SELECT @idTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT id_trainer FROM deleted)

	IF @idTrainer = @idGroupTrainer AND @idTrainer = @idIndivTrainer
		BEGIN
			SELECT @randTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())
			SELECT @randTrainer2 = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())
			PRINT 'Locating in equals training'
			PRINT @randTrainer
			PRINT @randTrainer2
			PRINT @idTrainer
			WHILE @randTrainer = @idTrainer 
				BEGIN
					SELECT @randTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())
					PRINT 'Прогоняемся по циклу'
					PRINT @randTrainer
				END
			WHILE @randTrainer2 = @idTrainer
				BEGIN
					SELECT @randTrainer2 = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())	
					PRINT 'Прогоняемся по циклу'
					PRINT @randTrainer2					
				END				
			PRINT 'Updating trainer'
			PRINT 'rand first'
			PRINT @randTrainer
			PRINT 'rand second'
			PRINT @randTrainer2
				BEGIN
					UPDATE [Group Schedule]
						SET trainer_id = @randTrainer
				END
				BEGIN
					UPDATE [Individual Schedule]
						SET trainer_id = @randTrainer2
				END
		DELETE [Trainer Directory] from deleted where deleted.id_trainer = [Trainer Directory].id_trainer
		END
	ELSE 
		IF @idTrainer = @idGroupTrainer
		BEGIN
			SELECT @randTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())
			PRINT 'Locating in Group schedule'
			PRINT @randTrainer
			PRINT @idGroupTrainer
			WHILE @randTrainer = @idGroupTrainer
				BEGIN
					SELECT @randTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())	
					PRINT @randTrainer				
				END
			PRINT 'Updating trainer'
			PRINT @randTrainer
			UPDATE [Group Schedule]
				SET trainer_id = @randTrainer
		DELETE [Trainer Directory] from deleted where deleted.id_trainer = [Trainer Directory].id_trainer
		END
	ELSE 
		IF @idTrainer = @idIndivTrainer
		BEGIN
			SELECT @randTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())
			PRINT 'Location in Indiv'
			PRINT @randTrainer
			PRINT @idGroupTrainer
			WHILE @randTrainer = @idIndivTrainer
				BEGIN
					SELECT @randTrainer = id_trainer FROM [Trainer Directory] WHERE id_trainer in (SELECT TOP 1 id_trainer FROM [Trainer Directory] ORDER BY NEWID())		
					PRINT @randTrainer												
				END
			PRINT 'Updating trainer'
			PRINT @randTrainer
			UPDATE [Individual Schedule]
				SET trainer_id = @randTrainer
		DELETE [Trainer Directory] from deleted where deleted.id_trainer = [Trainer Directory].id_trainer
		END
END

DELETE FROM [Trainer Directory] where id_trainer = 1

SELECT * FROM [Trainer Directory]

SELECT * FROM [Group Schedule]
SELECT * FROM [Individual Schedule]

GO
CREATE TRIGGER CHECK_SIZE_PLACE ON [Group Schedule] 
FOR INSERT
AS
BEGIN
	DECLARE @idGroupWork int
	DECLARE @sizePeopleGroup int
	DECLARE @baseSize int
	SELECT @idGroupWork = id_group FROM [Group work] WHERE id_group in (SELECT group_id FROM inserted)
	PRINT @idGroupWork
	SELECT @sizePeopleGroup = COUNT(*) FROM [Group work] WHERE id_group in (SELECT group_id FROM inserted)
	PRINT @sizePeopleGroup
	SELECT @baseSize = quantity_place from Place WHERE id_place in (SELECT place_id FROM inserted)
	PRINT @baseSize
		IF @sizePeopleGroup >= @baseSize
		BEGIN
			PRINT 'Error for insert. Size of group is so much than place where they want to be live'
			ROLLBACK TRANSACTION
		END
END

insert into [Group Schedule] values (5, 1, 3, 3, '10:00', '11:00', 'Saturday', 1);



-----------------------------------------------------------------------------------------------------------------------— 
--Обновление времени у группового занятия 
DROP TRIGGER UPDATING_TIME_GROUP_SCHEDULE 

GO 
CREATE TRIGGER UPDATING_TIME_GROUP_SCHEDULE ON [Group Schedule] 
AFTER UPDATE 
AS 
	DECLARE @timeStart time 
	DECLARE @timeEnd time 
	BEGIN 
		SELECT @timeStart = time_start FROM [Group Schedule] WHERE id_group_schedule in (SELECT id_group_schedule FROM inserted) 
		SELECT @timeEnd = time_end FROM [Group Schedule] WHERE id_group_schedule in (SELECT id_group_schedule FROM inserted) 
		PRINT 'currentTime: ' 
		PRINT @timeStart 
			SELECT @timeEnd = DATEADD(hour, 1, @timeStart) 
		PRINT @timeEnd 
		UPDATE [Group Schedule]
			SET time_start = @timeStart WHERE id_group_schedule in (SELECT id_group_schedule FROM inserted) 
		UPDATE [Group Schedule]
			SET time_end = @timeEnd WHERE id_group_schedule in (SELECT id_group_schedule FROM inserted) 
END 

UPDATE [Group Schedule] set time_start = '18:00' where id_group_schedule = 1 
SELECT * FROM [Group Schedule] 

-----------------------------------------------------------------------------------------------------------------------— 

GO 
CREATE TRIGGER UPDATING_TIME_INDIVID_SCHEDULE ON [Individual Schedule] 
AFTER UPDATE 
AS 
	DECLARE @timeStart time 
	DECLARE @timeEnd time 
	BEGIN 
		SELECT @timeStart = time_start FROM [Individual Schedule]  WHERE id_individual_work in (SELECT id_individual_work FROM inserted) 
		SELECT @timeEnd = time_end FROM [Individual Schedule]  WHERE id_individual_work in (SELECT id_individual_work FROM inserted) 
		PRINT 'currentTime: ' 
		PRINT @timeStart 
			SELECT @timeEnd = DATEADD(hour, 1, @timeStart) 
		PRINT @timeEnd 
		UPDATE [Individual Schedule] 
			SET time_start = @timeStart WHERE id_individual_work in (SELECT id_individual_work FROM inserted) 
		UPDATE [Individual Schedule] 
			SET time_end = @timeEnd WHERE id_individual_work in (SELECT id_individual_work FROM inserted) 
END 

UPDATE [Individual Schedule] set time_start = '18:00' where id_individual_work = 3

SELECT * FROM [Individual Schedule]
