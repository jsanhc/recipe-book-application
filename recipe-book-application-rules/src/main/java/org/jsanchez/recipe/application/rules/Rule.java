package org.jsanchez.recipe.application.rules;

import org.jsanchez.recipe.application.model.InquiryResponse;
import org.jsanchez.recipe.application.types.FamilyMemberType;

public interface Rule<T, R> {

    Double DEFAULT_OPERATOR_VALUE = 2.0;

    InquiryResponse run(T inquiry, R entity);

    InquiryResponse buildResponse(R entity);

    FamilyMemberType getFamilyTypeMember();
}
