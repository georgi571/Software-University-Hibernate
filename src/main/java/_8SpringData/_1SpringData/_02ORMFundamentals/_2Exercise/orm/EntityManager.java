package _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm;

import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Column;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Entity;
import _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm.anotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private static final String INSERT_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s);";
    private static final String UPDATE_WHIT_WHERE_TEMPLATE = "UPDATE %s SET %s WHERE %s;";
    private static final String SELECT_WHIT_WHERE_PLACEHOLDER_TEMPLATE = "SELECT %s FROM %s %s %s;";
    private static final String CREATE_TABLE_TEMPLATE = "CREATE TABLE %s(%s);";
    private static final String ALTER_TABLE_TEMPLATE = "ALTER TABLE %s ADD COLUMN %s;";
    private static final String EXISTING_SQL_TEMPLATE = "SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = 'mini_orm' AND TABLE_NAME = ?";
    private static final String DELETE_TEMPLATE = "DELETE FROM %s WHERE id = ?";
    private static final String EXISTING_TABLES_IN_DATABASE_TEMPLATE = "SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'mini_orm';";


    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws SQLException, IllegalAccessException {
        Field idColumn = getIdColumn(entity);
        idColumn.setAccessible(true);
        Object idValue = idColumn.get(entity);
        if (idValue == null || (long) idValue == 0) {
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

    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        List<String> tableNamesInDB = getAllTablesInDB();
        if (tableNamesInDB.contains(tableName)) {
            System.out.printf("This table already exists in Data Base%n");
        } else {
            String sql = String.format(CREATE_TABLE_TEMPLATE, tableName, getAllFieldsAndDataTypes(entityClass));
            this.connection.prepareStatement(sql).execute();
        }
    }

    @Override
    public void doAlter(E entity) throws SQLException {
        String tableName = getTableName(entity);
        String newColumns = getColumnsNotExistingInTable(entity);
        if (!newColumns.isEmpty()) {
            String sql = String.format(ALTER_TABLE_TEMPLATE, tableName, newColumns);
            this.connection.prepareStatement(sql).execute();
        } else {
            System.out.printf("This column already exists%n");
        }
    }

    @Override
    public boolean delete(E entity) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity);
        PreparedStatement preparedStatement = this.connection.prepareStatement(String.format(DELETE_TEMPLATE, tableName));
        Field fieldId = Arrays.stream(entity.getClass().getDeclaredFields()).filter(field -> field.getName().equals("id")).findFirst().get();
        fieldId.setAccessible(true);
        long id = fieldId.getLong(entity);
        preparedStatement.setLong(1, id);
        int deletedRows = preparedStatement.executeUpdate();
        if (deletedRows == 0) {
            System.out.printf("No rows deleted%n");
            return false;
        } else {
            System.out.printf("%d rows deleted from table%n", deletedRows);
            return true;
        }
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

        String updateQuery = String.format(UPDATE_WHIT_WHERE_TEMPLATE, tableName, String.join(", ", columnWithValues), idCondition);

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

    private String getAllFieldsAndDataTypes(Class<E> entityClass) {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] declaredFields = entityClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            stringBuilder.append(String.format("%s %s", getFieldName(declaredField), getFieldType(declaredField)));
            if (declaredField.isAnnotationPresent(Id.class)) {
                stringBuilder.append(" PRIMARY KEY AUTO_INCREMENT");
            }
            stringBuilder.append(",");
        }
        return stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
    }

    private String getFieldName(Field declaredField) {
        return declaredField.getAnnotation(Column.class).name();
    }

    private String getFieldType(Field declaredField) {
        String simpleName = declaredField.getType().getSimpleName();
        if (simpleName.equals("int") || simpleName.equals("Integer")) {
            return "INT";
        } else if (simpleName.equals("long") || simpleName.equals("LONG")) {
            return "BIGINT";
        } else if (simpleName.equals("String")) {
            return "VARCHAR(255)";
        } else if (simpleName.equals("double") || simpleName.equals("Double")) {
            return "DOUBLE";
        } else if (simpleName.equals("LocalDate")) {
            return "DATE";
        }
        return null;
    }

    private List<String> getExistingColumns(E entity) throws SQLException {
        List<String> existingColumns = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(EXISTING_SQL_TEMPLATE);
        preparedStatement.setString(1, getTableName(entity));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            existingColumns.add(resultSet.getString(1));
        }
        return existingColumns;
    }

    private String getColumnsNotExistingInTable(E entity) throws SQLException {
        List<String> existingColumns = getExistingColumns(entity);
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !existingColumns.contains(field.getAnnotation(Column.class).name()))
                .map(field -> String.format("%s %s", getFieldName(field), getFieldType(field)))
                .collect(Collectors.joining(", "));
    }

    private List<String> getAllTablesInDB() throws SQLException {
        List<String> existingTables = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(EXISTING_TABLES_IN_DATABASE_TEMPLATE);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            existingTables.add(resultSet.getString(1));
        }
        return existingTables;
    }
}
