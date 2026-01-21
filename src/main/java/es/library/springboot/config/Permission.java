package es.library.springboot.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission 
{
    // Author
    AUTHOR_READ("author:read"),
    AUTHOR_CREATE("author:create"),
    AUTHOR_UPDATE("author:update"),
    AUTHOR_DELETE("author:delete"),

    // Book
    BOOK_READ("book:read"),
    BOOK_CREATE("book:create"),
    BOOK_UPDATE("book:update"),
    BOOK_DELETE("book:delete"),

    // Category
    CATEGORY_READ("category:read"),
    CATEGORY_CREATE("category:create"),
    CATEGORY_UPDATE("category:update"),
    CATEGORY_DELETE("category:delete"),

    // Loan
    LOAN_READ_SELF("loan:read:self"),// user
    LOAN_READ_ANY_USER("loan:read:any:user"),// admin and employee
    LOAN_CREATE_SELF("loan:create:self"),// user
    LOAN_CREATE_ANY_USER("loan:create:any:user"),// admin and employee
    LOAN_RETURN_SELF("loan:return:self"),// user
    LOAN_RETURN_ANY_USER("loan:return:any:user"),// admin and employee
    LOAN_DELETE("loan:delete"), // admin

    // Rating
    AVG_RATING_READ("avg:rating:read"),// admin, employee, user
    RATING_READ_SELF("rating:read:self"),// user
    RATING_CREATE_SELF("rating:create:self"), // user
    RATING_UPDATE_SELF("rating:update:self"),// user
    RATING_DELETE("rating:delete"), // admin

    // User
    USER_READ_ANY("user:read:any"),// admin and employee
    USER_ROLE_ASSIGN_ANY("user:role:assign:any"), // admin
    USER_CREATE_LOW_LEVEL("user:create:low:level"), // employee and user
    USER_UPDATE_SELF("user:update:self"),// admin, employee, user
    USER_UPDATE_ANY("user:update:any"),// admin
    USER_DELETE_SELF("user:delete:self"),// user
    USER_DELETE_ANY("user:delete:any");// admin
	
	private final String permission;
}