package org.kucher.itacademyfitness.dao.entity.builders;

import org.kucher.itacademyfitness.dao.entity.Composition;
import org.kucher.itacademyfitness.dao.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RecipeBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String title;
    private List<Composition> composition;

    private RecipeBuilder() {

    }

    public static RecipeBuilder create() {
        return new RecipeBuilder();
    }

    public RecipeBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public RecipeBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public RecipeBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public RecipeBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public RecipeBuilder setComposition(List<Composition> composition) {
        this.composition = composition;
        return this;
    }

    public Recipe build() {
        return new Recipe(uuid, dtCreate, dtUpdate, title, composition);
    }
}
