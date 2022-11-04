INSERT INTO tag (name)
VALUES  ('tag1'),
        ('tag2'),
        ('tag3'),
        ('tag4'),
        ('tag5');

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate1', 'description1', 12, 1, '2022-10-26T06:12:15.156', '2022-10-26T06:12:15.156'),
('giftCertificate3', 'description3', 19.9, 3, '2022-10-24T06:12:15.156', '2022-10-24T06:12:15.156'),
('giftCertificate2', 'description2', 9.99, 2, '2022-10-25T06:12:15.156', '2022-10-25T06:12:15.156');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 2),
       (2, 2);