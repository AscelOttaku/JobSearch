package kg.attractor.jobsearch.model.enums;

public enum Category {
    TEST(null), TEST2(TEST);

    private final Category underCategory;

    Category(Category category) {
        underCategory = category;
    }
}
