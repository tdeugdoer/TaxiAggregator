INSERT INTO drivers (id, name, gender, phone_number, birth_date, available)
VALUES ('ced8b8a3-d691-4e57-adec-2c615d7ee3c1', 'Иван Иванов', 'Men', '+375297436112', '2000-01-01', TRUE),
       ('661f45e3-a203-4de2-bd11-c77ba0bd7052', 'Петр Петров', 'Men', '+375337436112', '1995-05-15', FALSE),
       ('714de0de-3e65-42a7-b874-676c99ba6855', 'Мария Сидорова', 'Women', '+375447436112', '1998-08-22', TRUE),
       ('0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5', 'Peter Jones', 'Women', '+375331675879', '1999-05-23', false);

INSERT INTO cars (id, number, brand, model, color, driver_id)
VALUES (1, 'A123BB', 'Toyota', 'Camry', 'Black', (SELECT id FROM drivers WHERE name = 'Иван Иванов')),
       (2, 'B456CC', 'Honda', 'Civic', 'Red', (SELECT id FROM drivers WHERE name = 'Иван Иванов')),
       (3, 'C789DD', 'Ford', 'Focus', 'Black', (SELECT id FROM drivers WHERE name = 'Петр Петров')),
       (4, 'D101EE', 'Mercedes', 'C-Class', 'White', (SELECT id FROM drivers WHERE name = 'Мария Сидорова'));