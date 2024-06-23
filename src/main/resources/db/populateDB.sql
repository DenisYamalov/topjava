DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-01-30 10:00', 'Завтрак', 500),
       (100000, '2020-01-30 13:00', 'Обед', 1000),
       (100000, '2020-01-30 20:00', 'Ужин', 500),
       (100000, '2020-01-31 00:00', 'Еда на граничное значение', 100),
       (100000, '2020-01-31 10:00', 'Завтрак', 1000),
       (100000, '2020-01-31 13:00', 'Обед', 500),
       (100000, '2020-01-31 20:00', 'Ужин', 410),
       (100001, '2020-01-30 10:00', 'Завтрак ADMIN', 500),
       (100001, '2020-01-30 13:00', 'Обед ADMIN', 1000),
       (100001, '2020-01-30 20:00', 'Ужин ADMIN', 500),
       (100001, '2020-01-31 00:00', 'Еда на граничное значение ADMIN', 100),
       (100001, '2020-01-31 10:00', 'Завтрак ADMIN', 1000),
       (100001, '2020-01-31 13:00', 'Обед ADMIN', 500),
       (100001, '2020-01-31 20:00', 'Ужин ADMIN', 410);