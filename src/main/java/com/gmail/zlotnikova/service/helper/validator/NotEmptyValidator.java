package com.gmail.zlotnikova.service.helper.validator;

public class NotEmptyValidator extends AbstractValidator {

    public NotEmptyValidator(Validator validator) {
        super(validator);
    }

    @Override
    public boolean validate(String parameter) {
        if (validateEmpty(parameter)) {
            return super.validate(parameter);
        } else {
            return false;
        }
    }

    private boolean validateEmpty(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }

}