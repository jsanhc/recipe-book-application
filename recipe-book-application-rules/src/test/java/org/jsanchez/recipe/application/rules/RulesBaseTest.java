package org.jsanchez.recipe.application.rules;

import org.jsanchez.recipe.application.rules.factory.FamilyRulesFactory;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashSet;
import java.util.Set;

/**
 * Class base for Rules unit tests
 */
public class RulesBaseTest {

    public static final String RECIPE_REF = "9f2df620-3879-4850-b186-1104f72477a9";
    public static Set<Rule> rules;

    public static FamilyRulesFactory familyRulesFactory;

    @BeforeAll
    public static void setup() {
        rules = new HashSet<>() {{
            add(new HusbandRule());
            add(new GrandsonRule());
            add(new DaughterRule());
        }};
        familyRulesFactory = new FamilyRulesFactory(rules);
    }

}
