# Отчёт о выполнении задания 5

1. Получить перечень и общее число поставщиков, поставляющих указанный вид товара, либо некоторый товар в объеме, не
   менее заданного за весь период сотрудничества, либо за указанный период.

```sql
select v
from vendor v
         join public.product_order po on v.id = po.vendor_id
         join public.product_order_details pod on po.id = pod.product_order_id
         join product p on pod.product_article = p.article
where article = 'furniture_10001'
  and delivery_date between '1900-01-01' and '2026-01-01'
group by v.id
having count(*) >= 1
```

2. Получить перечень и общее число покупателей, купивших указанный вид товара за некоторый период, либо сделавших
   покупку товара в объеме, не менее заданного.

```sql
select ci
from client_info ci
         join public.sale s on ci.id = s.client_info_id
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on pi.id = tpp.product_info_id
where pi.product_article = 'furniture_10001'
  and sell_date between '1900-01-01' and '2026-01-01'
group by ci.id
having count(*) >= 1
``` 

3. Получить номенклатуру и объем товаров в указанной торговой точке.

```sql
select p, pi, tpp
from product p
         join public.product_info pi on p.article = pi.product_article
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
where tpp.tp_id = 1
```

```sql
select p.article, count(*)
from product p
         join public.product_info pi on p.article = pi.product_article
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
where tpp.tp_id = 1
group by p.article
```

4. Получить сведения об объеме и ценах на указанный товар по всем торговым точкам, по торговым точкам заданного типа, по
   конкретной торговой точке.

```sql
select count(*), sum(pi.price)
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where product_article = 'furniture_10001'
  and tp.id = 1
  and tpb.tp_type = 'DEPARTMENT_STORE'
```

5. Получить данные о выработке на одного продавца за указанный период по всем торговым точкам, по торговым точкам
   заданного типа.

```sql
select seller_id, sum(pi.price * s.sale_count)
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.sale s on tpp.id = s.tpp_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where sell_date between '1900-01-01' and '2026-01-01'
  and tp_type = 'DEPARTMENT_STORE'
group by seller_id
```

6. Получить данные о выработке отдельно взятого продавца отдельно взятой торговой точки за указанный период.

```sql
select seller_id, sum(pi.price * s.sale_count)
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.sale s on tpp.id = s.tpp_id
where seller_id = 1
  and sell_date between '1900-01-01' and '2026-01-01'
  and tp_id = 1
group by seller_id
```

7. Получить данные об объеме продаж указанного товара за некоторый период по всем торговым точкам, по торговым точкам
   заданного типа, по конкретной торговой точке.

```sql
select pi.product_article, count(*) * s.sale_count as "Количество продаж"
from product_info pi
         join public.trading_point_product tpp on pi.id = tpp.product_info_id
         join public.sale s on tpp.id = s.tpp_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where sell_date between '1900-01-01' and '2026-01-01'
  and tp_id = 1
  and tp_type = 'DEPARTMENT_STORE'
group by s.id, pi.product_article
```

8. Получить данные о заработной плате продавцов по всем торговым точкам, по торговым точкам заданного типа, по
   конкретной торговой точке.

```sql
select j.employee_id, j.salary
from job j
         join public.trading_point tp on tp.id = j.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where j.job_title = 'CASHIER'
  and j.tp_id = 1
  and tp_type = 'DEPARTMENT_STORE'
```

9. Получить сведения о поставках определенного товара указанным поставщиком за все время поставок, либо за некоторый
   период.

```sql
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
```

10. Получить данные об отношении объема продаж к объему торговых площадей, либо к числу торговых залов, либо к числу
    прилавков по торговым точкам указанного типа, о выработке отдельно взятого продавца торговой точки, по заданной
    торговой точке.

```sql
select sum(s.sale_count) / sum(tp.tp_size) as "ОП/ОТП"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp.id = 1
group by tp.id
```

```sql
select sum(s.sale_count) / count(tp.id) as "ОП/ЧТЗ"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp_type = 'DEPARTMENT_STORE'
  and tpb_id = 1
group by tpb.id
```

```sql
select sum(s.sale_count) / count(tp.id) as "ОП/ЧП"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp_type = 'DEPARTMENT_STORE'
group by tpb.id
```

```sql
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
```

11. Получить данные о рентабельности торговой точки: соотношение объема продаж к накладным расходам (суммарная
    заработная плата продавцов + платежи за аренду, коммунальные услуги) за указанный период.

```sql
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
```

12. Получить сведения о поставках товаров по указанному номеру заказа.

```sql
select p.article, count(p.article)
from product p
         join public.product_order_details pod on p.article = pod.product_article
         join public.product_order po on po.id = pod.product_order_id
where po.id = 1
group by p.article 
```

13. Получить сведения о покупателях указанного товара за обозначенный, либо за весь период, по всем торговым точкам, по
    торговым точкам указанного типа, по данной торговой точке.

```sql
select p.article, ci, count(p.article) as "Кол-во покупок"
from client_info ci
         join public.sale s on ci.id = s.client_info_id
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on tpp.product_info_id = pi.id
         join public.product p on pi.product_article = p.article
where tp_id = 1
  and pi.sell_date between '2019-01-01' and '2026-01-01'
group by p.article, ci.id 
```

14. Получить сведения о наиболее активных покупателях по всем торговым точкам, по торговым точкам указанного типа, по
    данной торговой точке.

```sql
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
```

15. Получить данные о товарообороте торговой точки, либо всех торговых определенной группы за указанный период.                                                                                                                                      

```sql
select sum(s.sale_count * pi.price) as "Выручка", count(s.id) as "Кол-во сделок"
from sale s
         join public.trading_point_product tpp on s.tpp_id = tpp.id
         join public.product_info pi on pi.id = tpp.product_info_id
         join public.trading_point tp on tp.id = tpp.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where tp.id = 1
group by tp.id
```
