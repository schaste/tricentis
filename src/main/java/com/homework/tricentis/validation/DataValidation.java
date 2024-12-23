package com.homework.tricentis.validation;

import java.util.regex.Pattern;

public class DataValidation {

    // Regular expression patterns to detect potential vulnerabilities
    private static final String SQL_INJECTION_REGEX = "(?i)(union|select|insert|drop|delete|update|--|;|\\bexec\\b|\\bcount\\b|\\bselect\\b|\\bdeclare\\b|\\bfrom\\b|\\bwhere\\b|\\b--\\b|\\bconcat\\b|\\bcreate\\b|\\balter\\b|\\bdrop\\b|\\bupdate\\b).*";
    private static final String XSS_REGEX = ".*(<.*?>).*";
    private static final String COMMAND_INJECTION_REGEX = ".*([&|;`$<>]).*";

    // Method to validate message text
    public static boolean isValidMessage(String message) {
        if (message == null || message.isEmpty()) {
            return false; // Reject null or empty messages
        }

        // Check for SQL Injection pattern
        if (Pattern.matches(SQL_INJECTION_REGEX, message)) {
            System.out.println("SQL Injection detected!");
            return false;
        }

        // Check for XSS (Cross-Site Scripting) pattern
        if (Pattern.matches(XSS_REGEX, message)) {
            System.out.println("XSS vulnerability detected!");
            return false;
        }

        // Check for Command Injection pattern
        if (Pattern.matches(COMMAND_INJECTION_REGEX, message)) {
            System.out.println("Command Injection detected!");
            return false;
        }

        // If no vulnerabilities found, return true (valid message)
        return true;
    }




}
