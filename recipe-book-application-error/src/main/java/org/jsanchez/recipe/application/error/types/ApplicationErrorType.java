package org.jsanchez.recipe.application.error.types;

import lombok.Getter;

/**
 * Application error type for a set of application error values
 */
@Getter
public enum ApplicationErrorType {
    // server
    INTERNAL_SERVER_ERROR("BOOK.APP::Internal.Server.Error"),
    // REST
    NOT_FOUND("BOOK.APP::REST.Not.Found"),

    NOT_IMPLEMENTED("BOOK.APP::REST.Endpoint.Not.Implemented.Yet"),
    QUERY_PARAMETERS_NOT_SUPPORTED("BOOK.APP::REST.query.params.not.supported"),
    EMPTY_BODY_NOT_SUPPORTED("BOOK.APP::REST.Empty.Body.Not.Supported"),
    // DB
    RECIPE_NOT_FOUND("BOOK.APP::DB.Recipe.Not.Found"),
    INGREDIENT_NOT_FOUND("BOOK.APP::DB.Ingredient.Not.Found"),

    DATABASE_OPERATION_ERROR("BOOK.APP::DB.Operation.Failed"),
    DATABASE_DUPLICATE_ENTRY_ERROR("BOOK.APP::DB.Duplicate.Entry.Constraint.Violation"),
    DATABASE_OPT_DELETE_ERROR("BOOK.APP::DB.Delete.Operation.Failed"),
    DATABASE_OPT_INSERT_ERROR("BOOK.APP::DB.Insert.Operation.Failed"),

    // Rules
    RULE_NOT_FOUND("BOOk.APP::RULE.Not.Found");
    private String code;

    ApplicationErrorType(String code) {
        this.code = code;
    }


    public static ApplicationErrorType fromCode(String code) {
        for (ApplicationErrorType type : ApplicationErrorType.values()) {
            if (java.lang.String.valueOf(type.code).equals(code)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.getCode();
    }
}
