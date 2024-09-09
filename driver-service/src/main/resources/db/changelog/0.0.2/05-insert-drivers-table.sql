INSERT INTO drivers (name, gender, phone_number, birth_date, car_id)
VALUES ('Иван Иванов', 'Men', '+79123456789', '1990-01-01', (SELECT id FROM cars WHERE number = 'A123BB')),
       ('Мария Петрова', 'Women', '+79234567890', '1995-02-02', (SELECT id FROM cars WHERE number = 'B456CC')),
       ('Алексей Сидоров', 'Men', '+79345678901', '1985-03-03', (SELECT id FROM cars WHERE number = 'C789DD')),
       ('Ольга Козлова', 'Women', '+79456789012', '1992-04-04', (SELECT id FROM cars WHERE number = 'D101EE'));
