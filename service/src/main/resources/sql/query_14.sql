-- 14. Получить сведения о наиболее активных покупателях по всем торговым точкам, по торговым точкам указанного типа, по данной торговой точке.
select ci, count(s.id) as "Количество покупок"
from client_info ci
         join public.sale s on ci.id = s.client_info_id
         join public.trading_point_product tpp on tpp.id = s.tpp_id
         join public.trading_point tp on tpp.tp_id = tp.id
         join public.trading_point_building tpb on tp.tpb_id = tpb.id
where tp_type = 'DEPARTMENT_STORE'
  and tp.id = 1
group by ci.id
order by count(s.id) desc