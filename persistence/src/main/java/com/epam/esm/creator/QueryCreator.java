package com.epam.esm.creator;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class {@code QueryCreator} designed to create a selection condition.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@Component
public class QueryCreator {

    /**
     * Method for creating a database query from {@link Map} to update entity information.
     *
     * @param fields    query parameters
     * @param tableName name of the table where the update will take place
     * @return generated string query
     */
    public String createUpdateQuery(Map<String, String> fields, String tableName) {
        StringJoiner joiner = new StringJoiner(",");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            if (entry.getValue() != null && !entry.getKey().equals("id")) {
                joiner.add(entry.getKey() + "=" + '\'' + entry.getValue() + '\'');
            }
        }
        joiner.add("last_update_date = now()");
        return "UPDATE " + tableName + " SET " + joiner + " WHERE id=" + fields.get("id");
    }

    // fixme change all if to methods
    /**
     * Method for creating a database query from {@link Map} to get entities.
     *
     * @param fields    query parameters
     * @param tableName name of the table where the update will take place
     * @return generated string query
     */
    public String createGetQuery(Map<String, String> fields, String tableName, String tableNameAs) {
        StringBuilder query = new StringBuilder("SELECT gc.id, gc.name as gift_name, duration, description, price,create_date, last_update_date, t.id as tag_id, t.name as tag_name FROM " + tableName);
        if (Objects.equals(tableName, "gift_certificate")) {
            query.append(" gc LEFT JOIN gift_certificate_tag gct ON gc.id=gct.gift_certificate_id LEFT JOIN tag t ON gct.tag_id=t.id");
        }
        if (fields.get("tag_name") != null) {
            addParameter(query, "tag_name", fields.get("tag_name"), tableNameAs);
        }
        if (fields.get("name") != null) {
            addParameter(query, "name", fields.get("name"), tableNameAs);
        }
        if (fields.get("partOfTagName") != null) {
            addPartParameter(query, "tag_name", fields.get("partOfTagName"), tableNameAs);
        }
        if (fields.get("partOfName") != null) {
            addPartParameter(query, "name", fields.get("partOfName"), tableNameAs);
        }
        if (fields.get("partOfDescription") != null) {
            addPartParameter(query, "description", fields.get("partOfDescription"), tableNameAs);
        }
        if (fields.get("sortByTagName") != null) {
            addSortParameter(query, "tag_name", fields.get("sortByTagName"), tableNameAs);
        }
        if (fields.get("sortByName") != null) {
            addSortParameter(query, "name", fields.get("sortByName"), tableNameAs);
        }
        if (fields.get("sortByCreateDate") != null) {
            addSortParameter(query, "create_date", fields.get("sortByCreateDate"), tableNameAs);
        }

        return query.toString();
    }

    public String createGetTagQuery(Map<String, String> fields, String tableName, String tableNameAs) {
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName + " as t");
        if (fields.get("tag_name") != null) {
            addParameter(query, "name", fields.get("tag_name"), tableNameAs);
        }
        if (fields.get("partOfTagName") != null) {
            addPartParameter(query, "name", fields.get("partOfTagName"), tableNameAs);
        }
        if (fields.get("sortByTagName") != null) {
            addSortParameter(query, "name", fields.get("sortByTagName"), tableNameAs);
        }

        return query.toString();
    }
    private void addParameter(StringBuilder query, String partParameter, String value, String tableNameAs) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(tableNameAs).append(partParameter).append("='").append(value).append('\'');
    }

    private void addPartParameter(StringBuilder query, String partParameter, String value, String tableNameAs) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(tableNameAs).append(partParameter).append(" LIKE '%").append(value).append("%'");
    }

    private void addSortParameter(StringBuilder query, String sortParameter, String value, String tableNameAs) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(tableNameAs).append(sortParameter).append(" ").append(value);
    }
}
