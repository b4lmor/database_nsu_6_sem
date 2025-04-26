-- 1. Получить перечень и общее число поставщиков, поставляющих указанный вид товара, либо некоторый товар в объеме, не менее заданного за весь период сотрудничества, либо за указанный период.
select v
from vendor v
         join public.product_order po on v.id = po.vendor_id
         join public.product_order_details pod on po.id = pod.product_order_id
         join product p on pod.product_article = p.article
where article = 'furniture_10001'
  and delivery_date between '1900-01-01' and '2026-01-01'
group by v.id
having count(*) >= 1