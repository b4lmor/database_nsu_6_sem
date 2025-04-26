-- 15. Получить данные о товарообороте торговой точки, либо всех торговых определенной группы за указанный период.
select sum(s.sale_count * pi.price) as "Выручка", count(s.id) as "Кол-во сделок"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on pi.id = tpp.product_info_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp.id = 1
group by tp.id