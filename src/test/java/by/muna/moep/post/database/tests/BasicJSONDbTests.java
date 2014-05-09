package by.muna.moep.post.database.tests;

import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.parameters.IPostParameterCase;
import by.muna.moep.post.data.parameters.IPostParameterData;
import by.muna.moep.post.data.parameters.IPostParameterSwitch;
import by.muna.moep.post.data.parameters.PostParameterDataSwitch;
import by.muna.moep.post.data.parameters.PostParameterType;
import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.database.models.IDBPostFormula;
import by.muna.moep.post.database.models.IDBPostType;
import by.muna.moep.post.database.tests.factories.PostJSONDatabaseFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasicJSONDbTests {
    @Test
    public void basicTest() throws Exception {
        String json = "{" +
            "  \"types\": {" +
            "    \"1\": {" +
            "      \"name\": \"Тестовый тип\"," +
            "      \"parameters\": [" +
            "        {\"slug\": \"arg1\", \"name\": \"Первый параметр\", \"type\": \"uint\"}," +
            "        {\"slug\": \"arg2\", \"name\": \"Второй параметр\", \"type\": \"float\"}" +
            "      ]," +
            "      \"formula\": \"example\"" +
            "    }," +
            "    \"2\": {" +
            "      \"name\": \"Второй тип\"," +
            "      \"parameters\": [" +
            "        {\"slug\": \"some\", \"name\": \"Какой-то параметр\", \"type\": \"int\"}," +
            "        {\"slug\": \"sw\", \"name\": \"Лицо\", \"type\": \"switch\", \"cases\": [" +
            "          {\"slug\": \"natural\", \"name\": \"Физическое лицо\"}," +
            "          {\"slug\": \"legal\", \"name\": \"Юридическое лицо\"}" +
            "        ]}" +
            "      ]," +
            "      \"formula\": \"other_formula\"" +
            "    }" +
            "  }," +
            "  \"formulas\": {" +
            "    \"example\": \"(2 + 3 * $arg1) * $arg2\"," +
            "    \"other_formula\": \"$some * 0 + 42\"" +
            "  }" +
            "}";

        IPostDatabase db = PostJSONDatabaseFactory.createJsonDatabase(json);

        IDBPostFormula exampleFormula = db.getFormula("example");
        IDBPostFormula otherFormula = db.getFormula("other_formula");

        Assert.assertEquals("(2 + 3 * $arg1) * $arg2", exampleFormula.getFormulaCode());
        Assert.assertEquals("$some * 0 + 42", otherFormula.getFormulaCode());

        List<IDBPostType> allTypes = db.getAllTypes();

        Assert.assertEquals(2, allTypes.size());

        Set<Integer> expectedTypes = new HashSet<>();
        expectedTypes.add(1);
        expectedTypes.add(2);

        class NameAndType {
            public String name;
            public PostParameterType type;

            public NameAndType(String name, PostParameterType type) {
                this.name = name;
                this.type = type;
            }
        }

        for (IDBPostType type : allTypes) {
            switch (type.getId()) {
            case 1: {
                Assert.assertEquals("Тестовый тип", type.getName());
                Assert.assertEquals(exampleFormula.getFormulaCode(), type.getFormula().getFormulaCode());

                Map<String, NameAndType> expected = new HashMap<>();
                expected.put("arg1", new NameAndType("Первый параметр", PostParameterType.UINT));
                expected.put("arg2", new NameAndType("Второй параметр", PostParameterType.FLOAT));

                for (IPostParameter parameter : type.getParameters()) {
                    NameAndType nameAndType = expected.remove(parameter.getSlug());

                    Assert.assertEquals(nameAndType.name, parameter.getSlug());
                    Assert.assertEquals(nameAndType.type, parameter.getData().getParameterType());
                }

                Assert.assertEquals(0, expected.size());
            } break;
            case 2: {
                Assert.assertEquals("Второй тип", type.getName());
                Assert.assertEquals(otherFormula.getFormulaCode(), type.getFormula().getFormulaCode());

                Map<String, NameAndType> expected = new HashMap<>();
                expected.put("some", new NameAndType("Какой-то параметр", PostParameterType.INT));
                boolean swChecked = false;

                for (IPostParameter parameter : type.getParameters()) {
                    switch (parameter.getSlug()) {
                    case "some": {
                        NameAndType nameAndType = expected.remove(parameter.getSlug());

                        Assert.assertEquals(nameAndType.name, parameter.getSlug());
                        Assert.assertEquals(nameAndType.type, parameter.getData().getParameterType());
                    } break;
                    case "sw": {
                        Assert.assertEquals("Лицо", parameter.getName());
                        PostParameterDataSwitch parameterData =
                            (PostParameterDataSwitch) parameter.getData();
                        Assert.assertEquals(PostParameterType.SWITCH, parameterData.getParameterType());

                        Set<String> casesExpected = new HashSet<>();
                        casesExpected.add("natural");
                        casesExpected.add("legal");

                        for (IPostParameterCase parameterCase : parameterData.getSwitch().getCases()) {
                            if (!casesExpected.remove(parameterCase.getSlug())) Assert.fail();

                            switch (parameterCase.getSlug()) {
                            case "natural": {
                                Assert.assertEquals("Физическое лицо", parameterCase.getName());
                            } break;
                            case "legal": {
                                Assert.assertEquals("Юридическое лицо", parameterCase.getName());
                            } break;
                            default: Assert.fail();
                            }
                        }

                        if (swChecked) Assert.fail();
                        swChecked = true;
                    } break;
                    default: Assert.fail();
                    }
                }

                Assert.assertEquals(0, expected.size());
            } break;
            default: Assert.fail("Unknown type id: " + type.getId());
            }

            if (!expectedTypes.remove(type.getId())) Assert.fail("Already viewed: " + type.getId());
        }

        Assert.assertEquals(0, expectedTypes.size());
    }
}
