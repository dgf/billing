CREATE TABLE t_invoice (
  uuid       uuid        PRIMARY KEY DEFAULT gen_random_uuid(),
  created_at timestamp   NOT NULL DEFAULT now(),
  code       varchar(20) CHECK (length(code) > 2),
  date       date        NOT NULL,
  comment    text        NOT NULL
);

CREATE TABLE t_invoice_position (
  invoice      uuid      REFERENCES t_invoice ON DELETE CASCADE,
  number       smallint  NOT NULL,
  created_at   timestamp NOT NULL DEFAULT now(),
  cents        bigint    NOT NULL,
  description  text      NOT NULL,
  PRIMARY KEY (invoice, number)
);