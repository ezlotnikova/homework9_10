package com.gmail.zlotnikova.service.helper.validator;

public class IntegerValidator extends AbstractValidator {

    public IntegerValidator(Validator validator) {
        super(validator);
    }

    @Override
    public boolean validate(String parameter) {
        if (validateInteger(parameter)) {
            return super.validate(parameter);
        } else {
            return false;
        }
    }

    private boolean validateInteger(String parameter) {
        boolean result = false;
        try {
            Integer.parseInt(parameter);
            result = true;
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

}