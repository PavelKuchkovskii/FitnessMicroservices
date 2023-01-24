package org.kucher.itacademyfitness.dao.entity;

import org.kucher.itacademyfitness.dao.entity.api.IComposition;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "composition")
public class Composition implements IComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Product cannot be null")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Min(value = 1, message = "Weight cannot be null")
    @Column(name = "weight")
    private int weight;

    public Composition() {
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

}
