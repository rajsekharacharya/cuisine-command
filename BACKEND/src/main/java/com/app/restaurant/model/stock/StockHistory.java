package com.app.restaurant.model.stock;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.app.restaurant.audit.Auditable;
import com.app.restaurant.enums.StockActionType;
import com.app.restaurant.model.master.Unit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "stock_history")
@EntityListeners(AuditingEntityListener.class)
public class StockHistory extends Auditable<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn(name = "raw_material_id")
    private RawMaterial rawMaterial;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal quantityChanged;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal finalStock;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Unit unit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StockActionType actionType;

    private String reference;

    private LocalDateTime actionTime = LocalDateTime.now();
}
