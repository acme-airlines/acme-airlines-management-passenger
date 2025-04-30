package co.edu.uni.acme.airline.passenger.module.utils.constantes;

public class Constants {

    public static final String NAME_REQUIRED = "El nombre del pasajero es obligatorio.";
    public static final String LASTNAME_REQUIRED = "El apellido del pasajero es obligatorio.";
    public static final String DOCUMENT_INVALID = "El documento debe tener entre 6 y 10 dígitos numéricos.";
    public static final String BIRTHDATE_REQUIRED = "La fecha de nacimiento es obligatoria.";
    public static final String AGE_INVALID = "La edad del pasajero no es válida.";
    public static final String UNDERAGE_NEEDS_ADULT = "Pasajero menor de edad debe estar acompañado por un adulto.";
    public static final String PASSENGER_NOT_FOUND = "El passenger no existe";
    public static final String DOCUMENT_TYPE_NOT_FOUND = "El document type no existe";
    public static final String DOCUMENT_REGEX = "\\d{6,10}";
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 100;


}
