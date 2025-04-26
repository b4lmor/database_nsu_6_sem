-- 10.3 Получить данные об отношении объема продаж к числу прилавков по торговым точкам указанного типа, по заданной торговой точке.
select sum(s.sale_count) / count(tp.id) as "ОП/ЧП"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp_type = 'DEPARTMENT_STORE'
group by tpb.id