package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductOrderDetailId implements Serializable {
    private static final long serialVersionUID = -7341731716690166660L;
    @NotNull
    @Column(name = "product_order_id", nullable = false)
    private Long productOrderId;

    @Size(max = 60)
    @NotNull
    @Column(name = "product_article", nullable = false, length = 60)
    private String productArticle;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        ProductOrderDetailId entity = (ProductOrderDetailId) o;
        return Objects.equals(this.productOrderId, entity.productOrderId) &&
                Objects.equals(this.productArticle, entity.productArticle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productOrderId, productArticle);
    }

}