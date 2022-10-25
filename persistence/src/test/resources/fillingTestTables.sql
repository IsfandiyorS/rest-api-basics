
INSERT INTO tag (name)
VALUES  ('tag1'),
        ('tag2'),
        ('tag3'),
        ('tag4');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate1', 'description1', 12, 1, '2020-08-29T06:12:15.156', '2020-08-29T06:12:15.156'),
('giftCertificate3', 'description3', 19.9, 3, '2019-08-29T06:12:15.156', '2019-08-29T06:12:15.156'),
('giftCertificate2', 'description2', 9.99, 2, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 2),
       (2, 2);