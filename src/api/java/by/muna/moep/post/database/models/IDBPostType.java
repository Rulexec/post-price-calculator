package by.muna.moep.post.database.models;

import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.database.PostDatabaseException;

import java.util.List;

public interface IDBPostType extends IPostType {
    IDBPostFormula getFormula() throws PostDatabaseException;
    List<IPostParameter> getParameters() throws PostDatabaseException;
}
