# ЛОГИЧЕСКОЕ ПРОЕКТИРОВАНИЕ БАЗЫ ДАННЫХ, ХРАНИМЫЕ ПРОЦЕДУРЫ И ТРИГГЕРЫ

## Логическое проектирование базы данных

[**SQL файл с созданием таблиц**](../../database/scripts/migrations/V0001__init_tables.sql)

[**SQL файл с созданием ролей и их прав**](../../database/scripts/migrations/V0002__init_roles.sql)

[**SQL файл с созданием ограничений, триггеров и процедур**](../../database/scripts/migrations/V0004__constraints_and_triggers.sql)


### **`trading_point_building`**

| Атрибут | Тип                | Ограничения      |
|---------|--------------------|------------------|
| id      | SERIAL             | PRIMARY KEY      |
| address | VARCHAR(120)       | NOT NULL, UNIQUE |
| tp_type | trading_point_type | NOT NULL         |
| name    | VARCHAR(50)        | NOT NULL         |

**Primary Key:**`id`

**Foreign Keys:** Нет

---

### **`employee`**

| Атрибут          | Тип            | Ограничения                             |
|------------------|----------------|-----------------------------------------|
| id               | SERIAL         | PRIMARY KEY                             |
| manager_id       | INTEGER        | REFERENCES employee (id)                |
| job_title        | job_title_type | NOT NULL                                |
| tp_id            | INTEGER        | NOT NULL, REFERENCES trading_point (id) |
| full_name        | VARCHAR(100)   | NOT NULL                                |
| birth_date       | DATE           | NOT NULL                                |
| hire_date        | DATE           | NOT NULL, DEFAULT NOW()                 |
| resignation_date | DATE           |                                         |

**Primary Key:**`id`

**Foreign Keys:**

- `manager_id`→`employee (id)`

- `tp_id`→`trading_point (id)`

**Check Constraints:**

- `resignation_date >= hire_date` (if `resignation_date` not `NULL`).

---

### **`trading_point`**

| Атрибут      | Тип            | Ограничения                            |
|--------------|----------------|----------------------------------------|
| id           | SERIAL         | PRIMARY KEY                            |
| tpb_id       | INTEGER        | REFERENCES trading_point_building (id) |
| manager_id   | INTEGER        | NOT NULL, REFERENCES employee (id)     |
| name         | VARCHAR(50)    | NOT NULL                               |
| rent_payment | NUMERIC(10, 2) | NOT NULL                               |
| tp_size      | NUMERIC(10, 2) | NOT NULL                               |

**Primary Key:**`id`

**Foreign Keys:**

- `tpb_id`→`trading_point_building (id)`

- `manager_id`→`employee (id)`  
  **Unique Constraint:**`(tpb_id, name)`

---

### **`department`**

| Атрибут    | Тип         | Ограничения                                      |
|------------|-------------|--------------------------------------------------|
| id         | SERIAL      | PRIMARY KEY                                      |
| tpb_id     | INTEGER     | NOT NULL, REFERENCES trading_point_building (id) |
| name       | VARCHAR(50) | NOT NULL                                         |
| floor      | INTEGER     | NOT NULL                                         |
| manager_id | INTEGER     | NOT NULL, REFERENCES employee (id)               |

**Primary Key:**`id`

**Foreign Keys:**

- `tpb_id`→`trading_point_building (id)`

- `manager_id`→`employee (id)`  
  **Unique Constraint:**`(tpb_id, name)`

---

### **`department_to_trading_point`**

| Атрибут       | Тип     | Ограничения                           |
|---------------|---------|---------------------------------------|
| tp_id         | INTEGER | REFERENCES trading_point (id), UNIQUE |
| department_id | INTEGER | REFERENCES department (id)            |

**Primary Key:**`(tp_id, department_id)`

**Foreign Keys:**

- `tp_id`→`trading_point (id)`

- `department_id`→`department (id)`

---

### **`product`**

| Атрибут     | Тип         | Ограничения |
|-------------|-------------|-------------|
| article     | VARCHAR(60) | PRIMARY KEY |
| name        | VARCHAR(60) |             |
| description | TEXT        |             |
| photo_url   | TEXT        |             |

**Primary Key:**`article`

**Foreign Keys:** Нет

---

### **`product_info`**

| Атрибут         | Тип            | Ограничения                            |
|-----------------|----------------|----------------------------------------|
| id              | BIGSERIAL      | PRIMARY KEY                            |
| product_article | VARCHAR(60)    | NOT NULL, REFERENCES product (article) |
| delivery_date   | TIMESTAMP      | NOT NULL                               |
| sell_date       | TIMESTAMP      |                                        |
| price           | NUMERIC(10, 2) | NOT NULL                               |

**Primary Key:**`id`

**Foreign Keys:**

- `product_article`→`product (article)`

**Check Constraints:**

- `price >= 0`

- `sell_date >= delivery_date` (if `sell_date` not `NULL`).

---

### **`trading_point_product`**

| Атрибут         | Тип       | Ограничения                                              |
|-----------------|-----------|----------------------------------------------------------|
| id              | BIGSERIAL | PRIMARY KEY                                              |
| tp_id           | INTEGER   | NOT NULL, REFERENCES trading_point (id)                  |
| product_info_id | BIGINT    | NOT NULL, REFERENCES product_info (id) ON DELETE CASCADE |

**Primary Key:**`id`

**Foreign Keys:**

- `tp_id`→`trading_point (id)`

- `product_info_id`→`product_info (id)`**ON DELETE CASCADE**

---

### **`sale`**

| Атрибут    | Тип       | Ограничения                             |
|------------|-----------|-----------------------------------------|
| id         | BIGSERIAL | PRIMARY KEY                             |
| tp_id      | INTEGER   | NOT NULL, REFERENCES trading_point (id) |
| created_at | TIMESTAMP | DEFAULT NOW()                           |

**Primary Key:**`id`

**Foreign Keys:**

- `tp_id`→`trading_point (id)`

---

### **`sale_to_tpp`**

| Атрибут | Тип    | Ограничения                            |
|---------|--------|----------------------------------------|
| sale_id | BIGINT | REFERENCES sale (id) ON DELETE CASCADE |
| tpp_id  | BIGINT | REFERENCES trading_point_product (id)  |

**Primary Key:**`(sale_id, tpp_id)`

**Foreign Keys:**

- `sale_id`→`sale (id)`**ON DELETE CASCADE**

- `tpp_id`→`trading_point_product (id)`

---

### **`client_info`**

| Атрибут     | Тип           | Ограничения |
|-------------|---------------|-------------|
| id          | BIGSERIAL     | PRIMARY KEY |
| full_name   | VARCHAR(100)  |             |
| birth_date  | DATE          |             |
| height      | NUMERIC(4, 1) |             |
| weight      | NUMERIC(5, 2) |             |
| specificity | TEXT          |             |
| phone       | VARCHAR(15)   |             |
| email       | VARCHAR(50)   |             |

**Primary Key:**`id`

**Foreign Keys:** Нет

**Check Constraints:**

- `height > 0 AND height <= 300`

- `weight > 0 AND weight <= 500`

- `email` должен соответствовать формату `^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$`

**Unique Constraints:**

- `phone` (уникальный номер телефона).

- `email` (уникальный email).

---

### **`sale_to_client_info`**

| Атрибут        | Тип    | Ограничения                                   |
|----------------|--------|-----------------------------------------------|
| sale_id        | BIGINT | REFERENCES sale (id), UNIQUE                  |
| client_info_id | BIGINT | REFERENCES client_info (id) ON DELETE CASCADE |

**Primary Key:**`(sale_id, client_info_id)`

**Foreign Keys:**

- `sale_id`→`sale (id)`

- `client_info_id`→`client_info (id)`**ON DELETE CASCADE**

---

### **`vendor`**

| Атрибут | Тип          | Ограничения |
|---------|--------------|-------------|
| id      | SERIAL       | PRIMARY KEY |
| name    | VARCHAR(50)  | NOT NULL    |
| address | VARCHAR(120) | NOT NULL    |

**Primary Key:**`id`

**Foreign Keys:** Нет

**Unique Constraint:**`(name, address)`

---

### **`vendor_product`**

| Атрибут         | Тип       | Ограничения                                              |
|-----------------|-----------|----------------------------------------------------------|
| id              | BIGSERIAL | PRIMARY KEY                                              |
| vendor_id       | INTEGER   | NOT NULL, REFERENCES vendor (id) ON DELETE CASCADE       |
| product_info_id | BIGINT    | NOT NULL, REFERENCES product_info (id) ON DELETE CASCADE |

**Primary Key:**`id`

**Foreign Keys:**

- `vendor_id`→`vendor (id)`**ON DELETE CASCADE**

- `product_info_id`→`product_info (id)`**ON DELETE CASCADE**

---

### **`product_order`**

| Атрибут       | Тип                       | Ограничения                                         |
|---------------|---------------------------|-----------------------------------------------------|
| id            | BIGSERIAL                 | PRIMARY KEY                                         |
| manager_id    | INTEGER                   | REFERENCES employee (id)                            |
| vendor_id     | INTEGER                   | NOT NULL, REFERENCES vendor (id) ON DELETE SET NULL |
| tp_id         | INTEGER                   | NOT NULL, REFERENCES trading_point (id)             |
| order_status  | product_order_status_type | NOT NULL, DEFAULT 'ORDERED'                         |
| create_date   | TIMESTAMP                 | NOT NULL, DEFAULT NOW()                             |
| confirm_date  | TIMESTAMP                 |                                                     |
| delivery_date | TIMESTAMP                 |                                                     |

**Primary Key:**`id`

**Foreign Keys:**

- `manager_id`→`employee (id)`

- `vendor_id`→`vendor (id)`**ON DELETE SET NULL**

- `tp_id`→`trading_point (id)`

**Check Constraints:**

- `confirm_date >= create_date` (if `confirm_date` not `NULL`).

- `delivery_date >= confirm_date` (if `delivery_date` and `confirm_date` not `NULL`).

---

### **`product_order_details`**

| Атрибут          | Тип         | Ограничения                                               |
|------------------|-------------|-----------------------------------------------------------|
| product_order_id | BIGINT      | NOT NULL, REFERENCES product_order (id) ON DELETE CASCADE |
| product_id       | VARCHAR(60) | NOT NULL, REFERENCES product (article)                    |
| product_count    | INTEGER     | NOT NULL                                                  |

**Primary Key:**`(product_order_id, product_id)`

**Foreign Keys:**

- `product_order_id`→`product_order (id)`**ON DELETE CASCADE**

- `product_id`→`product (article)`

**Check Constraints:**

- `product_count >= 0`

## Хранимые процедуры и триггеры

[**SQL файл с созданием триггеров и процедур**](../../database/scripts/migrations/V0004__constraints_and_triggers.sql)

### Описание процедур

| №     | Операция                    | Описание                                                                     |
|-------|-----------------------------|------------------------------------------------------------------------------|
| 1     | BEFORE UPDATE               | Обновляет статус заказа на "CONFIRMED" или "DELIVERED" в зависимости от дат. |
| 2     | AFTER INSERT                | Обновляет дату продажи товара после добавления записи о продаже.             |
| ~~3~~ | ~~BEFORE INSERT OR UPDATE~~ | ~~Проверяет, что менеджер торговой точки является её сотрудником.~~          |
| 4     | BEFORE INSERT OR UPDATE     | Проверяет, что отдел принадлежит зданию торговой точки.                      |
| 5     | BEFORE INSERT OR UPDATE     | Проверяет, что у поставщика достаточно товара для выполнения заказа.         |
| 6     | BEFORE INSERT OR UPDATE     | Проверяет, что торговая точка, привязанная к секции, является универмагом.   |
