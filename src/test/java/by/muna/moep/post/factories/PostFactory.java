package by.muna.moep.post.factories;

import by.muna.moep.post.IPost;
import by.muna.moep.post.Post;
import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.formula.parser.IFormulaParserFactory;

public class PostFactory {
    public static IPost createPost(IPostDatabase db, IFormulaParserFactory formulaParserFactory) {
        return new Post(db, formulaParserFactory);
    }
}
