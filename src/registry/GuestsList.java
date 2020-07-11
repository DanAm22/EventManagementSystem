package registry;

import java.io.*;
import java.util.ArrayList;

public class GuestsList implements Serializable {

    private int noSeats;
    private ArrayList<Guest> guests;

    private static final long serialVersionUID = 1;

    public GuestsList(int noSeats) {
        this.noSeats = noSeats;
        this.guests = new ArrayList<Guest>(this.noSeats);
    }

    public int add(Guest guest) {
        if (check(guest)) {
            return -1;
        }

        this.guests.add(guest);
        if (this.guests.size() <= this.noSeats) {
            guest.notifyParticipation();
            return 0;
        }

        int indexWaitList = this.guests.size() - this.noSeats;
        guest.notifyWaitingList(indexWaitList);
        return indexWaitList;
    }

    private boolean check(Guest guest) {
        for (int i = 0; i < this.guests.size(); i++) {
            if (this.guests.get(i).equals(guest)) {
                return true;
            }
        }
        return false;
    }

    public int findGuestPosition(String firstName, String lastName,
                                 String email, String phoneNumber) {
        if (firstName != null && lastName != null) {
            return searchGuestByName(firstName, lastName);
        }
        if (email != null) {
            return searchGuestByEmail(email);
        }
        if (phoneNumber != null) {
            return searchGuestByPhone(phoneNumber);
        }
        System.out.println("Error: An error occured in 'GuestList',"
                + " method 'findGuestPosition(...)'");
        return -1;
    }

    public boolean removeGuest(int index) {
        if (index < 0 || index >= this.guests.size()) {
            System.out.println("Index out of bounds");
            return false;
        }

        if (index < this.noSeats && this.guests.size() > this.noSeats) {
            guests.get(this.noSeats).notifyParticipation();
        }
        this.guests.remove(index);

        return true;
    }

    public Guest get(int index) {
        if (index < 0 || index >= this.guests.size()) {
            System.out.println("Error: Index out of bounds");
            return null;
        }
        return this.guests.get(index);
    }

    public ArrayList<Guest> search(String searchBy) {
        ArrayList<Guest> list = new ArrayList<Guest>();
        for (int i = 0; i < this.guests.size(); i++) {
            if (this.guests.get(i).search(searchBy)) {
                list.add(this.guests.get(i));
            }
        }
        return list;
    }

    public void printGuestsList() {
        int total = this.getGuestsSize();

        if (total == 0) {
            System.out.println("Lista participantilor este goala...");
            return;
        }

        for (int i = 0; i < total; i++) {
            System.out.println((i + 1) + ". " + this.guests.get(i));
        }
    }

    public void printWaitingList() {
        int total = this.getWaitingSize();

        if (total == 0) {
            System.out.println("Lista de asteptare este goala...");
            return;
        }

        for (int i = this.noSeats; i < this.guests.size(); i++) {
            System.out.println((i - this.noSeats + 1) + ". "
                    + this.guests.get(i));
        }
    }

    public int available() {
        int availableSeats = this.noSeats - this.guests.size();
        return Math.max(availableSeats, 0);
    }

    public int getGuestsSize() {
        return Math.min(this.noSeats, this.guests.size());
    }

    public int getWaitingSize() {
        int waitlistSize = this.guests.size() - this.noSeats;
        return Math.max(waitlistSize, 0);
    }

    public int getSubscribeSize() {
        return this.guests.size();
    }

    private int searchGuestByName(String firstName, String lastName) {
        for (int i = 0; i < this.guests.size(); i++) {
            if (this.guests.get(i).checkByNames(firstName, lastName)) {
                return i;
            }
        }
        return -1;
    }

    private int searchGuestByEmail(String email) {
        for (int i = 0; i < this.guests.size(); i++) {
            if (this.guests.get(i).checkByEmail(email)) {
                return i;
            }
        }
        return -1;
    }

    private int searchGuestByPhone(String phoneNumber) {
        for (int i = 0; i < this.guests.size(); i++) {
            if (this.guests.get(i).checkByPhoneNumber(phoneNumber)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Numarul de locuri disponibile pentru eveniment este: " + this.noSeats + ", s-au inscris " + this.guests.size() + " persoane, iar pe lista de asteptare sunt " + (this.guests.size() - this.noSeats) + " persoane.";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }
        GuestsList other = (GuestsList) obj;
        return this.noSeats == other.noSeats && this.guests.equals(other.guests);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + this.noSeats;
        result = prime * result + ((this.guests == null) ? 0 : this.guests.hashCode());
        return result;
    }
}
