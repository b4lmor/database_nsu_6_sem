-- 5. Получить данные о выработке на одного продавца за указанный период по всем торговым точкам, по торговым точкам заданного типа.
select seller_id, sum(pi.price * s.sale_count)
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.sale s on tpp.id = s.tpp_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where sell_date between '1900-01-01' and '2026-01-01'
  and tp_type = 'DEPARTMENT_STORE'
group by seller_id