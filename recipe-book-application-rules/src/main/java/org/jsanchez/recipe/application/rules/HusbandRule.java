package org.jsanchez.recipe.application.rules;

import lombok.extern.slf4j.Slf4j;
import org.jsanchez.recipe.application.model.InquiryRequest;
import org.jsanchez.recipe.application.model.InquiryResponse;
import org.jsanchez.recipe.application.model.Recipe;
import org.jsanchez.recipe.application.rules.util.PrepareTimeUtil;
import org.jsanchez.recipe.application.types.FamilyMemberType;
import org.jsanchez.recipe.application.types.WorthType;

import java.util.Objects;

/**
 * Rules for 'HUSBAND'
 */
@Slf4j
public class HusbandRule extends PrepareTimeUtil implements Rule<InquiryRequest, Recipe> {

    @Override
    public InquiryResponse run(InquiryRequest inquiry, Recipe recipe) {
        if (Objects.nonNull(recipe) && Objects.nonNull(recipe.getMembers())) {
            if (recipe.getMembers().stream().anyMatch((member) -> member.getMember().equals(inquiry.getWho()))) {
                return buildResponse(recipe);
            }
        }
        return InquiryResponse.builder()
                .reference(inquiry.getReference())
                .worth(WorthType.INVALID)
                .build();
    }

    @Override
    public InquiryResponse buildResponse(Recipe recipe) {
        InquiryResponse inquiryResponse = InquiryResponse.builder()
                .reference(recipe.getReference())
                .prepareTime(recipe.getPrepareTime())
                .build();
        int comparatorResult = recipe.getPrepareTime().compareTo(DEFAULT_OPERATOR_VALUE);
        if (comparatorResult < 0) {
            inquiryResponse.setWorth(WorthType.YEAH);
        } else if (comparatorResult > 0) {
            inquiryResponse.setWorth(WorthType.NAH);
            inquiryResponse.setPrepareTime(null);
        } else {
            inquiryResponse.setWorth(WorthType.MEH);
            inquiryResponse.setPrepareTime(
                    addition(recipe.getPrepareTime(), minutesToHours(10.00))
            );
        }
        return inquiryResponse;
    }

    @Override
    public FamilyMemberType getFamilyTypeMember() {
        return FamilyMemberType.HUSBAND;
    }
}
