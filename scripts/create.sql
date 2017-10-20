CREATE TABLE category
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR(128),
  href VARCHAR(128)
);
CREATE TABLE product
(
  id           SERIAL PRIMARY KEY,
  name         VARCHAR(128),
  article      VARCHAR(128),
  href         VARCHAR(128),
  img VARCHAR(128)
);

CREATE TABLE category_product
(
  categoryid INTEGER
    REFERENCES category(id),
  productid  INTEGER
    REFERENCES product(id)
);
CREATE TABLE config
(
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(128),
  description VARCHAR(128),
  link        VARCHAR(128),
  createdon   TIMESTAMP,
  lastupdated TIMESTAMP
);

CREATE TABLE task
(
  id       SERIAL PRIMARY KEY,
  configid INTEGER
    REFERENCES config(id),
  status   VARCHAR(128)
);

CREATE TABLE statistics (
  id           SERIAL PRIMARY KEY,
  productid    INTEGER REFERENCES product (id),
  orderscount  VARCHAR(128),
  cost         VARCHAR(128),
  originalcost VARCHAR(128),
  discount     VARCHAR(128),
  createdon    TIMESTAMP
);

CREATE TABLE restcount
(
  id        SERIAL PRIMARY KEY,
  size      VARCHAR(128),
  count     VARCHAR(128),
  statisticsid INTEGER
    REFERENCES statistics (id)
);

insert INTO config (name, description, link, createdon, lastupdated) VALUES ('1', '1', 'https://www.wildberries.ru/catalog/zhenshchinam/bele-i-kupalniki/kombidressy', current_timestamp, current_timestamp)
insert INTO config (name, description, link, createdon, lastupdated) VALUES ('2', '2', 'https://www.wildberries.ru/catalog/zhenshchinam/bele-i-kupalniki/nizhnie-yubki', current_timestamp, current_timestamp)
