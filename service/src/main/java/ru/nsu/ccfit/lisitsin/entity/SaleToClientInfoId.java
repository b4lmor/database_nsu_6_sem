package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SaleToClientInfoId implements Serializable {
    private static final long serialVersionUID = -5299367102426843930L;
    @NotNull
    @Column(name = "sale_id", nullable = false)
    private Long saleId;

    @NotNull
    @Column(name = "client_info_id", nullable = false)
    private Long clientInfoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        SaleToClientInfoId entity = (SaleToClientInfoId) o;
        return Objects.equals(this.saleId, entity.saleId) &&
                Objects.equals(this.clientInfoId, entity.clientInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, clientInfoId);
    }

}