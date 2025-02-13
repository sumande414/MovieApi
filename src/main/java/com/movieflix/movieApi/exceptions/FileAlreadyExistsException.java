package com.movieflix.movieApi.exceptions;

public class FileAlreadyExistsException extends RuntimeException{
    public FileAlreadyExistsException(String message){
        super(message);
    }
}
