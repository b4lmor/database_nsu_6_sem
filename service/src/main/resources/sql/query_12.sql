-- 12. Получить сведения о поставках товаров по указанному номеру заказа.
select p.article, count(p.article)
from product p
         join public.product_order_details pod on p.article = pod.product_article
         join public.product_order po on po.id = pod.product_order_id
where po.id = 1
group by p.article