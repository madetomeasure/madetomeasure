CREATE TABLE subscribers(
  id serial primary key,
  address varchar(255) not null,
  created_at timestamp without time zone,
  updated_at timestamp without time zone
)
