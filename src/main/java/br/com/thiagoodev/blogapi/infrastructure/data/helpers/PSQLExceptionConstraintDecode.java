package br.com.thiagoodev.blogapi.infrastructure.data.helpers;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;

public class PSQLExceptionConstraintDecode {
    public static String decode(DataIntegrityViolationException error) {
        Throwable cause = error.getRootCause();

        if(!(cause instanceof PSQLException psqlException)) return null;

        ServerErrorMessage message = psqlException.getServerErrorMessage();

        if(message == null) return null;

        return message.getConstraint();
    }
}
