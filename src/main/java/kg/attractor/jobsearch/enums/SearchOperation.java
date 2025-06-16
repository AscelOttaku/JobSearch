package kg.attractor.jobsearch.enums;

public enum SearchOperation {
    EQUALITY, CONTAINS, END_WITH, STARTS_WITH, GREATER_THAN, LESS_THAN;

    public static SearchOperation getSimpleOperation(char c) {
        return switch (c) {
            case ':' -> EQUALITY;
            case '>' -> GREATER_THAN;
            case '<' -> LESS_THAN;
            case '%' -> END_WITH;
            default -> STARTS_WITH;
        };
    }

    public static final String ZERO_OR_MORE_REGEX = "*";
}
