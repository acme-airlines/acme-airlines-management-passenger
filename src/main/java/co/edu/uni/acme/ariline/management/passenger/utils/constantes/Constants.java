package co.edu.uni.acme.ariline.management.passenger.utils.constantes;

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

    // Error messages
    public static final String PASSENGER_COUNT_EXCEEDS_CAPACITY =
            "Passenger count exceeds the maximum capacity of the plane";
    public static final String MAIN_PASSENGER_MINOR_REQUIRES_COMPANION =
            "Main passenger is underage and requires a companion";
    public static final String COMPANION_RELATIONSHIP_REQUIRED =
            "Companion for the main passenger must have a specified relationship";
    public static final String SPECIAL_CONDITION_EMERGENCY_CONTACT_REQUIRED =
            "Passenger with special condition must have an emergency contact";
    public static final String MINOR_EMERGENCY_CONTACT_REQUIRED =
            "Minor passenger must have an emergency contact";
    public static final String EMERGENCY_CONTACT_RELATIONSHIP_REQUIRED =
            "Emergency contact must specify a relationship with the passenger";
}
