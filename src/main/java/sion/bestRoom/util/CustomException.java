package sion.bestRoom.util;

import org.hibernate.QueryException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

public class CustomException extends Exception {
    public CustomException(String msg) { super(msg); }
}