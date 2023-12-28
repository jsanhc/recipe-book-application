package org.jsanchez.recipe.application.rules;

import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.model.InquiryRequest;
import org.jsanchez.recipe.application.model.InquiryResponse;
import org.jsanchez.recipe.application.model.Member;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.types.WorthType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jsanchez.recipe.application.test.support.RecipeTestDataFactory.aValidRecipe;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for <br>
 * <ul>
 *     <li>{@link HusbandRule}</li>
 *     <li>{@link DaughterRule}</li>
 *     <li>{@link GrandsonRule}</li>
 * </ul>
 */
public class FamilyRulesTest extends RulesBaseTest {

    private static Stream<Arguments> validArguments() {
        return Stream.of(
                arguments(
                        FamilyMemberType.HUSBAND
                ),
                arguments(
                        FamilyMemberType.GRANDSON
                ),
                arguments(
                        FamilyMemberType.DAUGHTER
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    public void shouldRunRule(FamilyMemberType type) throws ApplicationException {
        // Given
        InquiryRequest inquiryRequest = InquiryRequest.builder().reference(RECIPE_REF).who(type).build();
        Recipe recipe = aValidRecipe(RECIPE_REF);
        recipe.setMembers(
                Collections.singletonList(Member.builder().member(type).build())
        );
        // when
        InquiryResponse inquiryResponse = familyRulesFactory.getRule(type).run(inquiryRequest, recipe);
        assertThat(inquiryResponse.getReference()).isEqualTo(recipe.getReference());
    }

    private static Stream<Arguments> invalidArguments() {
        return Stream.of(
                arguments(
                        FamilyMemberType.HUSBAND,
                        FamilyMemberType.GRANDSON
                ),
                arguments(
                        FamilyMemberType.GRANDSON,
                        FamilyMemberType.HUSBAND
                ),
                arguments(
                        FamilyMemberType.DAUGHTER,
                        FamilyMemberType.HUSBAND
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidArguments")
    public void shouldReturnInvalidRule(FamilyMemberType type, FamilyMemberType wrongType) throws ApplicationException {
        // Given
        InquiryRequest inquiryRequest = InquiryRequest.builder().reference(RECIPE_REF).who(type).build();
        Recipe recipe = aValidRecipe(RECIPE_REF);
        recipe.setMembers(
                Collections.singletonList(Member.builder().member(wrongType).build())
        );
        // when
        InquiryResponse inquiryResponse = familyRulesFactory.getRule(type).run(inquiryRequest, recipe);
        assertThat(inquiryResponse.getReference()).isEqualTo(recipe.getReference());
        assertThat(inquiryResponse.getWorth()).isEqualTo(WorthType.INVALID);
    }


    /*
    arguments:
        member,
        prepare time,
        portions, e
        expected prepareTime,
        expected portions,
        expected worth
     */
    private static Stream<Arguments> prepareTimeArguments() {
        return Stream.of(
                // husband
                arguments(
                        FamilyMemberType.HUSBAND,
                        1.00,
                        1,
                        1.00,
                        null,
                        WorthType.YEAH
                ),
                arguments(
                        FamilyMemberType.HUSBAND,
                        3.00,
                        2,
                        null,
                        null,
                        WorthType.NAH
                ),
                arguments(
                        FamilyMemberType.HUSBAND,
                        2.00,
                        3,
                        2.17, // prepare time (h) + 0.17 (10min) rounded
                        null,
                        WorthType.MEH
                ),
                // GrandSon
                arguments(
                        FamilyMemberType.GRANDSON,
                        1.00,
                        1,
                        0.75, // prepare time (h) - 0,25 (15 min)
                        1,
                        WorthType.YEAH
                ),
                arguments(
                        FamilyMemberType.GRANDSON,
                        3.00,
                        2,
                        2.92, // prepare time (h) - 0,08 (5 min)
                        null,
                        WorthType.YEAH
                ),
                arguments(
                        FamilyMemberType.GRANDSON,
                        2.00,
                        3,
                        1.83, // prepare time (h) - 0,17 (10 min)
                        3,
                        WorthType.YEAH
                ),
                // Daughter
                arguments(
                        FamilyMemberType.DAUGHTER,
                        1.00,
                        1,
                        0.75, // prepare time (h) - 0,25 (15 min)
                        1,
                        WorthType.YEAH
                ),
                arguments(
                        FamilyMemberType.DAUGHTER,
                        3.00,
                        2,
                        3.00,
                        null,
                        WorthType.MEH
                ),
                arguments(
                        FamilyMemberType.DAUGHTER,
                        2.00,
                        3,
                        1.83, // prepare time (h) - 0,17 (10 min)
                        3,
                        WorthType.YEAH
                )
        );
    }

    @ParameterizedTest
    @MethodSource("prepareTimeArguments")
    public void shouldRunRuleAndResponseIsValid(
            FamilyMemberType type, Double prepareTime, Integer portions,
            Double expectedPrepareTime, Integer expectedPortions, WorthType expectedWorthType
    ) throws ApplicationException {
        // Given
        InquiryRequest inquiryRequest = InquiryRequest.builder().reference(RECIPE_REF).who(type).build();
        Recipe recipe = aValidRecipe(RECIPE_REF);
        recipe.setPrepareTime(prepareTime);
        recipe.setPortions(portions);
        recipe.setMembers(
                Collections.singletonList(Member.builder().member(type).build())
        );
        // when
        InquiryResponse inquiryResponse = familyRulesFactory.getRule(type).run(inquiryRequest, recipe);
        assertThat(inquiryResponse.getReference()).isEqualTo(recipe.getReference());
        assertThat(inquiryResponse.getPrepareTime()).isEqualTo(expectedPrepareTime);
        assertThat(inquiryResponse.getPortions()).isEqualTo(expectedPortions);
        assertThat(inquiryResponse.getWorth()).isEqualTo(expectedWorthType);
    }

}
