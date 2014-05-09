package by.muna.moep.post.database.models;

import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.IPostTypeFull;
import by.muna.moep.post.database.PostDatabaseException;

import java.util.List;

public interface IDBPostType extends IPostTypeFull {
    IDBPostFormula getFormula() throws PostDatabaseException;
}
