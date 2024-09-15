INSERT INTO cars (number, brand, model, color, driver_id)
VALUES ('A123BB', 'Toyota', 'Camry', 'Black', (SELECT id FROM drivers WHERE name = 'Иван Иванов')),
       ('B456CC', 'Honda', 'Civic', 'Red', (SELECT id FROM drivers WHERE name = 'Иван Иванов')),
       ('C789DD', 'Ford', 'Focus', 'Black', (SELECT id FROM drivers WHERE name = 'Петр Петров')),
       ('D101EE', 'Mercedes', 'C-Class', 'White', (SELECT id FROM drivers WHERE name = 'Мария Сидорова'));