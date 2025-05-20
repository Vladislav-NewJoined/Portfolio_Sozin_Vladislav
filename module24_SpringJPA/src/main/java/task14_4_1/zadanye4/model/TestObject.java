package task14_4_1.zadanye4.model;

public class TestObject {
    private Long id;
    private String name;
    private String nameInUpperCase;
    private String nameInLowerCase;

    // геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Метод для преобразования значения поля name в UpperCase
    public void convertNameToUpperCase() {
        this.name = this.name.toUpperCase();
    }

    // Метод для преобразования значения поля name в LowerCase
    public void convertNameToLowerCase() {
        this.name = this.name.toLowerCase();
    }

    // Метод для возвращения значения поля name
    public String getOriginalName() {
        return this.name;
    }

    public void setNameInUpperCase(String nameInUpperCase) {
        this.nameInUpperCase = nameInUpperCase;
    }

    public void setNameInLowerCase(String nameInLowerCase) {
        this.nameInLowerCase = nameInLowerCase;
    }
}
