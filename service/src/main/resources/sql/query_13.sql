-- 13. Получить сведения о покупателях указанного товара за обозначенный, либо за весь период, по всем торговым точкам, по торговым точкам указанного типа, по данной торговой точке.
select p.article, ci, count(p.article) as "Кол-во покупок"
from client_info ci
         join public.sale s on ci.id = s.client_info_id
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on tpp.product_info_id = pi.id
         join public.product p on pi.product_article = p.article
where tp_id = 1
  and pi.sell_date between '2019-01-01' and '2026-01-01'
group by p.article, ci.id