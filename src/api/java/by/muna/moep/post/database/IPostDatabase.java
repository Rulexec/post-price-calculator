package by.muna.moep.post.database;

import by.muna.moep.post.database.models.IDBPostFormula;
import by.muna.moep.post.database.models.IDBPostType;

import java.util.List;

public interface IPostDatabase {
    List<IDBPostType> getAllTypes() throws PostDatabaseException;
    IDBPostType getPostType(int id, boolean deep) throws PostDatabaseException;
    IDBPostFormula getFormula(String slug) throws PostDatabaseException;

    default IDBPostType getPostType(int id) throws PostDatabaseException {
        return this.getPostType(id, true);
    }
}
