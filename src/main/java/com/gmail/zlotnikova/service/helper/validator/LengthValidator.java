package com.gmail.zlotnikova.service.helper.validator;

public class LengthValidator extends AbstractValidator {

    private final int length;

    public LengthValidator(Validator validator, int length) {
        super(validator);
        this.length = length;
    }

    @Override
    public boolean validate(String parameter) {
        if (validateLength(parameter)) {
            return super.validate(parameter);
        } else {
            return false;
        }
    }

    private boolean validateLength(String parameter) {
        return parameter.length() <= length;
    }

}