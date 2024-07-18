package org.example.service.comparison;

import org.example.model.FieldToValueMap;
import org.example.model.ObjectIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ComparisonService {

    Logger logger = LoggerFactory.getLogger(ComparisonService.class);

    default <T extends ObjectIdentifier> Map<String, List<FieldToValueMap>> getComparisonTable(List<T> items, Class<T> classType) {
        Map<String, List<FieldToValueMap>> comparisonTable = new HashMap<>();

        // Get the fields of the class
        List<String> fields = getFields(classType);

        // Add features to the table
        for (String field : fields) {
            comparisonTable.put(field, new ArrayList<>());
        }

        for (T item : items) {
            for (String field : fields) {
                try {
                    Object value = classType.getMethod(getGetterName(field)).invoke(item);
                    comparisonTable.get(field).add(new FieldToValueMap(item.getObjectIdentifier(), String.valueOf(value)));
                } catch (Exception e) {
                    logger.error(String.format("Error getting field value: %s", e.getMessage()));
                }
            }
        }

        return comparisonTable;
    }

    default <T extends ObjectIdentifier> Map<String, List<FieldToValueMap>> getDifferenceTable(List<T> items, Class<T> classType) {
        Map<String, List<FieldToValueMap>> differenceTable = new HashMap<>();

        // Get the fields of the class
        List<String> fields = getFields(classType);

        // Add features to the table
        for (String field : fields) {
            differenceTable.put(field, new ArrayList<>());
        }

        T item = items.get(0);
        for (int j = 1; j < items.size(); j++) {
            T otherItem = items.get(j);
            for (String field : fields) {
                try {
                    Object value1 = classType.getMethod(getGetterName(field)).invoke(item);
                    Object value2 = classType.getMethod(getGetterName(field)).invoke(otherItem);
                    if (!value1.equals(value2)) {
                        int isItemAlreadyPresentInFieldMap = (int) differenceTable.get(field).stream().filter(f -> {
                            return Objects.equals(f.getField(), item.getObjectIdentifier());
                        }).count();
                        if(isItemAlreadyPresentInFieldMap == 0) {
                            differenceTable.get(field).add(new FieldToValueMap(item.getObjectIdentifier(), String.valueOf(value1)));
                        }
                        differenceTable.get(field).add(new FieldToValueMap(otherItem.getObjectIdentifier(), String.valueOf(value2)));
                    }
                } catch (Exception e) {
                    logger.error(String.format("Error getting field value: %s", e.getMessage()));
                }
            }
        }


        return differenceTable;
    }

    default List<String> getFields(Class<?> classType) {
        List<String> fields = new ArrayList<>();
        for (java.lang.reflect.Field field : classType.getDeclaredFields()) {
            fields.add(field.getName());
        }
        return fields;
    }

    default String getGetterName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }
}
