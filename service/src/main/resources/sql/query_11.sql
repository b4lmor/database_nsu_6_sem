-- 11. Получить данные о рентабельности торговой точки: соотношение объема продаж к накладным расходам (суммарная заработная плата продавцов + платежи за аренду, коммунальные услуги) за указанный период.
with sell_vol as (select sum(s.sale_count * pi.price)
                  from sale s
                           join public.trading_point_product tpp on s.tpp_id = tpp.id
                           join public.product_info pi on pi.id = tpp.product_info_id
                           join public.trading_point tp on tp.id = tpp.tp_id
                           join public.trading_point_building tpb on tpb.id = tp.tpb_id
                  where tp.id = 1
                  group by tp.id),

     months_between as (SELECT (EXTRACT(YEAR FROM age('2026-01-01', '2019-01-01')) * 12 +
                                EXTRACT(MONTH FROM age('2026-01-01', '2019-01-01'))))

select (select * from sell_vol) / ((sum(j.salary) + tp.rent_payment) * (select * from months_between)) as "рентабельность"
from trading_point tp
         join public.job j on tp.id = j.tp_id
         join public.trading_point_product t on tp.id = t.tp_id
         join public.sale s2 on t.id = s2.tpp_id
         join public.product_info pi on pi.id = t.product_info_id
where j.end_date is null
  and pi.sell_date between '2019-01-01' and '2026-01-01'
group by tp.id