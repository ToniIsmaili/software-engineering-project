package com.seeu.common;

public class Responses {
    public static final String SAVE_SUCCESSFUL = "{\"message\": \"Save successful: Your changes have been saved successfully.\"}";
    public static final String DELETE_SUCCESSFUL = "{\"message\": \"Delete successful: The item has been deleted successfully.\"}";
    public static final String INVALID_ID = "{\"message\": \"Invalid ID: The provided ID is not valid. Please check it and try again.\"}";
    public static final String MISSING_ID = "{\"message\": \"Missing ID: An ID value is required and cannot be empty.\"}";
    public static final String STARTED_JOB = "{\"message\": \"Job started successfully: The job has been started and is now running.\"}";
    public static final String ENDED_JOB = "{\"message\": \"Job ended: The job has finished and is no longer running.\"}";
    public static final String INVALID_VALUE = "{\"message\": \"Invalid value: The provided value is not a valid option.\"}";
    public static final String UNAUTHORIZED = "{\"message\": \"Unauthorized.\"}";
    public static final String EMAIL_ALREADY_EXISTS = "{\"message\": \"Email already exists: An account with this email is already registered.\"}";
}
