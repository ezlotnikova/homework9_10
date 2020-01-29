package com.gmail.zlotnikova.service.helper.validator;

public class MayBeEmptyValidator extends AbstractValidator {

    public MayBeEmptyValidator(Validator validator) {
        super(validator);
    }

    @Override
    public boolean validate(String parameter) {
        if (validateMayBeEmpty(parameter)) {
            return true;
        } else {
            return super.validate(parameter);
        }
    }

    private boolean validateMayBeEmpty(String parameter) {
        return parameter == null || parameter.isEmpty();
    }

}