package pl.mateusz.redosz.nbp.exception;

public class InvalidSortPropertyException extends RuntimeException {

    public InvalidSortPropertyException(String property) {
        super("Nieprawidłowe pole sortowania: " + property +
                ". Dozwolone pola: name, accountingDate");
    }
}