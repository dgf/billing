CREATE TABLE t_invoice (
  uuid    uuid        PRIMARY KEY DEFAULT gen_random_uuid(),
  code    varchar(20) CHECK (length(code) > 2),
  date    date        NOT NULL,
  comment text        NOT NULL
);

CREATE TABLE t_invoice_position (
  invoice      uuid     REFERENCES t_invoice ON DELETE CASCADE,
  number       smallint NOT NULL,
  cents        bigint   NOT NULL,
  description  text     NOT NULL,
  PRIMARY KEY (invoice, number)
);