package org.jsanchez.recipe.application.controller;

import com.google.gson.Gson;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.Method;
import org.jsanchez.recipe.application.configuration.ApplicationProperties;
import org.jsanchez.recipe.application.configuration.guice.ApplicationGuiceModule;
import org.jsanchez.recipe.application.http.ApiTestUtils;
import org.jsanchez.recipe.application.model.InquiryRequest;
import org.jsanchez.recipe.application.model.InquiryResponse;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.types.WorthType;
import org.jsanchez.recipe.application.web.api.ApplicationApi;
import org.jsanchez.recipe.application.web.api.discover.RestOperation;
import org.jsanchez.recipe.application.web.controller.RulesApiController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.inject.Guice.createInjector;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.KEY_FILTER_CONTROLLER;
import static org.jsanchez.recipe.application.configuration.ApplicationConstants.KEY_RULES_CONTROLLER;
import static org.jsanchez.recipe.application.web.controller.RulesApiController.ROOT_CONTEXT_PATH;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for {@link RulesApiController}
 */
public class RulesApiControllerTest {

    public final String SERVER_PORT = "4567";
    private final String SERVER_HOST = "http://localhost:" + SERVER_PORT;

    private static ApplicationApi rulesController;
    private static Gson gson;

    @BeforeAll
    public static void setup() {
        ApplicationProperties applicationProperties = new ApplicationProperties();
        applicationProperties.loadProperties();
        Injector injector = createInjector(new ApplicationGuiceModule(applicationProperties));
        gson = injector.getInstance(Gson.class);
        Map<String, ApplicationApi> mapControllers = injector.getInstance(
                Key.get(new TypeLiteral<Map<String, ApplicationApi>>() {
                })
        );
        rulesController = mapControllers.get(KEY_RULES_CONTROLLER);
        assertDoesNotThrow(() -> rulesController.initialize());
        assertDoesNotThrow(() -> mapControllers.get(KEY_FILTER_CONTROLLER).initialize());
        spark.Spark.awaitInitialization();
    }

    @AfterAll
    public static void tearDown() {
        spark.Spark.stop();
    }

    @Test
    void shouldHaveOperations() {
        List<RestOperation> operations = ((RulesApiController) rulesController).getOperations();
        assertThat(operations).isNotEmpty();
        assertThat(operations).hasSize(1);
    }

    private static Stream<Arguments> validArguments() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH,
                        Method.POST.name()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    void shouldValidateOperationsController(String contextPath, String method) {
        List<RestOperation> operations = ((RulesApiController) rulesController).getOperations();
        assertThat(operations).isNotEmpty();
        assertThat(operations).filteredOn(
                operation -> operation.getPath().equals(contextPath) && operation.getOperation().equals(method)
        );
    }

    private static Stream<Arguments> validRuleArguments() {
        // Given
        return Stream.of(
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Husband-12345A").who(FamilyMemberType.HUSBAND).build()
                        ),
                        //{"worth":"yeah","prepareTime":1.1,"reference":"Husband-12345A"}
                        InquiryResponse.builder().worth(WorthType.YEAH).prepareTime(1.1).reference("Husband-12345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Husband-22345A").who(FamilyMemberType.HUSBAND).build()
                        ),
                        //{"worth":"meh","prepareTime":2.17,"reference":"Husband-22345A"}
                        InquiryResponse.builder().worth(WorthType.MEH).prepareTime(2.17).reference("Husband-22345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Husband-32345A").who(FamilyMemberType.HUSBAND).build()
                        ),
                        //{"worth":"nah","reference":"Husband-32345A"}
                        InquiryResponse.builder().worth(WorthType.NAH).reference("Husband-32345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Grandson-12345A").who(FamilyMemberType.GRANDSON).build()
                        ),
                        //{"worth":"yeah","portions":20,"prepareTime":0.95,"reference":"Grandson-12345A"}
                        InquiryResponse.builder().worth(WorthType.YEAH).portions(20).prepareTime(0.95).reference("Grandson-12345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Grandson-22345A").who(FamilyMemberType.GRANDSON).build()
                        ),
                        //{"worth":"yeah","portions":4,"prepareTime":1.83,"reference":"Grandson-22345A"}
                        InquiryResponse.builder().worth(WorthType.YEAH).portions(4).prepareTime(1.83).reference("Grandson-22345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Grandson-32345A").who(FamilyMemberType.GRANDSON).build()
                        ),
                        //{"worth":"yeah","prepareTime":2.92,"reference":"Grandson-32345A"}
                        InquiryResponse.builder().worth(WorthType.YEAH).prepareTime(2.92).reference("Grandson-32345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Daughter-12345A").who(FamilyMemberType.DAUGHTER).build()
                        ),
                        //{"worth":"yeah","portions":30,"prepareTime":1.05,"reference":"Daughter-12345A"}
                        InquiryResponse.builder().worth(WorthType.YEAH).portions(30).prepareTime(1.05).reference("Daughter-12345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Daughter-22345A").who(FamilyMemberType.DAUGHTER).build()
                        ),
                        // {"worth":"yeah","portions":6,"prepareTime":1.83,"reference":"Daughter-22345A"}
                        InquiryResponse.builder().worth(WorthType.YEAH).portions(6).prepareTime(1.83).reference("Daughter-22345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("Daughter-32345A").who(FamilyMemberType.DAUGHTER).build()
                        ),
                        //{"worth":"meh","prepareTime":3.0,"reference":"Daughter-32345A"}
                        InquiryResponse.builder().worth(WorthType.MEH).prepareTime(3.0).reference("Daughter-32345A").build()
                ),
                arguments(
                        ROOT_CONTEXT_PATH,
                        gson.toJson(
                                InquiryRequest.builder().reference("0000000099").who(FamilyMemberType.DAUGHTER).build()
                        ),
                        //{"worth":"INVALID","reference":"0000000099"}
                        InquiryResponse.builder().worth(WorthType.INVALID).reference("0000000099").build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validRuleArguments")
    void shouldRequestEndpoints(String contextPath, String requestBody, InquiryResponse responseExpected) {
        ApiTestUtils apiTestUtils = ApiTestUtils.builder()
                .method(Method.POST.name())
                .host(SERVER_HOST)
                .path(contextPath)
                .requestBody(requestBody)
                .responseType(InquiryResponse.class).build();
        // when
        ApiTestUtils.TestResponse<InquiryResponse> res = apiTestUtils.request();
        assertThat(res.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(res.getBody().getWorth()).isEqualTo(responseExpected.getWorth());
        assertThat(res.getBody().getReference()).isEqualTo(responseExpected.getReference());
        assertThat(res.getBody().getPrepareTime()).isEqualTo(responseExpected.getPrepareTime());
    }
}
