package org.kucher.itacademyfitness.tests;

import java.util.HashMap;

public class RecipeServiceTest {

    public static void main(String[] args) {
        HashMap<String, Integer> warehouseToNumberOfTablesMap = new HashMap<>();
        warehouseToNumberOfTablesMap.put("warehouse1", 12);
        warehouseToNumberOfTablesMap.put("warehouse2", 31);

        HashMap<String, Integer> warehouseToNumberOfChairsMap = new HashMap<>();
        warehouseToNumberOfChairsMap.put("warehouse1", 22);
        warehouseToNumberOfChairsMap.put("warehouse2", 56);
        warehouseToNumberOfChairsMap.put("warehouse3", 35);

        HashMap<String, Integer> warehouseToNumberOfProductsMap = new HashMap<>(warehouseToNumberOfTablesMap);

        for (String warehouse : warehouseToNumberOfChairsMap.keySet()) {
            warehouseToNumberOfProductsMap.put(warehouse, warehouseToNumberOfProductsMap.get(warehouse) +
                    warehouseToNumberOfChairsMap.get(warehouse));
        }

        Integer numberOfWarehouse1Products = warehouseToNumberOfProductsMap.get("warehouse1");
    }
}
