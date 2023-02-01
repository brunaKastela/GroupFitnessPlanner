-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO goals(goal_name) VALUES
('povećanje mišićne mase'),
('izdržljivost'),
('gubitak težine');

INSERT INTO workout_type(workout_type) VALUES
('kardio'),
('snaga'),
('fleksibilnost'),
('bodybuilding');

INSERT INTO workouts(workout_name, workout_typeid) VALUES
('vježba sa utezima od 5kg', (SELECT typeID FROM workout_type WHERE workout_type='snaga')),
('vježba sa utezima od 10kg', (SELECT typeID FROM workout_type WHERE workout_type='snaga')),
('Vježba s konopcem', (SELECT typeID FROM workout_type WHERE workout_type='snaga')),
('Bicep curl s utezima', (SELECT typeID FROM workout_type WHERE workout_type='snaga')),
('Bench press s utezima', (SELECT typeID FROM workout_type WHERE workout_type='snaga')),
('Trčanje na traci', (SELECT typeID FROM workout_type WHERE workout_type='kardio')),
('Skipping s konopcem', (SELECT typeID FROM workout_type WHERE workout_type='kardio')),
('Step aerobik', (SELECT typeID FROM workout_type WHERE workout_type='kardio')),
('Sprintanje', (SELECT typeID FROM workout_type WHERE workout_type='kardio')),
('Vožnja bicikla', (SELECT typeID FROM workout_type WHERE workout_type='kardio')),
('Joga', (SELECT typeID FROM workout_type WHERE workout_type='fleksibilnost')),
('Rastezanje leđa', (SELECT typeID FROM workout_type WHERE workout_type='fleksibilnost')),
('Rastezanje nogu', (SELECT typeID FROM workout_type WHERE workout_type='fleksibilnost')),
('Istezanje vrata', (SELECT typeID FROM workout_type WHERE workout_type='fleksibilnost')),
('Istezanje prednjeg dijela trupa', (SELECT typeID FROM workout_type WHERE workout_type='fleksibilnost')),
('Tricep dip na klupi', (SELECT typeID FROM workout_type WHERE workout_type='bodybuilding')),
('Zgibovi', (SELECT typeID FROM workout_type WHERE workout_type='bodybuilding')),
('Sklekovi', (SELECT typeID FROM workout_type WHERE workout_type='bodybuilding')),
('Čučanj s utezima', (SELECT typeID FROM workout_type WHERE workout_type='bodybuilding')),
('Veslanje', (SELECT typeID FROM workout_type WHERE workout_type='bodybuilding'));



INSERT INTO users(username, password, email, name, surname, date_of_birth,mail_verified, role, contact) VALUES
('admin', '$2a$12$Nxn2nVMmuJ7ejDdDwyZJlOrzBQKi1ehMR/QazxP1tVygV4SrZleCC', 'admin@admin.com','admin','admin',DATE '1990-01-01',true, 0,123456789);

