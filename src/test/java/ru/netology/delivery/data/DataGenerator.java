package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int daysForShift) {
        return LocalDate.now().plusDays(daysForShift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) throws IOException {
        // TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных городов и класс Random
        //Faker faker = new Faker(new Locale(locale));
        //String city = "Москва";
        //String city = faker.address().city();
        Charset charset = StandardCharsets.UTF_8;
        String townsFile = "src/test/java/ru/netology/delivery/data/towns.txt";
        ArrayList<String> cities = new ArrayList<>(Files.readAllLines(Paths.get(townsFile), charset));
        Random rand = new Random();
        int randomIndex = rand.nextInt(cities.size());
        String city = cities.get(randomIndex);
        return city;
    }

    public static String generateName(String locale) {
        // TODO: добавить логику для объявления переменной name и задания её значения, для генерации можно
        // использовать Faker
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name.replace('ё', 'е');
    }

    public static String generatePhone(String locale) {
        // TODO: добавить логику для объявления переменной phone и задания её значения, для генерации можно
        // использовать Faker
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) throws IOException {
            DataGenerator.UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(locale),
            // generateName(locale), generatePhone(locale)
            //UserInfo user = new UserInfo();
            //Registration.generateUser(locale).name=generateName(locale);
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
