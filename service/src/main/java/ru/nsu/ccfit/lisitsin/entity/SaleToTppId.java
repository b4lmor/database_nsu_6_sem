package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

import java.io.Serializable;
import java.util.Objects;

@TableViewName(isVisible = false)
@Getter
@Setter
@Embeddable
public class SaleToTppId implements Serializable {
    private static final long serialVersionUID = -3397707252585944870L;
    @NotNull
    @Column(name = "sale_id", nullable = false)
    private Long saleId;

    @NotNull
    @Column(name = "tpp_id", nullable = false)
    private Long tppId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        SaleToTppId entity = (SaleToTppId) o;
        return Objects.equals(this.saleId, entity.saleId) &&
                Objects.equals(this.tppId, entity.tppId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, tppId);
    }

}