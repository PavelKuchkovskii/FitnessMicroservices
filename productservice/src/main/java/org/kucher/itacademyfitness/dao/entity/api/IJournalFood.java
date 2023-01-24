package org.kucher.itacademyfitness.dao.entity.api;

import org.kucher.itacademyfitness.dao.entity.Profile;

import java.time.LocalDateTime;

public interface IJournalFood extends IEssence{

    LocalDateTime getDtSupply();

    IRecipe getRecipe();

    IProduct getProduct();

    int getWeight();

    Profile getProfile();

}
