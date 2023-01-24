package org.kucher.itacademyfitness.dao.api;

import org.kucher.itacademyfitness.dao.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IRecipeDao extends JpaRepository<Recipe, UUID> {

}
