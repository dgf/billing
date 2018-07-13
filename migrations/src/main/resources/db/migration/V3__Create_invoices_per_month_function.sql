CREATE TABLE r_invoices_per_month (
  year  smallint,
  month smallint,
  count bigint,
  cents bigint,
  PRIMARY KEY (year, month)
);

CREATE FUNCTION f_get_invoices_per_month(year smallint)
  RETURNS SETOF r_invoices_per_month AS $$
WITH invoices AS (
    SELECT
      i.*,
      sum(p.cents) cents
    FROM t_invoice i, t_invoice_position p
    WHERE i.uuid = p.invoice AND EXTRACT(YEAR FROM date) = year
    GROUP BY i.uuid
)
SELECT
  year,
  EXTRACT(MONTH FROM date) :: smallint,
  count(*),
  sum(cents) :: bigint
FROM invoices
GROUP BY 1, 2;
$$
LANGUAGE SQL;