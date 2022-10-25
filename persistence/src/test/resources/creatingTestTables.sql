DROP TABLE IF EXISTS gift_certificate_tag;

DROP TABLE IF EXISTS tag;

CREATE TABLE IF NOT EXISTS tag
(
    id       SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS gift_certificate;

CREATE TABLE IF NOT EXISTS gift_certificate
(
    id SERIAL PRIMARY KEY NOT NULL ,
    name VARCHAR NOT NULL ,
    description TEXT NOT NULL ,
    price NUMERIC NOT NULL ,
    duration INT NOT NULL ,
    create_date timestamp NOT NULL default now(),
    last_update_date timestamp
);

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    tag_id int REFERENCES tag(id),
    gift_certificate_id int REFERENCES gift_certificate(id),
    CONSTRAINT gift_certificate_tag_pkey PRIMARY KEY (tag_id, gift_certificate_id)
);