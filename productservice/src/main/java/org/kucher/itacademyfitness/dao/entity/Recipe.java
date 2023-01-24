package org.kucher.itacademyfitness.dao.entity;

import org.hibernate.annotations.Cascade;
import org.kucher.itacademyfitness.dao.entity.api.IRecipe;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipe")
public class Recipe implements IRecipe {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "title")
    private String title;

    @OneToMany
    @JoinColumn(name = "recipe_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Composition> composition;

    public Recipe() {
    }

    public Recipe(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String title, List<Composition> composition) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
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
    public List<Composition> getComposition() {
        return this.composition;
    }
}
