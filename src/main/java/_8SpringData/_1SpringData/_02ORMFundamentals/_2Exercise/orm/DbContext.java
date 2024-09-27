package _8SpringData._1SpringData._02ORMFundamentals._2Exercise.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {
    boolean persist(E entity) throws SQLException, IllegalAccessException;

    Iterable<E> find(Class<E> entity) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    Iterable<E> find(Class<E> entity, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    E findFirst(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void doCreate(Class<E> entityClass) throws SQLException;
    void doAlter(E entity) throws SQLException;
    boolean delete(E entity) throws SQLException, IllegalAccessException;
}
