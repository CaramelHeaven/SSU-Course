CREATE DATABASE GymDB
COLLATE Cyrillic_General_CI_AS
GO

--¬се, кто имеют свой id - ставлю id слева, id которые ссылаютс€ на другие id - right

CREATE TABLE [Client Directory]
(
	id_client int NOT NULL IDENTITY, -- автоинкремитрировано поле. Id јвтоматически генерируютс€
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	gym_membership_id int NOT NULL,
	birthday date NOT NULL,
	bank_account int NOT NULL
)
GO

CREATE TABLE [Trainer Directory]
(
	id_trainer int NOT NULL IDENTITY,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	birthday date NOT NULL,
	salary money NOT NULL
)
GO

CREATE TABLE [Gym Membership]
(
	id_gym_membership int NOT NULL IDENTITY,
	day_of_start date NOT NULL,
	day_of_end date NOT NULL,
	price_of_membership money NOT NULL,
	sale int NOT NULL,
	type_of_time varchar NOT NULL
)
GO

CREATE TABLE Sale
(
	id_sale int NOT NULL, 
	procent_of_sale int NOT NULL,
)
GO

CREATE TABLE [Induvidual Schedule]
(
	id_individual_work int NOT NULL,
	time_start time NOT NULL,
	time_end time NOT NULL,
	date_individual date NOT NULL,
	client_id int NOT NULL,
	trainer_id int NOT NULL
)
GO

CREATE TABLE [Group Schedule]
(
	id_group int NOT NULL,
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
	id_kind int NOT NULL,
	name_kind varchar(20) NOT NULL
)
GO

CREATE TABLE [Place]
(
	id_place int NOT NULL,
	name_place varchar(20) NOT NULL,
	quantity_place int NOT NULL 
)
GO

CREATE TABLE [Group work]
(
	id_group_table int NOT NULL,
	name_group varchar(20) NOT NULL,
	quantity int NOT NULL,
	get_people int NOT NULL
)
GO

CREATE TABLE [People Quantity in the group]
(
	id int NOT NULL,
	client int NOT NULL
)
GO

ALTER TABLE [Client Directory]
ADD PRIMARY KEY (id_client)
GO

ALTER TABLE [Trainer Directory]
ADD PRIMARY KEY (id_trainer)
GO

ALTER TABLE [Gym Membership]
ADD UNIQUE (id_gym_membership) --ќбеспечиваем уникальность пол€, после этого мы можем св€зать таблицы ClientDirectory with Gym Membership
GO

---------REFENCES----------------------REFENCES----------------------REFENCES----------------------REFENCES----------------------REFENCES-------------
ALTER TABLE [Client Directory]
ADD
FOREIGN KEY (gym_membership_id) REFERENCES [Gym Membership] (id_gym_membership)
GO

ALTER TABLE [Sale]
ADD UNIQUE (id_sale)
GO

ALTER TABLE [Gym Membership]
ADD
FOREIGN KEY (sale) REFERENCES [Sale] (id_sale)
GO
------------------------------------------------------------------------
ALTER TABLE [Induvidual Schedule]
ADD PRIMARY KEY (id_individual_work)
GO

ALTER TABLE [Induvidual Schedule]
ADD
FOREIGN KEY (trainer_id) REFERENCES [Trainer Directory] (id_trainer)
GO



--ќбеспечение уникальности,
--ALTER TABLE [Client Directory]
--ADD
--UNIQUE(id_client)
--GO

-- We set some primaryKeys:

--ќ“ЌќЎ≈Ќ»я----ќ“ЌќЎ≈Ќ»я----ќ“ЌќЎ≈Ќ»я----ќ“ЌќЎ≈Ќ»я----ќ“ЌќЎ≈Ќ»я----ќ“ЌќЎ≈Ќ»я--



ALTER TABLE [Gym Membership]
ADD
FOREIGN KEY (sale) REFERENCES [Sale] (id_sale)
GO

ALTER TABLE [Induvidual Schedule]
ADD 
FOREIGN KEY (client_id) REFERENCES [Client Directory] (id_client)
GO

ALTER TABLE [Induvidual Schedule]
ADD
FOREIGN KEY (trainer_id) REFERENCES [Trainer Directory] (id_trainer)
GO
-----------------------------------------------------------
ALTER TABLE [Group Schedule]
ADD
FOREIGN KEY (id_group) REFERENCES [Group work] (id_group_table)
GO

ALTER TABLE [Group Schedule]
ADD
FOREIGN KEY (place_id) REFERENCES [Place] (id_place)
GO

ALTER TABLE [Group Schedule]
ADD
FOREIGN KEY (type_of_sport_id) REFERENCES [Kind Of Sport] (id_kind)
GO

ALTER TABLE [Group Schedule]
ADD
FOREIGN KEY (trainer_id) REFERENCES [Trainer Directory] (id_trainer)
GO
-----------------------------------------------------------
ALTER TABLE [Group work]
ADD
FOREIGN KEY (get_people) REFERENCES [People Quantity in the group] (id)
GO

ALTER TABLE [People Quantity in the group]
ADD
FOREIGN KEY (client) REFERENCES [Client Directory] (id_client)
GO

--Simple example - ѕри удалении записи Products - у нас так же удалитьс€ запить ProductDetails, т.е. его описание
--ALTER TABLE ProductDetails
--ADD
--FOREIGN KEY(ID) REFERENCES Products(ID)
--ON DELETE CASCADE
--GO
