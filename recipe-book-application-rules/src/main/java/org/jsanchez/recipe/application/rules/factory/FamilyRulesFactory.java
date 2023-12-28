package org.jsanchez.recipe.application.rules.factory;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jsanchez.recipe.application.rules.HusbandRule;
import org.jsanchez.recipe.application.rules.Rule;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.error.ApplicationException;
import org.jsanchez.recipe.application.error.types.ApplicationErrorType;
import org.jsanchez.recipe.application.rules.*;
import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;


/**
 * Family rules factory implements {@link RulesFactory}
 * This factory will return the instance of a {@link Rule} to run depending on the enum {@link FamilyMemberType} value provided<br>
 * <ul>
 *     <li>{@link HusbandRule}</li>
 *     <li>{@link GrandsonRule}</li>
 *     <li>{@link DaughterRule}</li>
 * </ul>
 * If no rule found returns {@link ApplicationException} with the proper {@link ApplicationErrorType}
 */
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRulesFactory implements RulesFactory<FamilyMemberType> {

    @Inject
    private Set<Rule> rules;

    @Override
    public Rule getRule(FamilyMemberType who) throws ApplicationException {
        Optional<Rule> optional = rules.stream().filter((ruleInstance ->
                ruleInstance.getFamilyTypeMember().equals(who)
        )).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ApplicationException(ApplicationErrorType.RULE_NOT_FOUND, "Rule for [" + who + "] not found.");
    }
}
