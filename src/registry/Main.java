package registry;

import exceptions.EmailException;
import exceptions.InvalidOption;
import exceptions.NameException;
import exceptions.PhoneException;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String waitingOrderMessage = "\nAsteapta comanda: " + Orders.help + Orders.help.getMessage() + "\n";
    private static final String authFilter = "Alege modul de autentificare, tastand: \n\t \"1\" - Nume si prenume" +
            "\n\t \"2\" - Email " + "\n\t \"3\" - Numar de telefon \n";
    private static final String updateFilter = "Alege campul de actualizat, tastand: " + "\n\t\"1\" - Nume"
            + "\n\t\"2\" - Prenume" + "\n\t\"3\" - Email" + "\n\t\"4\" - Numar de telefon";


    private static Scanner scanner = new Scanner(System.in);
    private static GuestsList guestsList;

    private static String getLastName() throws NameException {
        System.out.println("Introduceti numele de familie:");
        String lastName = scanner.next();
        for (Character c : lastName.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new NameException("Numele de familie nu poate contine caractere non-alfabetice.");
            }
        }
        return lastName;
    }

    private static String getFirstName() throws NameException {
        System.out.println("Introduceti prenumele:");
        String firstName = scanner.next();
        for (Character c : firstName.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new NameException("Prenumele nu poate contine caractere non-alfabetice.");
            }
        }
        return firstName;
    }

    private static String getEmail() throws EmailException {
        System.out.println("Introduceti email:");
        String email = scanner.next();
        if (!email.contains("@")) {
            throw new EmailException("Email-ul nu contine caracterului '@'.");
        }
        return email;
    }

    private static String getPhoneNumber() throws PhoneException {
        System.out.println("Introduceti numar de telefon (format \"+40733386463\"):");
        String phoneNumber = scanner.next();
        if (!(phoneNumber.startsWith("02") || phoneNumber.startsWith("03") || phoneNumber.startsWith("07"))) {
            throw new PhoneException("Numarul de telefon introdus este invalid.");
        }
        return phoneNumber;
    }

    private static void add() throws NameException, EmailException, PhoneException, IOException {
        System.out.println("Se adauga o noua persoana...");
        String firstName = getFirstName();
        String lastName = getLastName();
        String email = getEmail();
        String telefon = getPhoneNumber();
        Guest guest = new Guest(firstName, lastName, email, telefon);
        int result = guestsList.add(guest);
        if (result < 0) {
            System.out.println("Persoana este deja inscrisa la eveniment.");
        }
    }

    private static void check() throws InvalidOption, NameException, EmailException, PhoneException {
        System.out.println("Se verifica daca o persoana este inscrisa...");
        int index = searchGuestByOption();
        System.out.println("Persoana " + ((index < 0) ? "nu" : "")
                + " este inscrisa la eveniment.");
    }

    private static int searchGuestByOption() throws InvalidOption, NameException, EmailException, PhoneException {

        String firstName = null, lastName = null, email = null, phoneNumber = null;
        System.out.println(authFilter);
        int option = scanner.nextInt();
        scanner.nextLine(); // ignore the new line
        if (option < 1 && option > 3) {
            throw new InvalidOption("Optiune invalida. Reincearca din nou");
        }
        switch (option) {
            case 1:
                firstName = getFirstName();
                lastName = getLastName();
                break;
            case 2:
                email = getEmail();
                break;
            case 3:
                phoneNumber = getPhoneNumber();
                break;
        }
        return guestsList.findGuestPosition(firstName, lastName, email, phoneNumber);
    }


    private static boolean updateGuest(Guest guest) throws InvalidOption, NameException, EmailException, PhoneException {

        String lastName = null, firstName = null, email = null, phoneNumber = null;
        System.out.println(updateFilter);

        int option = scanner.nextInt();
        scanner.nextLine();
        if (option < 1 && option > 4) {
            throw new InvalidOption("Optiune invalida. Reincearca din nou");
        }
        switch (option) {
            case 1:
                lastName = getLastName();
                break;
            case 2:
                firstName = getFirstName();
                break;
            case 3:
                email = getEmail();
                break;
            case 4:
                phoneNumber = getPhoneNumber();
                break;
        }
        return guest.update(lastName, firstName, email, phoneNumber);
    }

    private static void update() throws InvalidOption, NameException, EmailException, PhoneException {
        System.out.println("Se actualizeaza detaliile unei persoane...");
        int position = searchGuestByOption();

        if (position < 0) {
            System.out.println("Eroare: Persoana nu este inscrisa la eveniment.");
            return;
        }

        updateGuest(guestsList.get(position));

    }


    private static void remove() throws InvalidOption, NameException, EmailException, PhoneException {
        System.out.println("Se sterge o persoana existenta din lista...");
        int index = searchGuestByOption();

        if (index < 0) {
            System.out.println("Persoana nu este inscrisa la eveniment.");
            return;
        }

        guestsList.removeGuest(index);
        System.out.println("Stergerea persoanei s-a realizat cu succes.");
    }

    private static void search() {
        System.out.println("Se cauta toti invitatii"
                + " conform sirului de caractere introdus...");
        System.out.println("Introduceti sirul de caractere (fara spatii):");
        String searchBy = scanner.nextLine();
        ArrayList<Guest> list = guestsList.search(searchBy);
        for (Guest guest : list) {
            System.out.println(guest);
        }
    }

    private static void guestsList() {
        guestsList.printGuestsList();
    }

    private static void waitingList() {
        guestsList.printWaitingList();
    }

    private static void available() {
        System.out.println(guestsList.available());
    }

    private static void guestNo() {
        System.out.println(guestsList.getGuestsSize());
    }

    private static void waitingNo() {
        System.out.println(guestsList.getWaitingSize());
    }

    private static void subscribeNo() {
        System.out.println(guestsList.getSubscribeSize());
    }

    private static void writeToBinaryFile(List<Guest> data) throws IOException {
        try (ObjectOutputStream binaryFileOut = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream("guestsList.dat")))) {
            binaryFileOut.writeObject(data);
        }
    }

    private static ArrayList<Guest> readGuestsFromBinaryFile() throws IOException {
        ArrayList<Guest> data = null;

        try (ObjectInputStream binaryFileIn = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream("guestsList.dat")))) {
            data = (ArrayList<Guest>) binaryFileIn.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("A class not found exception: " + e.getMessage());
        }

        return data;
    }

    private static void writeNoSeatsToBinaryFile(int noSeats) throws IOException {
        try (DataOutputStream binaryFileOut = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream("noSeats.dat")))) {
            binaryFileOut.writeInt(noSeats);
        } catch (IOException e) {
            System.out.println("IOException thrown: " + e.getMessage());
            return;
        }
    }

    private static int readNoSeatsFromBinaryFile() throws IOException {
        int noSeats = 0;
        try (DataInputStream binaryFileIn = new DataInputStream(
                new BufferedInputStream(new FileInputStream("noSeats.dat")))) {
            noSeats = binaryFileIn.readInt();
        }
        return noSeats;
    }


    public static void main(String[] args) throws InvalidOption, NameException, EmailException, PhoneException, IOException {

        try {
            ArrayList<Guest> guests = readGuestsFromBinaryFile();
            int noSeats = readNoSeatsFromBinaryFile();
            if (noSeats > 0) {
                guestsList = new GuestsList(noSeats, guests);
            }
        } catch (IOException e) {
            System.out.println("Bun venit! Introduceti numarul de locuri disponibile:");
            while (true) {
                try {
                    int noSeats = scanner.nextInt();
                    guestsList = new GuestsList(noSeats);
                    writeNoSeatsToBinaryFile(guestsList.getNoSeats());
                    break;
                } catch (InputMismatchException ie) {
                    scanner.nextLine();
                    System.out.println("Nu ai introdus un numar intreg. Reincearca din nou.");
                }
            }
        }

            System.out.println(waitingOrderMessage);

            while (true) {
                switch (scanner.next()) {
                    case "help":
                        Orders.printValues();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "add":
                        while (true) {
                            try {
                                add();
                                writeToBinaryFile(guestsList.getGuests());
                                break;
                            } catch (NameException e) {
                                System.out.println("Numele / prenumele introdus este eronat. Reincearca din nou.");
                            } catch (EmailException e) {
                                System.out.println("Adresa de e-mail este invalida. Reincearca din nou.");
                            } catch (PhoneException e) {
                                System.out.println("Numarul de telefon este eronat. Reincearca din nou.");
                            }
                        }
                        System.out.println(waitingOrderMessage);
                        break;
                    case "check":
                        while (true) {
                            try {
                                check();
                                break;
                            } catch (InputMismatchException | InvalidOption e) {
                                System.out.println("Optiune invalida. Reincearca din nou.");
                            } catch (NameException e) {
                                System.out.println("Numele / prenumele introdus este eronat. Reincearca din nou.");
                            } catch (EmailException e) {
                                System.out.println("Adresa de e-mail este invalida. Reincearca din nou.");
                            } catch (PhoneException e) {
                                System.out.println("Numarul de telefon este eronat. Reincearca din nou.");
                            }
                        }
                        System.out.println(waitingOrderMessage);
                        break;
                    case "remove":
                        while (true) {
                            try {
                                remove();
                                writeToBinaryFile(guestsList.getGuests());
                                break;
                            } catch (InputMismatchException | InvalidOption e) {
                                System.out.println("Optiune invalida. Reincearca din nou.");
                            } catch (NameException e) {
                                System.out.println("Numele / prenumele introdus este eronat. Reincearca din nou.");
                            } catch (EmailException e) {
                                System.out.println("Adresa de e-mail este invalida. Reincearca din nou.");
                            } catch (PhoneException e) {
                                System.out.println("Numarul de telefon este eronat. Reincearca din nou.");
                            }
                        }
                        break;
                    case "update":
                        while (true) {
                            try {
                                update();
                                writeToBinaryFile(guestsList.getGuests());
                                break;
                            } catch (InputMismatchException | InvalidOption e) {
                                System.out.println("Optiune invalida. Reincearca din nou.");
                            } catch (NameException e) {
                                System.out.println("Numele / prenumele introdus este eronat. Reincearca din nou.");
                            } catch (EmailException e) {
                                System.out.println("Adresa de e-mail este invalida. Reincearca din nou.");
                            } catch (PhoneException e) {
                                System.out.println("Numarul de telefon este eronat. Reincearca din nou.");
                            }
                        }
                        break;
                    case "guests":
                        guestsList();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "waitlist":
                        waitingList();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "available":
                        available();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "guests_no":
                        guestNo();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "waitlist_no":
                        waitingList();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "subscribe_no":
                        subscribeNo();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "search":
                        search();
                        System.out.println(waitingOrderMessage);
                        break;
                    case "quit":
                        System.out.println("Inchide aplicatia...");
                        System.exit(0);
                        break;
                }
            }
        }
    }

