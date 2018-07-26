CREATE TABLE t_payment (
  uuid       uuid        PRIMARY KEY DEFAULT gen_random_uuid(),
  created_at timestamp   NOT NULL DEFAULT now(),
  code       varchar(20) CHECK (length(code) > 2),
  date       date        NOT NULL,
  comment    text        NOT NULL,
  cents      bigint      NOT NULL
);
