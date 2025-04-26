-- 10.1 Получить данные об отношении объема продаж к объему торговых площадей по торговым точкам указанного типа, по заданной торговой точке.
select sum(s.sale_count) / sum(tp.tp_size) as "ОП/ОТП"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp.id = 1
group by tp.id