package BDV1_LAB2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;


public class BDV1_LAB2 {

   
    public static void main(String[] args) {
         try {
            // Адрес нашей базы данных "kdk10_lab2" на локальном компьютере (localhost)
            String url = "jdbc:mysql://localhost:3306/d_14?&serverTimezone=Asia/Almaty&useSSL=false";

            // Создание свойств соединения с базой данных
            Properties authorization = new Properties();
            authorization.put("user", "root"); // Зададим имя пользователя БД
            authorization.put("password", "root"); // Зададим пароль доступа в БД

            // Создание соединения с базой данных
            Connection connection = DriverManager.getConnection(url, authorization);

            // Создание оператора доступа к базе данных
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            // Выполнение запроса к базе данных, получение набора данных
            ResultSet table = statement.executeQuery("SELECT * FROM avto ");

            System.out.println("Начальная БД:");
            
            table.first(); // Выведем имена полей
            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
            }
            System.out.println();

            table.beforeFirst(); // Выведем записи таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            Scanner sc = new Scanner(System.in); 
            System.out.println("Введите параметры нового поля таблицы:");
            System.out.print("name - ");
            String scannedName = sc.nextLine();
            System.out.print("year - ");
            String scannedManufacturer = sc.nextLine();
            
            // Выполнение запроса к базе данных на добавление записи
            statement.execute("INSERT avto(name, year) VALUES ('" + scannedName + "', '" + scannedManufacturer + "')");
            
            System.out.println("После добавления:");
            // Выполнение запроса к базе данных, получение набора данных
            table = statement.executeQuery("SELECT * FROM avto");
            // Вывод всех данных таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            System.out.println("Строку с каким id хотите удалить?");
            System.out.print("id - ");
            int scannedId = sc.nextInt();
            
            // Выполнение запроса к базе данных на удаление записи по id
            statement.execute("DELETE FROM avto WHERE id = " + scannedId);
            
            System.out.println("После удаления:");
            // Выполнение запроса к базе данных, получение набора данных
            table = statement.executeQuery("SELECT * FROM avto");
            // Вывод всех данных таблицы
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }
            
            
        sc.nextLine();
            System.out.print("Введите фрагмент названия для фильтрации: ");
           
            String filter = sc.nextLine();
            System.out.println("Данные таблицы с фильтром и сортировкой:");
            table = statement.executeQuery("SELECT * FROM avto WHERE name like '%"+filter+"%'" );  
            
            while (table.next()) {
                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                    System.out.print(table.getString(j) + "\t\t");
                }
                System.out.println();
            }

            if (table != null) {
                table.close();
            } // Закрытие набора данных
            
            if (statement != null) {
                statement.close();
            } // Закрытие базы данных
            
            if (connection != null) {
                connection.close();
            } // Отключение от базы данных
            
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }
    
    
}
