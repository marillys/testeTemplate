package steps;

import api.ApiHeaders;
import api.ApiRequest;
import com.github.javafaker.Faker;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.cucumber.messages.internal.com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.Assert;
import user.UsersLombok;
import utils.JsonUtils;
import utils.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GorestSteps extends ApiRequest {

    PropertiesUtils prop = new PropertiesUtils();
    JsonUtils jsonUtils = new JsonUtils();
    ApiHeaders apiHeaders = new ApiHeaders();
    UsersLombok user;
    Faker faker = new Faker();
    String userId;

    Map<String, String> jsonValues = new HashMap<>();

    @Dado("que possuo gorest token valido")
    public void que_possuo_gorest_token_valido() {
        token = prop.getProp("token_gorest");
    }

    @Quando("envio um request de cadastro de usuário com dados validos")
    public void envio_um_request_de_cadastro_de_usuário_com_dados_validos() {
        super.uri = prop.getProp("url.gorest");
        super.headers = apiHeaders.gorestHeaders(token);
        /*super.body = jsonUtils.parseJSONFile("create_user");
        super.body.put("email",faker.internet().emailAddress());*/
        //super.body = jsonUtils.setJsonValueByKey(jsonUtils.parseJSONFile("create_user"),setJsonValues());

        /*super.body = new Users(faker.name().fullName(), "Female",faker.internet().emailAddress(),
                "Inactive").getJson();*/

        /*UsersGetterSetter usersGetterSetter = new UsersGetterSetter();

        usersGetterSetter.setEmail(faker.internet().emailAddress());
        usersGetterSetter.setName(faker.name().fullName());
        usersGetterSetter.setGender("Female");
        usersGetterSetter.setStatus("Inactive");

        super.body = usersGetterSetter.getJson();*/

        /*Lombok
        super.body= new JSONObject(new Gson().toJson(
                UsersLombok.builder()
                        .email(faker.internet().emailAddress())
                        .name(faker.name().fullName())
                        .gender("Female")
                        .status("Inactive")
                        .build()));*/

        user = UsersLombok.builder()
                .email(faker.internet().emailAddress())
                .name(faker.name().fullName())
                .gender("female")
                .status("inactive")
                .build();

        /*Criar um xml
        String xml = new UsersRecord(faker.internet().emailAddress(),faker.name().fullName(),
                "Female","Inactive").getXml();*/

        /*super.body = new UsersRecord(faker.internet().emailAddress(),faker.name().fullName(),
                "Female","Inactive").getJson();*/

        super.body = new JSONObject((new Gson().toJson(user)));

        super.POST();
    }

    /*private Map<String, String> setJsonValues() {
        jsonValues.put("email",faker.internet().emailAddress());
        jsonValues.put("name",faker.name().fullName());
        jsonValues.put("gender","Female");
        jsonValues.put("status","Inactive");
        return  jsonValues;
    }*/

    @Entao("o usuario deve ser criado corretamente")
    public void o_usuario_deve_ser_criado_corretamente() {
        UsersLombok userRetorno = response.jsonPath().getObject("data", UsersLombok.class);

        assertEquals("Falha na comparação ", user, userRetorno);

        /*assertEquals(body.getString("email"),response.jsonPath().getString("data.email"));
        assertEquals(body.getString("name"),response.jsonPath().getString("data.name"));*/

        assertEquals(user, userRetorno);
    }

    @Entao("o status code do request deve ser {int}")
    public void o_status_code_do_request_deve_ser(int statusEsperado) {
        Assert.assertEquals(statusEsperado, response.statusCode());
    }

    @E("existe um usuario cadastrado na api")
    public void existeUmUsuarioCadastradoNaApi() {
        //userId ="30562";
        envio_um_request_de_cadastro_de_usuário_com_dados_validos();
    }

    @Quando("buscar esse usuario")
    public void buscarEsseUsuario() {
        super.uri = prop.getProp("url.gorest") + "/" + response.jsonPath().getJsonObject("data.id");
        super.headers = apiHeaders.gorestHeaders(token);
        super.body = new JSONObject();

        super.GET();
    }

    @Entao("os dados dos usuarios devem ser retornados")
    public void osDadosDosUsuariosDevemSerRetornados() {
        System.out.println(response.asString());
    }

    @Quando("altero os dados do usuario")
    public void alteroOsDadosDoUsuario() {
        super.uri = prop.getProp("url.gorest") + "/" + response.jsonPath().getJsonObject("data.id");
        super.headers = apiHeaders.gorestHeaders(token);

        super.body.put("status", "active");
        user.setStatus("active");
        super.PUT();
    }

    @Entao("o usuario deve ser alterado com sucesso")
    public void oUsuarioDeveSerAlteradoComSucesso() {
        assertEquals("Erro na comparação do objeto", user, response.jsonPath().getObject("data", UsersLombok.class));
    }

    @Quando("altero um ou mais dados do usuario")
    public void alteroUmOuMaisDadosDoUsuario() {
        super.uri = prop.getProp("url.gorest") + "/" + response.jsonPath().getJsonObject("data.id");
        super.headers = apiHeaders.gorestHeaders(token);

        user.setGender("male");
        super.body = new JSONObject("{\"gender\":\"male\"}");
        super.PATCH();
    }

    @Quando("deleto esse usuario")
    public void deletoEsseUsuario() {
        super.uri = prop.getProp("url.gorest") + "/" + response.jsonPath().getJsonObject("data.id");
        super.headers = apiHeaders.gorestHeaders(token);
        super.body = new JSONObject();

        super.DELETE();
    }

    @Entao("o usuario e deletado corretamente")
    public void oUsuarioEDeletadoCorretamente() {
        assertEquals("", response.asString());
    }
}
