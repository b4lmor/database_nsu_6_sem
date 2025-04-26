-- 2. Получить перечень и общее число покупателей, купивших указанный вид товара за некоторый период, либо сделавших покупку товара в объеме, не менее заданного.
select ci
from client_info ci
         join public.sale s on ci.id = s.client_info_id
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on pi.id = tpp.product_info_id
where pi.product_article = 'furniture_10001'
  and sell_date between '1900-01-01' and '2026-01-01'
group by ci.id
having count(*) >= 1