package com.gmail.zlotnikova.service.helper.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternValidator extends AbstractValidator {

    private final Pattern pattern;

    public PatternValidator(Validator validator, String regexp) {
        super(validator);
        this.pattern = Pattern.compile(regexp);
    }

    @Override
    public boolean validate(String parameter) {
        if (validatePattern(parameter)) {
            return super.validate(parameter);
        } else {
            return false;
        }
    }

    private boolean validatePattern(String parameter) {
        Matcher matcher = pattern.matcher(parameter);
        return matcher.matches();
    }

}