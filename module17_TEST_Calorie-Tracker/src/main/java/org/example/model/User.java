package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate; // Импортируем LocalDate


@Data
@Entity
@Table(name = "ПОЛЬЗОВАТЕЛИ") // Имя таблицы на кириллице
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID") // Имя столбца на английском (H2 не поддерживает кириллицу для ID)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    @Column(name = "Имя") // Имя столбца на кириллице
    private String name;

    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не может быть пустым")
    @Column(name = "Email") // Имя столбца на кириллице
    private String email;

    @NotNull(message = "Цель не может быть пустой")
    @Enumerated(EnumType.STRING)
    @Column(name = "Цель") // Имя столбца на кириллице
    private Goal goal;

    @Min(value = 1, message = "Возраст должен быть больше 0")
    @Max(value = 120, message = "Возраст должен быть меньше 120")
    @Column(name = "Возраст") // Имя столбца на кириллице
    private int age = 30; // Пример возраста

    @Min(value = 1, message = "Рост должен быть больше 0")
    @Column(name = "Рост (см)") // Имя столбца на кириллице
    private double height;

    @Min(value = 1, message = "Вес должен быть больше 0")
    @Column(name = "Вес (кг)") // Имя столбца на кириллице
    private double weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "Уровень активности") // Имя столбца на кириллице
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING) // Используем EnumType.STRING
    @Column(name = "Пол") // Имя столбца на кириллице
    private Gender gender;

    @Column(name = "Калории на порцию")
    private Double caloriesPerPortion = 165.0; // Пример калорий

    @Column(name = "Углеводы")
    private Double carbohydrates = 0.0; // Пример углеводов

    @Column(name = "Жиры")
    private Double fats = 3.6; // Пример жиров

    @Column(name = "Название")
    private String mealName = "Куриная грудка"; // Название блюда

    @Column(name = "Белки")
    private Double proteins = 31.0; // Пример белков

    @Column(name = "Дата") // Новый столбец "Дата"
    private LocalDate date;

    @Transient // Вычисляемое поле, не сохраняется в БД
    private double dailyCalorieIntake;

    public enum Goal {
        LOSE_WEIGHT, MAINTAIN_WEIGHT, GAIN_WEIGHT
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum ActivityLevel {
        SEDENTARY, LIGHTLY_ACTIVE, MODERATELY_ACTIVE, VERY_ACTIVE, EXTRA_ACTIVE
    }
}
