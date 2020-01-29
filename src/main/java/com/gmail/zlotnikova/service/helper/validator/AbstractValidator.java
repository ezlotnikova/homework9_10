package com.gmail.zlotnikova.service.helper.validator;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractValidator implements Validator {

    protected static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final Validator validator;

    protected AbstractValidator(Validator validator) {this.validator = validator;}

    @Override
    public boolean validate(String parameter) {
        return validator.validate(parameter);
    }

}