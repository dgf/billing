CREATE TABLE invoices_per_month (
  year  int,
  month int,
  count int,
  PRIMARY KEY (year, month)
);

CREATE OR REPLACE FUNCTION get_invoices_per_month(year bigint)
  RETURNS SETOF invoices_per_month AS $$
DECLARE
  r invoices_per_month;
BEGIN
  FOR r IN
  SELECT
    year,
    EXTRACT(MONTH FROM date),
    count(*)
  FROM invoice
  WHERE EXTRACT(YEAR FROM date) = year
  GROUP BY 1, 2
  LOOP
    RETURN NEXT r;
  END LOOP;
  RETURN;

END
$$
LANGUAGE plpgsql;