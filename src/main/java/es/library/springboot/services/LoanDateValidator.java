package es.library.springboot.services;

import java.time.LocalDate;

import es.library.springboot.exceptions.ValidateException;

public final class LoanDateValidator 
{
	private LoanDateValidator() {}

    public static void validate(LocalDate dto) 
    {
        if (DateUtils.isBeforeToday(dto)) {
            throw new ValidateException("Start date cannot be before today");
        }
    }
}
