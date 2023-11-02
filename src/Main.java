import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите данные (Фамилия Имя Отчество дата рождения номер телефона пол):");
        String inputData = scanner.nextLine();

        String[] dataArray = inputData.split(" ");

        if (dataArray.length != 6) {
            System.out.println("Ошибка: неверное количество данных!");
            return;
        }

        String lastName = dataArray[0];
        String firstName = dataArray[1];
        String patronymic = dataArray[2];
        String birthDateStr = dataArray[3];
        String phoneNumberStr = dataArray[4];
        String genderStr = dataArray[5];

        LocalDate birthDate;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            birthDate = LocalDate.parse(birthDateStr, dateFormat);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: неверный формат даты рождения!");
            return;
        }

        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: номер телефона должен быть числом!");
            return;
        }

        char gender = genderStr.charAt(0);
        if (gender != 'f' && gender != 'm') {
            System.out.println("Ошибка: пол должен быть 'f' или 'm'!");
            return;
        }

        System.out.println("Данные успешно приняты:");
        System.out.println("Фамилия: " + lastName);
        System.out.println("Имя: " + firstName);
        System.out.println("Отчество: " + patronymic);
        System.out.println("Дата рождения: " + birthDate);
        System.out.println("Номер телефона: " + phoneNumber);
        System.out.println("Пол: " + gender);

        var filename = lastName + ".txt";
        BufferedWriter writer = null;

        try {
            File myObj = new File(filename);
            var exists = myObj.exists();
            writer = new BufferedWriter(new FileWriter(filename, true));
            if (!exists) {
                System.out.println("Файл создан: " + myObj.getName());
                writer.write(String.format("%s %s %s %s %s %s", lastName, firstName, patronymic, birthDate.format(dateFormat), phoneNumber, gender));
            } else {
                System.out.println("Файл уже существует. Записываем новые данные однофамильца.");
                try {
                    writer.write(String.format("\n%s %s %s %s %s %s", lastName, firstName, patronymic, birthDate.format(dateFormat), phoneNumber, gender));
                } catch (IOException ioe) {
                    System.out.println("Ошибка: " + ioe.getMessage());
                    ioe.printStackTrace();
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}