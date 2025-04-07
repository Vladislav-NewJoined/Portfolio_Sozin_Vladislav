package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        user.setDailyCalorieIntake(calculateDailyCalories(user));
        return userRepository.save(user);
    }

    public double calculateDailyCalories(User user) {
        double bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge();
        switch (user.getGoal()) {
            case LOSE_WEIGHT:
                return bmr * 0.8;
            case GAIN_WEIGHT:
                return bmr * 1.2;
            default:
                return bmr;
        }
    }
}
