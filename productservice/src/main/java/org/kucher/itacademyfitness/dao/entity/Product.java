package org.kucher.itacademyfitness.dao.entity;

import org.kucher.itacademyfitness.dao.entity.api.IProduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = " product")
public class Product implements IProduct {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "title")
    private String title;
    @Column(name = "weight")
    private int weight;
    @Column(name = "calories")
    private int calories;
    @Column(name = "fats")
    private double fats;
    @Column(name = "carbohydrates")
    private double carbohydrates;
    @Column(name = "proteins")
    private double proteins;

    public Product() {
    }

    public Product(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String title, int weight, int calories, double fats, double carbohydrates, double proteins) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.weight = weight;
        this.calories = calories;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.proteins = proteins;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return this.dtUpdate;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getCalories() {
        return this.calories;
    }

    @Override
    public double getFats() {
        return this.fats;
    }

    @Override
    public double getCarbohydrates() {
        return this.carbohydrates;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public double getProteins() {
        return this.proteins;
    }
}
