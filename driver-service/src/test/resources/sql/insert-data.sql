INSERT INTO drivers (id, name, gender, phone_number, birth_date, available)
VALUES ('11111111-1111-1111-1111-111111111111', 'John Doe', 'Men', '+375297435874', '2000-01-01', true),
       ('0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5', 'Peter Jones', 'Women', '+375331675879', '1999-05-23', false);

INSERT INTO cars (id, number, brand, model, color, driver_id)
VALUES ('1', 'dsf32241', 'Audi', 'a4', 'Black', '11111111-1111-1111-1111-111111111111'),
       ('2', 'loo98543', 'Mercedes', 'b943', 'White', '0ef7de40-7c91-46b7-8ecb-d68f7eb28ed5');
