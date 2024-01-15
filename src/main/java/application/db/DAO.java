package application.db;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface representing Data Access Object (DAO) operations.
 *
 * @param <T> The type of entity for which the DAO is implemented.
 */
public interface DAO<T> {

    /**
     * Retrieves a list of all entities.
     *
     * @return A list containing all entities.
     * @throws SQLException If an error occurs during database access.
     */
    List<T> getAll() throws SQLException;

    /**
     * Inserts a new entity into the database.
     *
     * @param t The entity to be inserted.
     * @throws SQLException If an error occurs during database access.
     */
    void insert(T t) throws SQLException;

    /**
     * Updates an existing entity in the database.
     *
     * @param t The entity to be updated.
     * @throws SQLException If an error occurs during database access.
     */
    void update(T t) throws SQLException;

    /**
     * Deletes an entity from the database.
     *
     * @param t The entity to be deleted.
     * @throws SQLException If an error occurs during database access.
     */
    void delete(T t) throws SQLException;

}
