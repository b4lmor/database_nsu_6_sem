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
public class DepartmentToTradingPointId implements Serializable {
    private static final long serialVersionUID = 3061941939225936557L;
    @NotNull
    @Column(name = "tp_id", nullable = false)
    private Integer tpId;

    @NotNull
    @Column(name = "department_id", nullable = false)
    private Integer departmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        DepartmentToTradingPointId entity = (DepartmentToTradingPointId) o;
        return Objects.equals(this.tpId, entity.tpId) &&
                Objects.equals(this.departmentId, entity.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tpId, departmentId);
    }

}