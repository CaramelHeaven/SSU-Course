IF EXISTS (SELECT * FROM SYS.DATABASES WHERE NAME='GymDatabase')
DROP DATABASE GymDatabase
CREATE DATABASE GymDatabase
USE GymDatabase

CREATE TABLE [Client Directory]
(
	id_client int NOT NULL CONSTRAINT PK_client PRIMARY KEY, 
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	gym_membership_id int NOT NULL,
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
	client_id int NOT NULL,
	trainer_id int NOT NULL
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

CREATE TABLE [Group work]
(
	id_group_work int NOT NULL CONSTRAINT PK_id_grp_work PRIMARY KEY,
	name_group varchar(50) NOT NULL,
	quantity int NOT NULL,
	get_people int NOT NULL
)
GO

CREATE TABLE [People Quantity in the group]
(
	id int NOT NULL CONSTRAINT PK_id_PQ_in_TG PRIMARY KEY,
	client int NOT NULL
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

ALTER TABLE [Group work]
WITH CHECK ADD CONSTRAINT [GW_fk_get_people] FOREIGN KEY (get_people)
REFERENCES [People Quantity in the group](id)
ON UPDATE CASCADE
GO

ALTER TABLE [People Quantity in the group]
WITH CHECK ADD CONSTRAINT [PQ_in_TG_fk_client] FOREIGN KEY (client)
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

insert into [Place] values (1, 'Aerobic Training Zone', 35);
insert into [Place] values (2, 'Hall of free weights', 50);
insert into [Place] values (3, 'Boxing Zone', 4);
insert into [Place] values (4, 'Place for group works', 20);
SELECT * FROM [Place]

insert into [People Quantity in the group] values (1, 1);
insert into [People Quantity in the group] values (2, 3);
insert into [People Quantity in the group] values (3, 5);
insert into [People Quantity in the group] values (4, 6);
insert into [People Quantity in the group] values (5, 7);
insert into [People Quantity in the group] values (6, 8);
insert into [People Quantity in the group] values (7, 9);
SELECT * FROM [People Quantity in the group]

insert into [Group work] values (1, 'Group ¹ 1', 20, 1);
insert into [Group work] values (2, 'Group ¹ 1', 20, 2);
insert into [Group work] values (3, 'Group ¹ 1', 20, 3);
insert into [Group work] values (4, 'Group ¹ 1', 20, 4);
insert into [Group work] values (5, 'Group ¹ 2', 10, 5);
insert into [Group work] values (6, 'Group ¹ 2', 10, 6);
insert into [Group work] values (7, 'Group ¹ 2', 10, 7);
SELECT * FROM [Group work]

insert into [Group Schedule] values (1, 1, 2, 3, '10:00', '11:00', 'Saturday', 1);
insert into [Group Schedule] values (2, 2, 2, 1, '14:00', '16:00', 'Tuesday', 3);

SELECT * FROM [Group Schedule]
SELECT * FROM [Group work]
SELECT * FROM [People Quantity in the group]
SELECT * FROM [Place]
SELECT * FROM [Kind of Sport]
SELECT * FROM [Trainer Directory]
SELECT * FROM [Client Directory]
SELECT * FROM [Gym Membership]
SELECT * FROM [Sale]