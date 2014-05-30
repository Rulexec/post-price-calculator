package by.muna.moep.post.database;

import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.parameters.IPostParameterCase;
import by.muna.moep.post.data.parameters.IPostParameterData;
import by.muna.moep.post.data.parameters.PostParameterDataSimple;
import by.muna.moep.post.data.parameters.PostParameterDataSwitch;
import by.muna.moep.post.data.parameters.PostParameterType;
import by.muna.moep.post.database.models.IDBPostFormula;
import by.muna.moep.post.database.models.IDBPostType;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostJSONDatabase implements IPostDatabase {
    private Map<Integer, IDBPostType> typeById = new HashMap<>();
    private List<IDBPostType> allTypes;
    private Map<String, IDBPostFormula> formulaBySlug = new HashMap<>();

    @SuppressWarnings("unchecked")
    public PostJSONDatabase(String json) throws JSONParsingException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> data;

        try {
            data = mapper.readValue(json, Map.class);
        } catch (IOException ex) {
            throw new JSONParsingException(ex);
        }

        Map<String, Map<String, Object>> typesJson = (Map<String, Map<String, Object>>) data.get("types");
        Map<String, String> formulasJson = (Map<String, String>) data.get("formulas");

        for (Map.Entry<String, String> entry : formulasJson.entrySet()) {
            final String formulaCode = entry.getValue();

            this.formulaBySlug.put(entry.getKey(), () -> formulaCode);
        }

        this.allTypes = new ArrayList<>(typesJson.size());

        for (Map.Entry<String, Map<String, Object>> entry : typesJson.entrySet()) {
            Map<String, Object> typeData = entry.getValue();
            List<Map<String, Object>> parametersData =
                (List<Map<String, Object>>) typeData.get("parameters");

            final int id = Integer.parseUnsignedInt(entry.getKey());
            final String formulaName = (String) typeData.get("name");
            String formulaSlug = (String) typeData.get("formula");

            final List<IPostParameter> parameters = new ArrayList<>(parametersData.size());

            for (Map<String, Object> parameterJson : parametersData) {
                final String slug = (String) parameterJson.get("slug");
                final String name = (String) parameterJson.get("name");
                String typeString = (String) parameterJson.get("type");

                IPostParameterData parameterData;

                switch (typeString) {
                case "switch": {
                    List<Map<String, String>> casesJson =
                        (List<Map<String, String>>) parameterJson.get("cases");

                    List<IPostParameterCase> cases = new ArrayList<>(casesJson.size());

                    for (Map<String, String> caseJson : casesJson) {
                        final String caseSlug = caseJson.get("slug");
                        final String caseName = caseJson.get("name");

                        cases.add(new IPostParameterCase() {
                            @Override
                            public String getName() {
                                return caseName;
                            }

                            @Override
                            public String getSlug() {
                                return caseSlug;
                            }
                        });
                    }

                    parameterData = new PostParameterDataSwitch(() -> cases);
                } break;

                case "int": parameterData = new PostParameterDataSimple(PostParameterType.INT); break;
                case "uint": parameterData = new PostParameterDataSimple(PostParameterType.UINT); break;
                case "float": parameterData = new PostParameterDataSimple(PostParameterType.FLOAT); break;
                case "ufloat": parameterData = new PostParameterDataSimple(PostParameterType.UFLOAT); break;

                default: throw new JSONParsingException("Unknown parameter type: " + typeString);
                }

                final IPostParameterData parameterDataFinal = parameterData;

                parameters.add(new IPostParameter() {
                    @Override
                    public String getName() {
                        return name;
                    }

                    @Override
                    public String getSlug() {
                        return slug;
                    }

                    @Override
                    public IPostParameterData getData() {
                        return parameterDataFinal;
                    }
                });
            }

            final IDBPostFormula formula = this.formulaBySlug.get(formulaSlug);

            IDBPostType type = new IDBPostType() {
                @Override
                public IDBPostFormula getFormula() throws PostDatabaseException {
                    return formula;
                }

                @Override
                public List<IPostParameter> getParameters() throws PostDatabaseException {
                    return parameters;
                }

                @Override
                public int getId() {
                    return id;
                }

                @Override
                public String getName() {
                    return formulaName;
                }
            };

            this.typeById.put(id, type);
            this.allTypes.add(type);
        }
    }

    @Override
    public List<IDBPostType> getAllTypes() throws PostDatabaseException {
        return this.allTypes;
    }

    @Override
    public IDBPostType getPostType(int id, boolean deep) throws PostDatabaseException {
        return this.typeById.get(id);
    }

    @Override
    public IDBPostFormula getFormula(String slug) throws PostDatabaseException {
        return this.formulaBySlug.get(slug);
    }
}
