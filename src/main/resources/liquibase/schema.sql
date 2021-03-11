CREATE TABLE sites
(
  id   serial primary key not null,
  name VARCHAR(255)      UNIQUE not null,
  login VARCHAR (255) not null,
  password VARCHAR (255) not NULL,
  created DATE DEFAULT NOW()
);

CREATE TABLE links
(
  id       serial primary key          not null,
  full_url    varchar(255)                UNIQUE not null,
  short_url     varchar(255) not null,
  created DATE DEFAULT NOW(),
  id_site  int references sites(id)
);

CREATE TABLE statistics
(
 id serial primary key not null,
 url    varchar(255)                UNIQUE not null,
 total int default 0,
 version  bigint
);
