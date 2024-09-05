--liquibase formatted sql

--changeset tserashkevich:1
INSERT INTO passengers (name, gender, phone_number, birth_date)
VALUES ('John Doe', 'Men', '1234567890', '2000-01-01'),
       ('Peter Jones', 'Other', '1112223333', '1980-10-20'),
       ('Mary Brown', 'Women', '4445556666', '1975-03-08');
--rollback DELETE FROM passengers WHERE name IN ('John Doe', 'Peter Jones', 'Mary Brown');

