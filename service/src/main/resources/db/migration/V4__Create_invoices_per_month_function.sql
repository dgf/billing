CREATE TABLE r_invoices_per_month (
  year  int,
  month int,
  count int,
  PRIMARY KEY (year, month)
);

CREATE OR REPLACE FUNCTION f_get_invoices_per_month(year bigint)
  RETURNS SETOF r_invoices_per_month AS $$
DECLARE
  r r_invoices_per_month;
BEGIN
  FOR r IN
  SELECT
    year,
    EXTRACT(MONTH FROM date),
    count(*)
  FROM t_invoice
  WHERE EXTRACT(YEAR FROM date) = year
  GROUP BY 1, 2
  LOOP
    RETURN NEXT r;
  END LOOP;
  RETURN;

END
$$
LANGUAGE plpgsql;