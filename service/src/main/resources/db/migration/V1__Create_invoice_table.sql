CREATE TABLE t_invoice (
  id      serial PRIMARY KEY,
  date    date NOT NULL,
  code    text NOT NULL,
  comment text NOT NULL
);