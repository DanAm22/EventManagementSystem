package registry;

import java.io.Serializable;

public class Guest implements Serializable {

    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;

    private static final long serialVersionUID = 1;

    // Constructor
    public Guest(String lastName, String firstName, String email, String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Metoda de verificare dupa lastName si firstName
    public boolean checkByNames(String lastName, String firstName) {
        return this.firstName.equals(firstName) && this.lastName.equals(lastName);
    }

    // Metoda de verificare dupa email
    public boolean checkByEmail(String email) {
        return this.email.equals(email);
    }

    // Metoda de verificare dupa phoneNumber
    public boolean checkByPhoneNumber(String phoneNumber) {
        return this.phoneNumber.equals(phoneNumber);
    }

    // Metoda de update a field-urilor
    public boolean update(String firstName, String lastName,
                          String email, String phoneNumber) {
        if (firstName != null) {
            this.firstName = firstName;
            return true;
        }
        if (lastName != null) {
            this.lastName = lastName;
            return true;
        }
        if (email != null) {
            this.email = email;
            return true;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
            return true;
        }

        return false;
    }

    public boolean search(String searchBy) {
        searchBy = searchBy.toLowerCase();
        String firstName = getFirstName().toLowerCase();
        String lastName = getLastName().toLowerCase();
        String email = getEmail().toLowerCase();
        String phoneNumber = getPhoneNumber().toLowerCase();
        return firstName.indexOf(searchBy) != -1 || lastName.indexOf(searchBy) != -1 || email.indexOf(searchBy) != -1 || phoneNumber.indexOf(searchBy) != -1;
    }

    public void notifyParticipation() {
        System.out.println("[" + this.firstName + " " + this.lastName +
                "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
    }

    public void notifyWaitingList(int index) {
        System.out.println("[" + this.firstName + " " + this.lastName
                + "] Te-ai inscris cu succes in lista de asteptare "
                + "si ai primit numarul de ordine " + index
                + ". Te vom notifica daca un loc devine disponibil.");
    }

    @Override
    public String toString() {
        return "Nume: " + this.firstName + " " + this.lastName + ", Email: " + this.email + ", Numar de telefon: " + this.phoneNumber;
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

        Guest other = (Guest) obj;
        return this.lastName.equals(other.lastName) && this.firstName.equals(other.firstName) && this.email.equals(other.email) && this.phoneNumber.equals(other.phoneNumber);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
        result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
        result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
        result = prime * result + ((this.phoneNumber == null) ? 0 : this.phoneNumber.hashCode());
        return result;
    }

}
