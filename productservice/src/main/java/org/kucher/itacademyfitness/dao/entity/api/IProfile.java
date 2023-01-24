package org.kucher.itacademyfitness.dao.entity.api;

import org.kucher.itacademyfitness.dao.entity.User;
import org.kucher.itacademyfitness.dao.entity.enums.EActivityType;
import org.kucher.itacademyfitness.dao.entity.enums.ESex;

import java.util.Date;

public interface IProfile extends IEssence{

    int getHeight();
    double getWeight();
    Date getDtBirthday();
    double getTarget();
    EActivityType getActivityType();
    ESex getSex();
    User getUser();
}
