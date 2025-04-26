-- 7. Получить данные об объеме продаж указанного товара за некоторый период по всем торговым точкам, по торговым точкам заданного типа, по конкретной торговой точке.
select pi.product_article, count(*) * s.sale_count as "Количество продаж"
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.sale s on tpp.id = s.tpp_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where sell_date between '1900-01-01' and '2026-01-01'
  and tp_id = 1
  and tp_type = 'DEPARTMENT_STORE'
group by s.id, pi.product_article