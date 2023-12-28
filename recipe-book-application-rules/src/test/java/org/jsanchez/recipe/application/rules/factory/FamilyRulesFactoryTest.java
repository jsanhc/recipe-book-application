package org.jsanchez.recipe.application.rules.factory;

import org.jsanchez.recipe.application.rules.*;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Unit test for {@link FamilyRulesFactory}
 */
public class FamilyRulesFactoryTest extends RulesBaseTest {

    private static Stream<Arguments> validArguments() {
        return Stream.of(
                arguments(
                        FamilyMemberType.HUSBAND,
                        HusbandRule.class
                ),
                arguments(
                        FamilyMemberType.GRANDSON,
                        GrandsonRule.class
                ),
                arguments(
                        FamilyMemberType.DAUGHTER,
                        DaughterRule.class
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validArguments")
    public void shouldGetHusbandRuleInstance(FamilyMemberType type, Class expectedInstance) throws ApplicationException {
        // when
        Rule rule = familyRulesFactory.getRule(type);
        // then
        assertThat(rule).isInstanceOf(expectedInstance);
    }

    @Test
    public void shouldThrowsRuleNotFoundException() {
        // when
        ApplicationException exception = catchThrowableOfType(() ->
                        familyRulesFactory.getRule(FamilyMemberType.ALL)
                , ApplicationException.class
        );
        // then
        assertThat(exception.getErrorCodeType()).isEqualTo(ApplicationErrorType.RULE_NOT_FOUND);
        assertThat(exception.getMessage()).isEqualTo("Rule for [" + FamilyMemberType.ALL + "] not found.");
    }
}
