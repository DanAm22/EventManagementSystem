package exceptions;

public class InvalidOption extends RuntimeException{

    public InvalidOption(){
        super();
    }

    public InvalidOption(String message){
        super(message);
    }

}
