-- 6. Получить данные о выработке отдельно взятого продавца отдельно взятой торговой точки за указанный период.
select seller_id, sum(pi.price * s.sale_count)
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.sale s on tpp.id = s.tpp_id
where seller_id = 1
  and sell_date between '1900-01-01' and '2026-01-01'
  and tp_id = 1
group by seller_id