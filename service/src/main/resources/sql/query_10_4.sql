-- 10.4 Получить данные об отношении объема продаж к выработке отдельно взятого продавца торговой точки, по заданной торговой точке.
with sell_vol as (select sum(s.sale_count * pi.price)
                  from sale s
                           join public.trading_point_product tpp on s.tpp_id = tpp.id
                           join public.product_info pi on pi.id = tpp.product_info_id
                           join public.trading_point tp on tp.id = tpp.tp_id
                           join public.trading_point_building tpb on tpb.id = tp.tpb_id
                  where tp.id = 1
                  group by tp.id)

select (select * from sell_vol) / sum(s.sale_count * pi.price) as "ОП/ВП"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on pi.id = tpp.product_info_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp.id = 1
  and s.seller_id = 1
group by tp.id