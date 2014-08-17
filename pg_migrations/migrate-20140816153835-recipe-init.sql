CREATE TABLE recipes (
id           VARCHAR (50) PRIMARY KEY,
name         VARCHAR (100),
description  VARCHAR (200),
recipe_str   VARCHAR (1000),
submitted_by VARCHAR (80) references users(name));
