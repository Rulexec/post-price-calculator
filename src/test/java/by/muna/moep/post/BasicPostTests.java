package by.muna.moep.post;

import by.muna.moep.post.cli.tests.BasicCLITest;
import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.parameters.IPostParameterCase;
import by.muna.moep.post.data.parameters.IPostParameterData;
import by.muna.moep.post.data.parameters.IPostParameterSwitch;
import by.muna.moep.post.data.parameters.PostParameterDataSimple;
import by.muna.moep.post.data.parameters.PostParameterDataSwitch;
import by.muna.moep.post.data.parameters.PostParameterType;
import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.database.PostDatabaseException;
import by.muna.moep.post.database.models.IDBPostFormula;
import by.muna.moep.post.database.models.IDBPostType;
import by.muna.moep.post.factories.PostFactory;
import by.muna.moep.post.formula.tests.factories.FormulaParserFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicPostTests {
    @Test
    public void basicTest() throws Exception {
        IPost post = PostFactory.createPost(BasicPostTests.DB, FormulaParserFactory.createFormulaParser());

        Assert.assertEquals(0, post.getPostTypes().size());

        this.assertType(post.getPostTypes().get(0));
        this.assertType(post.getPostType(1));

        Map<String, String> parametersAdd = new HashMap<>();
        parametersAdd.put("type", "'add'");
        parametersAdd.put("a", "2");
        parametersAdd.put("b", "3");

        Map<String, String> parametersMult = new HashMap<>();
        parametersAdd.put("type", "'mult'");
        parametersAdd.put("a", "2");
        parametersAdd.put("b", "3");

        Assert.assertEquals(5, post.calculate(1, parametersAdd));
        Assert.assertEquals(6, post.calculate(1, parametersMult));
    }

    private boolean assertType(IPostType type) {
        return false;
    }

    public static final IPostDatabase DB = new IPostDatabase() {
        private IDBPostType type = new IDBPostType() {
            @Override
            public IDBPostFormula getFormula() throws PostDatabaseException {
                return BasicPostTests.DB.getFormula("plusOrMultiply");
            }

            @Override
            public List<IPostParameter> getParameters() throws PostDatabaseException {
                class PostParameter implements IPostParameter {
                    private String name, slug;
                    private IPostParameterData data;

                    public PostParameter(String slug, String name, IPostParameterData data) {
                        this.slug = slug;
                        this.name = name;
                        this.data = data;
                    }

                    @Override
                    public String getName() {
                        return this.name;
                    }

                    @Override
                    public String getSlug() {
                        return this.slug;
                    }

                    @Override
                    public IPostParameterData getData() {
                        return this.data;
                    }
                }

                class PostParameterCase implements IPostParameterCase {
                    private String slug, name;

                    public PostParameterCase(String slug, String name) {
                        this.slug = slug;
                        this.name = name;
                    }

                    @Override
                    public String getSlug() {
                        return this.slug;
                    }

                    @Override
                    public String getName() {
                        return this.name;
                    }
                }

                return Arrays.asList(
                    new PostParameter("a", "Первый параметр",
                        new PostParameterDataSimple(PostParameterType.INT)),
                    new PostParameter("b", "Второй параметр",
                        new PostParameterDataSimple(PostParameterType.INT)),
                    new PostParameter("type", "Операция", new PostParameterDataSwitch(
                        () -> Arrays.asList(
                            new PostParameterCase("add", "Сложение"),
                            new PostParameterCase("mult", "Умножение")
                        )
                    ))
                );
            }

            @Override
            public int getId() {
                return 1;
            }

            @Override
            public String getName() {
                return "Сложение или умножение";
            }
        };

        @Override
        public List<IDBPostType> getAllTypes() throws PostDatabaseException {
            return Arrays.asList(this.type);
        }

        @Override
        public IDBPostType getPostType(int id, boolean deep) throws PostDatabaseException {
            if (id == 1) return this.type;
            else return null;
        }

        @Override
        public IDBPostFormula getFormula(String slug) throws PostDatabaseException {
            switch (slug) {
            case "plusOrMultiply": return () -> "switch $type: case 'add': $a + $b case 'mult': $a * $b";
            default: return null;
            }
        }
    };
}
