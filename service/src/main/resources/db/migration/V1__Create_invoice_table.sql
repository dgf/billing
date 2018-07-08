CREATE TABLE t_invoice (
  code    varchar(20) CHECK (length(code) > 2) PRIMARY KEY,
  date    date NOT NULL,
  comment text NOT NULL
);

CREATE TABLE t_invoice_position (
  invoice_code varchar REFERENCES t_invoice ON DELETE CASCADE,
  number       smallint NOT NULL,
  cents        bigint   NOT NULL,
  description  text     NOT NULL,
  PRIMARY KEY (invoice_code, number)
);