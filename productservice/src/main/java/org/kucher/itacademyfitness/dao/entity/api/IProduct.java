package org.kucher.itacademyfitness.dao.entity.api;

public interface IProduct extends IEssence{

    String getTitle();
    int getWeight();
    int getCalories();
    double getFats();
    double getCarbohydrates();
    double getProteins();


}
