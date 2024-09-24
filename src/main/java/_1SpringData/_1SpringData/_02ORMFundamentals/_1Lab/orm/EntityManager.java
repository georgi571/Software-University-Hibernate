package _1SpringData._1SpringData._02ORMFundamentals._1Lab.orm;

import _1SpringData._1SpringData._02ORMFundamentals._1Lab.orm.anotations.Column;
import _1SpringData._1SpringData._02ORMFundamentals._1Lab.orm.anotations.Entity;
import _1SpringData._1SpringData._02ORMFundamentals._1Lab.orm.anotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EntityManager<E> implements DbContext<E>{
    private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String UPDATE_WHIT_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s";
    private static final String SELECT_WHIT_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s %s";

    
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);
        if (idValue == null || (long)idValue == 0) {
            return doInsert(entity, INSERT_TEMPLATE);
        } else {
            return doUpdate(entity, idColumn, idValue);
        }
    }

    private boolean doInsert(E entity, String insertStatement) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columnList = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        String formattedInsert = String.format(insertStatement, tableName, String.join(", ", columnList), String.join(", ", values));

        PreparedStatement statement = connection.prepareStatement(formattedInsert);
        int changedRows = statement.executeUpdate();

        return changedRows == 1;
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return find(table, null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return baseFind(table, where, null);
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<E> result = baseFind(table, where, 1);

        if (result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    private List<E> baseFind(Class<E> table, String where, Integer limit) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String fieldList = "*";
        String tableName = getTableName(table);
        String whereClause;
        String limitClause;

        if (where == null) {
            whereClause = "";
        } else {
            whereClause = "WHERE " + where;
        }

        if (limit == null) {
            limitClause = "";
        } else {
            limitClause = "LIMIT " + limit;
        }

        String selectStatement = String.format(SELECT_WHIT_WHERE_PLACEHOLDER_TEMPLATE, fieldList, tableName, whereClause, limitClause);
        PreparedStatement preparedStatement = connection.prepareStatement(selectStatement);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<E> result = new ArrayList<>();
        while (resultSet.next()) {
            E current = generateEntity(table, resultSet);
            result.add(current);
        }

        return result;
    }

    private String getTableName(E entity) {
        Entity annotation = entity.getClass().getAnnotation(Entity.class);
        if (annotation == null) {
            throw new RuntimeException("No Entity annotation present");
        }

        return annotation.name();
    }

    private String getTableName(Class<E> clazz) {
        Entity annotation = clazz.getAnnotation(Entity.class);
        if (annotation == null) {
            throw new RuntimeException("No Entity annotation present");
        }

        return annotation.name();
    }

    private List<String> getColumnsWithoutId(E entity) {
        List<String> fields = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }

            if (!declaredField.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column = declaredField.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }

            fields.add(column.name());
        }
        return fields;
    }

    private List<String> getColumnValuesWithoutId(E entity) throws IllegalAccessException {
        List<String> values = new ArrayList<>();
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                continue;
            }

            if (!declaredField.isAnnotationPresent(Column.class)) {
                continue;
            }

            Column column = declaredField.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }
            declaredField.setAccessible(true);
            Object fieldValue = declaredField.get(entity);

            values.add("'" + fieldValue.toString() + "'");
        }
        return values;
    }

    private Field getIdColumn(E entity) {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Id.class)) {
                return declaredField;
            }
        }
        throw new RuntimeException("Entity has no Id column");
    }

    private boolean doUpdate(E entity, Field idColumn, Object idValue) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity);
        List<String> columns = getColumnsWithoutId(entity);
        List<String> values = getColumnValuesWithoutId(entity);

        List<String> columnWithValues = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            String columnsAndValues = columns.get(i) + "=" + values.get(i);

            columnWithValues.add(columnsAndValues);
        }

        String idCondition = String.format("%s = %s", idColumn.getName(), idValue.toString());

        String updateQuery = String.format(UPDATE_WHIT_WHERE_TEMPLATE, tableName, String.join(", ",columnWithValues), idCondition);

        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
        int updateCount = preparedStatement.executeUpdate();

        return updateCount == 1;
    }

    private E generateEntity(Class<E> table, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        E result = table.getDeclaredConstructor().newInstance();

        Field[] declaredFields = table.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            fillField(result, declaredField, resultSet);
        }

        return result;
    }

    private void fillField(E result, Field declaredField, ResultSet resultSet) throws SQLException, IllegalAccessException {
        declaredField.setAccessible(true);

        String dbFieldName = declaredField.getAnnotation(Column.class).name();
        Class<?> javaType = declaredField.getType();

        if (javaType == int.class || javaType == Integer.class) {
            int value = resultSet.getInt(dbFieldName);
            declaredField.setInt(result, value);
        } else if (javaType == long.class || javaType == Long.class) {
            long value = resultSet.getLong(dbFieldName);
            declaredField.setLong(result, value);
        } else if (javaType == LocalDate.class) {
            LocalDate value = resultSet.getObject(dbFieldName, LocalDate.class);
            declaredField.set(result, value);
        } else if (javaType == String.class) {
            String value = resultSet.getString(dbFieldName);
            declaredField.set(result, value);
        } else {
            throw new RuntimeException("Unsupported type " + javaType);
        }
    }
}
