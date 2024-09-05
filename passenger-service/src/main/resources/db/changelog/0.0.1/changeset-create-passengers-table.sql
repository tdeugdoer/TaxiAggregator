CREATE TABLE passengers
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(50)        NOT NULL,
    gender       VARCHAR(5)         NOT NULL CHECK (gender IN ('Men', 'Women', 'Other')),
    phone_number VARCHAR(13) UNIQUE NOT NULL,
    birth_date   DATE
);
