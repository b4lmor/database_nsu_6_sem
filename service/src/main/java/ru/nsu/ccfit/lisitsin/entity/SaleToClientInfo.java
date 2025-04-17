package ru.nsu.ccfit.lisitsin.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.utils.TableViewName;

@TableViewName(isVisible = false)
@Getter
@Setter
@Entity
@Table(name = "sale_to_client_info")
public class SaleToClientInfo {
    @EmbeddedId
    private SaleToClientInfoId id;

    @MapsId("saleId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @MapsId("clientInfoId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_info_id", nullable = false)
    private ClientInfo clientInfo;

}