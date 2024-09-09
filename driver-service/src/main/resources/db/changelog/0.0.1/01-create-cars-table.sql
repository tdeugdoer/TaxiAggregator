CREATE TABLE cars
(
    id     BIGSERIAL PRIMARY KEY,
    number VARCHAR(10) UNIQUE NOT NULL,
    brand  VARCHAR(10)        NOT NULL,
    model  VARCHAR(10)        NOT NULL,
    color  VARCHAR(10)        NOT NULL CHECK (color IN
                                              ('GREEN', 'RED', 'YELLOW', 'BLACK', 'WHITE', 'METALLIC', 'GRAY', 'ORANGE'))
);
