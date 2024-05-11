INSERT INTO Address (id, address_line1, address_line2, city, postal_code) VALUES (1, 'xx', 'yy', 'city', '62-030');
INSERT INTO Address (id, address_line1, address_line2, city, postal_code) VALUES (2, 'Testowa2', 'Testowa2', 'Cracow', '30-001');
INSERT INTO Address (id, address_line1, address_line2, city, postal_code) VALUES (3, 'Testowa3', 'Testowa3', 'Warsaw', '30-001');
INSERT INTO Address (id, address_line1, address_line2, city, postal_code) VALUES (4, 'Example4', 'Example4', 'Gdansk', '80-001');
INSERT INTO Address (id, address_line1, address_line2, city, postal_code) VALUES (5, 'Example5', 'Example5', 'Wroclaw', '50-001');
INSERT INTO Address (id, address_line1, address_line2, city, postal_code) VALUES (6, 'Example6', 'Example6', 'Poznan', '60-001');

INSERT INTO Doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id) VALUES (1, 'ABC123', 'johndoe@example.com', 'Simon', 'Simonowski', 'SURGEON', '123123123', 1);
INSERT INTO Doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id) VALUES (2, 'DEF456', 'emilyr@example.com', 'Emily', 'Ross', 'GP', '321321321', 2);
INSERT INTO Doctor (id, doctor_number, email, first_name, last_name, specialization, telephone_number, address_id) VALUES (3, 'GHI789', 'michaelb@example.com', 'Michael', 'Brown', 'DERMATOLOGIST', '456456456', 3);


INSERT INTO Patient (id, date_of_birth, email, first_name, last_name, patient_number, registration_date, telephone_number, address_id) VALUES (1, '1980-04-01', 'janesmith@example.com', 'Jane', 'Smith', '123123', '2024-04-08', '123123123', 2);
INSERT INTO Patient (id, date_of_birth, email, first_name, last_name, patient_number, registration_date, telephone_number, address_id) VALUES (2, '1990-02-02', 'john.doeee@example.com', 'John', 'Doeee', '456456', '2024-04-09', '321321321', 4);
INSERT INTO Patient (id, date_of_birth, email, first_name, last_name, patient_number, registration_date, telephone_number, address_id) VALUES (3, '1985-03-03', 'lisa.ann@example.com', 'Lisa', 'Ann', '789789', '2024-04-10', '654654654', 5);
INSERT INTO Patient (id, date_of_birth, email, first_name, last_name, patient_number, registration_date, telephone_number, address_id) VALUES (4, '1985-03-03', 'test@example.com', 'Prosze o 3 :(', 'Prosze o 3 :(', '123333', '1980-05-10', '654654654', 5);

INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES ('Annual Checkup', '2024-04-09 10:00:00', 1, 1);
INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES ('Routine Checkup', '2024-04-10 11:00:00', 2, 2);
INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES ('Consultation', '2024-04-11 12:00:00', 3, 3);
INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES ('Consultation', '2024-04-12 12:00:00', 3, 3);
INSERT INTO Visit (description, time, doctor_id, patient_id) VALUES ('Consultation', '2024-04-12 12:00:00', 3, 3);


INSERT INTO Medical_Treatment (description, type, visit_id) VALUES ('Blood Test', 'USG', 1);
INSERT INTO Medical_Treatment (description, type, visit_id) VALUES ('MRI Scan', 'USG', 2);

SELECT * FROM Patient WHERE last_name='Smith';

SELECT * FROM Visit WHERE patient_id = 1;

SELECT p.*, COUNT(v.id) as visit_count
FROM Patient p
JOIN Visit v ON p.id = v.patient_id
GROUP BY p.id
HAVING COUNT(v.id) > 2;

-- SELECT * FROM Patient WHERE registration_date > '2023-01-01';

