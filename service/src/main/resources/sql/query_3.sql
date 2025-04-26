-- 3 Получить номенклатуру и объем товаров в указанной торговой точке.
select p.article, count(*)
from product p
         join public.product_info pi on p.article = pi.product_article
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
where tpp.tp_id = 1
group by p.article