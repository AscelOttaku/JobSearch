package kg.attractor.jobsearch.model.enums;

public enum Category {
    TEST(null), TEST2(TEST);

    private final Category category;

    Category(Category category) {
        this.category = category;
    }
}
