import view.*;
import classes.*;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    private String prevPos; // текущая позиция меню в формате "1,2" пустая строка означает выбрано главное
                            // меню
    private String choice; // текущий выбранный пункт меню
    private String newPos; // = prevPos + choice
    private boolean ShowNewChoice; // = признак необходимости показать ввод нового пункта меню
    private Scanner sc;
    DateTimeFormatter formatter; //формат даты времени

    public void run() {
        showProgramGreeting();

        // Построение и работа с меню
        sc = new Scanner(System.in);
        while (true) {
            ShowNewChoice = true;
            // формируем новую позицию меню = прежнее позиция меню + выбранный пункт
            if (getChoice() != "") {
                if (getPrevPos() != "") {
                    setNewPos((getPrevPos() + "," +
                            getChoice()));
                } else {
                    setNewPos(getChoice());
                }
            } else {
                // откатываемся на предыдущую позицию меню
                if (getPrevPos() != "") {
                    setNewPos(getPrevPos());
                } else {
                    setNewPos("");
                }
            }
            // обработка общих случаев
            if (getChoice().equals("0")) {
                // означает - Перейти в главное меню
                showMainMenu();
            } else if (getChoice().equals("q")) {
                // означает - Выход из программы
                showProgramExit();
                return;
            } else {
                // Обработка конкретных пунктов меню в соответствии с новой позицией меню
                switch (getNewPos()) {
                    case (""): // главное меню
                        showMainMenu();
                        break;

                    // меню Животные
                    case ("1"): // показать меню Животные
                        showAnimalsMenu();
                        //showBuyersMenu();
                        break;

                    case ("1,1"): // меню Животные - Показать таблицу
                        AnimalsShowTableAll();
                        break;

                    case ("1,2"): // меню Животные - Добавить
                        if (AnimalAddNew()) {
                            AnimalsShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,3"): // меню Животные - Редактировать
                        if (AnimalEdit()) {
                            AnimalShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,4"): // меню Животные - Добавить команды
                        if (AnimalAddCommands()) {
                            AnimalShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    case ("1,5"): // меню Животные - Удалить
                        if (AnimalDeleteById()) {
                            AnimalShowTableAll();
                        } else {
                            setChoice("");
                            ShowNewChoice = false;
                        }
                        break;

                    default:
                        showMenuItemNotFound();
                        // возвращаем указатель меню на предыдущее положение
                        // потому что обработчик не найден
                        setChoice("");
                        ShowNewChoice = false;
                        break;
                }
            }
            // для отладки
            // System.out.println("Отладка. Текущая позиция меню: " + getNewPos());

            if (ShowNewChoice) {
                // Считываем новый выбранный пункт меню - Choice
                System.out.printf("Укажите пункт меню: ");
                try {
                    setChoice(sc.nextLine().trim());
                } catch (NoSuchElementException exception) {
                    System.out.println("Пункт меню не выбран");
                    setChoice("");
                }
            }
        }
    }

    public void showProgramGreeting() {
        // показать заголовок программы
        System.out.println();
        String s1 = "Программа - Мой питомник";
        System.out.println(s1);
        System.out.println("-".repeat(s1.length()));
    }

    public void showMainMenu() {
        // показать меню Игрушки в консоли
        String s1 = "Главное меню";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Животные");
        System.out.println("q. Выход из программы.");
        ResetMenuPos();
    }

    public void showAnimalsMenu() {
        // показать меню Покупатели в консоли
        String s1 = "Меню - Животные";
        System.out.println("\n" + s1 + "\n" + "-".repeat(s1.length()));
        System.out.println("1. Показать таблицу из БД.");
        System.out.println("2. Добавить животное.");
        System.out.println("3. Редактировать данные животного.");
        System.out.println("4. Добавить команды животному.");
        System.out.println("5. Удалить животное.");
        System.out.println("0. Назад в Главное меню.");
        System.out.println("q. Выход.");
        setPrevPos(getNewPos());
    }

    public void showProgramExit() {
        // показать заголовок программы
        System.out.println();
        System.out.println("Завершение работы программы");
    }

    public void showMenuItemNotFound() {
        System.out.println("Не найден обработчик для указанного пункта меню.");
    }

    // методы для обработки меню - Покупатели
}