package org.kucher.itacademyfitness.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.kucher.itacademyfitness.dao.entity.Product;
import org.kucher.itacademyfitness.dao.entity.Profile;
import org.kucher.itacademyfitness.dao.entity.Recipe;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class JournalFoodDTO {


    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;

    @NotNull(message = "Supply date cannot be null")
    private LocalDateTime dtSupply;
    private Recipe recipe;
    private Product product;
    @Min(value = 1, message = "Weight cannot be null")
    private int weight;
    @JsonIgnore
    private Profile profile;

    public JournalFoodDTO() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public LocalDateTime getDtSupply() {
        return dtSupply;
    }

    public void setDtSupply(LocalDateTime dtSupply) {
        this.dtSupply = dtSupply;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
