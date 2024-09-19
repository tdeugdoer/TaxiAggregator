CREATE TABLE cars
(
    id        BIGSERIAL PRIMARY KEY,
    number    VARCHAR(10) UNIQUE NOT NULL,
    brand     VARCHAR(10)        NOT NULL,
    model     VARCHAR(10)        NOT NULL,
    color     VARCHAR(10)        NOT NULL CHECK (color IN
                                                 ('Green', 'Red', 'Yellow', 'Black', 'White', 'Metallic', 'Gray',
                                                  'Orange', 'Blue')),
    driver_id UUID,
    FOREIGN KEY (driver_id) REFERENCES drivers (id)
);
