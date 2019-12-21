CREATE TABLE gpsdata.data
(
    id SERIAL PRIMARY KEY,
    date DATETIME NOT NULL,
    n_or_s VARCHAR(1) NOT NULL,
    latitude DOUBLE NOT NULL,
    w_or_e VARCHAR(1) NOT NULL,
    longitude DOUBLE NOT NULL,
    user_name VARCHAR(30) NOT NULL
);

CREATE TABLE gpsdata.users
(
    id SERIAL,
    name VARCHAR(30) NOT NULL,
    info VARCHAR(200),
    PRIMARY KEY(name)
);