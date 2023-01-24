package org.kucher.itacademyfitness.dao.entity.builders;

import org.kucher.itacademyfitness.dao.entity.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProductBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String title;
    private int weight;
    private int calories;
    private double fats;
    private double carbohydrates;
    private double proteins;

    private ProductBuilder() {

    }

    public static ProductBuilder create() {
        return new ProductBuilder();
    }

    public ProductBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ProductBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public ProductBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public ProductBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductBuilder setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public ProductBuilder setCalories(int calories) {
        this.calories = calories;
        return this;
    }

    public ProductBuilder setFats(double fats) {
        this.fats = fats;
        return this;
    }

    public ProductBuilder setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
        return this;
    }

    public ProductBuilder setProteins(double proteins) {
        this.proteins = proteins;
        return this;
    }

    public Product build() {
        return new Product(uuid, dtCreate, dtUpdate, title, weight, calories, fats, carbohydrates, proteins);
    }
}
