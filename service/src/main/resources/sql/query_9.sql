-- 9. Получить сведения о поставках определенного товара указанным поставщиком за все время поставок, либо за некоторый период.
select p.article, count(*), sum(pod.product_count * pi.price)
from product p
         join public.product_order_details pod on p.article = pod.product_article
         join public.product_order po on po.id = pod.product_order_id
         join public.vendor_product vp on po.vendor_id = vp.vendor_id
         join public.product_info pi on pi.id = vp.product_info_id
where p.article = 'furniture_10001'
  and vp.vendor_id = 1
  and po.delivery_date between '1900-01-01' and '2026-01-01'
group by p.article