package by.muna.moep.post.database;

import by.muna.moep.post.database.models.IDBPostFormula;
import by.muna.moep.post.database.models.IDBPostType;

import java.util.List;

public class PostJSONDatabase implements IPostDatabase {
    public PostJSONDatabase(String json) {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public List<IDBPostType> getAllTypes() throws PostDatabaseException {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public IDBPostType getPostType(int id, boolean deep) throws PostDatabaseException {
        throw new RuntimeException("Not implemented yet.");
    }

    @Override
    public IDBPostFormula getFormula(String slug) throws PostDatabaseException {
        throw new RuntimeException("Not implemented yet.");
    }
}
