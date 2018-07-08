CREATE TABLE t_invoice (
  code    text NOT NULL PRIMARY KEY,
  date    date NOT NULL,
  comment text NOT NULL
);

CREATE TABLE t_invoice_position (
  invoice_code text REFERENCES t_invoice ON DELETE CASCADE,
  number       smallint NOT NULL,
  cents        bigint   NOT NULL,
  description  text     NOT NULL,
  PRIMARY KEY (invoice_code, number)
);