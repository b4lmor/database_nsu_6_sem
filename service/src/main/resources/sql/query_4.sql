-- 4. Получить сведения об объеме и ценах на указанный товар по всем торговым точкам, по торговым точкам заданного типа, по конкретной торговой точке.
select count(*), sum(pi.price)
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where product_article = 'furniture_10001'
  and tp.id = 1
  and tpb.tp_type = 'DEPARTMENT_STORE'