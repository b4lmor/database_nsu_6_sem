-- 8. Получить данные о заработной плате продавцов по всем торговым точкам, по торговым точкам заданного типа, по конкретной торговой точке.
select j.employee_id, j.salary
from job j
         join public.trading_point tp on tp.id = j.tp_id
         join public.trading_point_building tpb on tpb.id = tp.tpb_id
where j.job_title = 'CASHIER'
  and j.tp_id = 1
  and tp_type = 'DEPARTMENT_STORE'