CREATE DATABASE GymDatabase
COLLATE Cyrillic_General_CI_AS
GO

--¬се, кто имеют свой id - ставлю id слева, id которые ссылаютс€ на другие id - right
----------------------------------------------------------------------------------------
CREATE TABLE [Client Directory]
(
	id_client int NOT NULL, -- IDENTITY автоинкремитрировано поле. Id јвтоматически генерируютс€ с 1
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	gym_membership_id int NOT NULL,
	birthday date NOT NULL,
	bank_account varchar(16) NOT NULL
)
GO

ALTER TABLE [Client Directory]
ADD CONSTRAINT [PK_Client_Directory] PRIMARY KEY CLUSTERED ([id_client])
GO

ALTER TABLE [Client Directory]
WITH CHECK ADD CONSTRAINT [Client_FK] FOREIGN KEY (gym_membership_id)
REFERENCES [Gym Membership]([id_gym_membership])
ON UPDATE CASCADE
GO

ALTER TABLE [Client Directory]
CHECK CONSTRAINT [Client_FK]
GO
----------------------------------------------------------------------------------------
CREATE TABLE [Trainer Directory]
(
	id_trainer int NOT NULL,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	birthday date NOT NULL,
	salary money NOT NULL
)
GO
ALTER TABLE [Trainer Directory]
ADD CONSTRAINT [PK_Trainer_Directory] PRIMARY KEY CLUSTERED (id_trainer)
GO
----------------------------------------------------------------------------------------
CREATE TABLE [Gym Membership]
(
	id_gym_membership int NOT NULL,
	day_of_start date NOT NULL,
	day_of_end date NOT NULL,
	price_of_membership money NOT NULL,
	sale int NOT NULL,
	type_of_time varchar NOT NULL
)
GO

ALTER TABLE [Gym Membership]
ADD CONSTRAINT [PK_Gym_Membership] PRIMARY KEY CLUSTERED (id_gym_membership)
GO

ALTER TABLE [Gym Membership]
WITH CHECK ADD CONSTRAINT [Gym_FK] FOREIGN KEY (sale)
REFERENCES Sale(id_sale)
ON UPDATE CASCADE
GO

ALTER TABLE [Gym Membership]
CHECK CONSTRAINT [Gym_FK]
GO
----------------------------------------------------------------------------------------
CREATE TABLE Sale
(
	id_sale int NOT NULL, 
	procent_of_sale int NOT NULL,
)
GO
ALTER TABLE Sale
ADD CONSTRAINT [PK_Sale] PRIMARY KEY CLUSTERED (id_sale)
GO
----------------------------------------------------------------------------------------
CREATE TABLE [Individual Schedule]
(
	id_individual_work int NOT NULL,
	time_start time NOT NULL,
	time_end time NOT NULL,
	date_individual date NOT NULL,
	client_id int NOT NULL,
	trainer_id int NOT NULL
)
GO

ALTER TABLE [Individual Schedule]
ADD CONSTRAINT [PK_Individual_Schedule] PRIMARY KEY CLUSTERED (id_individual_work)
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

ALTER TABLE [Individual Schedule]
CHECK CONSTRAINT [IS_fk0_client_id]
GO

ALTER TABLE [Individual Schedule]
CHECK CONSTRAINT [IS_fk1_trainer_id]
GO
----------------------------------------------------------------------------------------
CREATE TABLE [Group Schedule]
(
	id_group_schedule int NOT NULL,
	group_id int NOT NULL,
	place_id int NOT NULL,
	trainer_id int NOT NULL,
	time_start time NOT NULL,
	time_end time NOT NULL,
	day_of_week varchar(20) NOT NULL,
	type_of_sport_id int NOT NULL
)
GO

ALTER TABLE [Group Schedule]
ADD CONSTRAINT [PK_Group_Schedule] PRIMARY KEY CLUSTERED (id_group_schedule)
GO

ALTER TABLE [Group Schedule]
WITH CHECK ADD CONSTRAINT [GS_fk0_group_id] FOREIGN KEY (group_id)
REFERENCES [Group work](id_group_table)
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

ALTER TABLE [Group Schedule]
CHECK CONSTRAINT [GS_fk0_group_id]
GO

ALTER TABLE [Group Schedule]
CHECK CONSTRAINT [GS_fk1_trainer_id]
GO


ALTER TABLE [Group Schedule]
CHECK CONSTRAINT [GS_fk2_place_id]
GO


ALTER TABLE [Group Schedule]
CHECK CONSTRAINT [GS_fk3_type_of_sport_id]
GO
----------------------------------------------------------------------------------------
CREATE TABLE [Kind of Sport]
(
	id_kind int NOT NULL,
	name_kind varchar(20) NOT NULL
)
GO

ALTER TABLE [Kind of Sport]
ADD CONSTRAINT [PK_Kind_of_Sport] PRIMARY KEY CLUSTERED (id_kind)
GO
----------------------------------------------------------------------------------------
CREATE TABLE Place
(
	id_place int NOT NULL,
	name_place varchar(20) NOT NULL,
	quantity_place int NOT NULL 
)
GO

ALTER TABLE Place
ADD CONSTRAINT [PK_place] PRIMARY KEY CLUSTERED (id_place)
GO
----------------------------------------------------------------------------------------

CREATE TABLE [Group work]
(
	id_group_table int NOT NULL,
	name_group varchar(20) NOT NULL,
	quantity int NOT NULL,
	get_people int NOT NULL
)
GO

ALTER TABLE [Group work]
ADD CONSTRAINT [PK_group_work] PRIMARY KEY CLUSTERED (id_group_table)
GO

ALTER TABLE [Group work]
WITH CHECK ADD CONSTRAINT [GW_fk_get_people] FOREIGN KEY (get_people)
REFERENCES [People Quantity in the group](id)
ON UPDATE CASCADE
GO

ALTER TABLE [Group work]
CHECK CONSTRAINT [GW_fk_get_people]
GO
----------------------------------------------------------------------------------------
CREATE TABLE [People Quantity in the group]
(
	id int NOT NULL,
	client int NOT NULL
)
GO

ALTER TABLE [People Quantity in the group]
ADD CONSTRAINT [PK_id_PQIG] PRIMARY KEY CLUSTERED (id)
GO

ALTER TABLE [People Quantity in the group]
WITH CHECK ADD CONSTRAINT [PQIG_fk_client] FOREIGN KEY (client)
REFERENCES [Client Directory](id_client)
ON UPDATE CASCADE
GO

ALTER TABLE [People Quantity in the group]
CHECK CONSTRAINT [PQIG_fk_client]
GO