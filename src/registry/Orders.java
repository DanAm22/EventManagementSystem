package registry;

public enum Orders {
    help(" - Afiseaza aceasta lista de comenzi"),
    add(" - Adauga o noua persoana (inscriere)"),
    check(" - Verifica daca o persoana este inscrisa la eveniment"),
    remove( " - Sterge o persoana existenta din lista"),
    update( " - Actualizeaza detaliile unei persoane"),
    guests(" - Lista de persoane care participa la eveniment"),
    waitlist( " - Persoanele din lista de asteptare"),
    available( " - Numarul de locuri libere"),
    guests_no( " - Numarul de persoane care participa la eveniment"),
    waitlist_no( " - Numarul de persoane din lista de asteptare"),
    subscribe_no(" - Numarul total de persoane inscrise"),
    search( " - Cauta toti invitatii conform sirului de caractere introdus"),
    quit(" - Inchide aplicatia");

    private final String message;

    Orders(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    // Metoda pentru afisarea tuturor valorilor din enum si a mesajelor aferente acestora
    public static void printValues(){
        for(Orders o : Orders.values()){
            System.out.println(o + o.getMessage());
        }
    }
}
