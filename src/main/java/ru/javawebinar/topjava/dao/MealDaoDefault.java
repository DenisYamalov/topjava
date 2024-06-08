package ru.javawebinar.topjava.dao;

public class MealDaoDefault {
    public static class InstanceHolder{
        public static final MealDao MEAL_DAO = new MealMapDao();
    }
    public static MealDao getInstance(){
        return InstanceHolder.MEAL_DAO;
    }
}
