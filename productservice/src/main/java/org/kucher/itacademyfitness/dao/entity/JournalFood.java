package org.kucher.itacademyfitness.dao.entity;

import org.kucher.itacademyfitness.dao.entity.api.IJournalFood;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name ="journal_food")
public class JournalFood implements IJournalFood {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "dt_supply")
    private LocalDateTime dtSupply;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "weight")
    private int weight;
    @ManyToOne
    @JoinColumn(name = "profile_uuid")
    private Profile profile;


    public JournalFood() {
    }

    public JournalFood(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, LocalDateTime dtSupply, Recipe recipe, Product product, int weight, Profile profile) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.dtSupply = dtSupply;
        this.recipe = recipe;
        this.product = product;
        this.weight = weight;
        this.profile = profile;
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
    public LocalDateTime getDtSupply() {
        return this.dtSupply;
    }

    @Override
    public Recipe getRecipe() {
        return this.recipe;
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public Profile getProfile() {
        return this.profile;
    }
}
