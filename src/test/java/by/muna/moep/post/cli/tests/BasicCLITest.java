package by.muna.moep.post.cli.tests;

import by.muna.moep.post.IPost;
import by.muna.moep.post.PostException;
import by.muna.moep.post.cli.BadPostCLICommandException;
import by.muna.moep.post.cli.IPostCLI;
import by.muna.moep.post.cli.PostCLI;
import by.muna.moep.post.cli.tests.factories.PostCLIFactory;
import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.IPostTypeFull;
import by.muna.moep.post.data.parameters.IPostParameterCase;
import by.muna.moep.post.data.parameters.IPostParameterData;
import by.muna.moep.post.data.parameters.IPostParameterSwitch;
import by.muna.moep.post.data.parameters.PostParameterDataSimple;
import by.muna.moep.post.data.parameters.PostParameterDataSwitch;
import by.muna.moep.post.data.parameters.PostParameterType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BasicCLITest {
    @Test
    public void basicCLITest() throws BadPostCLICommandException {
        IPostCLI cli = PostCLIFactory.createPostCLI(this.post);

        String s = cli.init();

        Assert.assertEquals("Post Price Calculator 0.0.1\nВведите help для помощи", s);

        s = cli.command("help");

        Assert.assertEquals(
            "types -- выводит список типов почтовых отправлений\n" +
            "type <id> -- выводит параметры типа\n" +
            "calc <id> <paramName>=<paramValue> <...> -- считает стоимость исходя из введённых параметров",

            s
        );

        s = cli.command("types");

        Assert.assertEquals("7 -- Тестовое отправление", s);

        s = cli.command("type 7");

        Assert.assertEquals(
            "Тестовое отправление:\n" +
            "  int_number -- int -- Какое-нибудь целое число\n" +
            "  num -- float -- И ещё дробное\n" +
            "  s -- switch -- А тут у нас выбор отборного плана\n" +
            "    planA -- План A\n" +
            "    planB -- План B\n" +
            "    planC -- План C",

            s
        );

        s = cli.command("calc 7 int_number=42 num=3.1415 s='planB'");

        Assert.assertEquals("42000", s);
    }

    private IPost post = new IPost() {
        private IPostTypeFull type = new IPostTypeFull() {
            @Override
            public List<IPostParameter> getParameters() {
                class PostParameter implements IPostParameter {
                    private String name, slug;
                    private IPostParameterData data;

                    public PostParameter(String name, String slug, IPostParameterData data) {
                        this.name = name;
                        this.slug = slug;
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

                return Arrays.asList(
                    new PostParameter(
                        "Какое-нибудь целое число", "int_number",
                        new PostParameterDataSimple(PostParameterType.INT)),
                    new PostParameter(
                        "И ещё дробное", "num",
                        new PostParameterDataSimple(PostParameterType.FLOAT)),
                    new PostParameter(
                        "А тут у нас выбор отборного плана", "s",
                        new PostParameterDataSwitch(new IPostParameterSwitch() {
                            @Override
                            public List<IPostParameterCase> getCases() {
                                class PostParameterCase implements IPostParameterCase {
                                    private String name, slug;

                                    public PostParameterCase(String name, String slug) {
                                        this.name = name;
                                        this.slug = slug;
                                    }

                                    @Override
                                    public String getName() {
                                        return this.name;
                                    }

                                    @Override
                                    public String getSlug() {
                                        return this.slug;
                                    }
                                }

                                return Arrays.asList(
                                    new PostParameterCase("План A", "planA"),
                                    new PostParameterCase("План B", "planB"),
                                    new PostParameterCase("План C", "planC")
                                );
                            }
                        }))
                );
            }

            @Override
            public int getId() {
                return 7;
            }

            @Override
            public String getName() {
                return "Тестовое отправление";
            }
        };

        @Override
        public List<IPostType> getPostTypes() throws PostException {
            return Arrays.asList(this.type);
        }

        @Override
        public IPostTypeFull getPostType(int id) throws PostException {
            return id == 7 ? this.type : null;
        }

        @Override
        public long calculate(int postTypeId, Map<String, String> arguments) throws PostException {
            // int_number=42 num=3.1415 s='planB'

            Assert.assertEquals(7, postTypeId);

            Assert.assertEquals(3, arguments.size());

            String intNumber = arguments.get("int_number");
            String num = arguments.get("num");
            String s = arguments.get("s");

            Assert.assertEquals("42", intNumber);
            Assert.assertEquals("3.1415", num);
            Assert.assertEquals("'planB'", s);

            return 42000;
        }
    };
}
