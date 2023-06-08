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
    public void AnimalsShowTableAll() {
        // Создаем объект Модель - она содержит коллекцию объектов нужного типа
        // позволяет загружать, сохранять и вносить изменения в коллекцию
        // показать таблицу Игрушки полностью
        // загружаем данные в модель из файла
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        ReturnToPrevPos();
        showBuyersMenu();
    }

    public boolean BuyerAddNew() {
        // Добавление игрушки
        // через параметр передаем сканер чтобы не создавать новый.
        // И для консольного приложения рекомендуется использовать один сканер
        // загружаем данные в модель из файла
        BuyersModel buyersModel = new BuyersModel();
        if (!buyersModel.load()) {
            System.out.println("\nФункция добавления покупателя прервана.");
            return false;
        }

        int curId = buyersModel.getNewId();
        // Вводим значения полей
        System.out.println("\nДобавление покупателя. Введите значения полей.");
        System.out.print("ФИО: ");
        try {
            String curFullName = sc.nextLine();
            System.out.print("Номер чека: ");
            String curCheckNumber = sc.nextLine();
            System.out.print("Номер телефона: ");
            String curPhone = sc.nextLine();
            Buyer curBuyer = new Buyer(curId, curFullName, curCheckNumber,
                    curPhone);
            buyersModel.add(curBuyer);
        } catch (Exception ex) {
            System.out.println("Ошибка при вводе данных о покупателе.\n" + ex.toString());
            return false;
        }

        if (buyersModel.save()) {
            System.out.println("Новый покупатель успешно добавлен!");
        } else {
            System.out.println("Ошибка при добавлении нового покупателя.");
            return false;
        }
        return true;
    }

    public boolean BuyerDeleteById() {
        // Удаление игрушки
        // загружаем данные в модель из файла

        // Показываем список игрушек для выбора id для удаления
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        System.out.print("\nВведите id удаляемого покупателя: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            // удаляем запись
            if (buyersModel.deleteById(curId)) {
                // сохраняем данные в файл
                buyersModel.save();
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Ошибка при удалении покупателя.\n" + ex.toString());
            return false;
        }
        return false;
    }

    public boolean BuyerEdit() {
        // Редактирование игрушки
        Buyer editedBuyer;
        // Показываем список игрушек для выбора id для редактирования
        BuyersModel buyersModel = new BuyersModel();
        if (buyersModel.load()) {
            BuyersView buyersView = new BuyersView(buyersModel.getBuyersAll());
            buyersView.ShowTable();
        }
        System.out.print("\nВведите id редактируемого покупателя: ");
        try {
            int curId = Integer.parseInt(sc.nextLine());
            editedBuyer = buyersModel.getBuyerById(curId);
            String curValue;
            System.out.println("Введите новые значения полей (Enter - оставить прежнее значение).");
            // Получаем новые значения полей
            System.out.print("ФИО (прежнее значение): " +
                    editedBuyer.getFullName() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setFullName(curValue);

            System.out.print("Номер чека (прежнее значение): " +
                    editedBuyer.getCheckNumber() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setCheckNumber(curValue);

            System.out.print("Номер телефона (прежнее значение): " +
                    editedBuyer.getPhone() +
                    "\nНовое значение: ");
            curValue = sc.nextLine();
            if (!curValue.equals(""))
                editedBuyer.setPhone(curValue);

            // сохраняем данные в файл
            if (buyersModel.save()) {
                System.out.println("Данные покупателя с id=" + curId + " успешно отредактированы.");
                return true;
            }

        } catch (Exception ex) {
            System.out.println("Ошибка при редактировании данных покупателя.\n" + ex.toString());
            return false;
        }
        return false;
    }

    // методы для обработки меню - Игрушки
    public void ToysShowTableAll() {
        // Создаем объект Модель - она содержит коллекцию объектов нужного типа
        // позволяет загружать, сохранять и вносить изменения в коллекцию
        // показать таблицу Игрушки полностью
        // загружаем данные в модель из файла
