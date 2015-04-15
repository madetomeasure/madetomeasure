CREATE TABLE subscribers(
  id serial primary key,
  address varchar(255) not null,
  first_name varchar(255),
  last_name varchar(255),
  created_at timestamp without time zone not null default now(),
  updated_at timestamp without time zone not null default now()
);

CREATE UNIQUE INDEX subscribers_address_idx ON subscribers (address);
CREATE INDEX subscribers_updated_at_idx ON subscribers (updated_at);
