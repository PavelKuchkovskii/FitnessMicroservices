package org.kucher.itacademyfitness.dao.entity.api;

import org.kucher.itacademyfitness.dao.entity.Composition;

import java.util.List;

public interface IRecipe extends IEssence{

    String getTitle();

    List<Composition> getComposition();
}
