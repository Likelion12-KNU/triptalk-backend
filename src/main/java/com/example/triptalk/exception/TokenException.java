package com.example.triptalk.exception;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class TokenException extends RuntimeException {
    private final TOKEN_ERROR tokenError;

    @Getter
    public enum TOKEN_ERROR{
        //인증 안 한 채로 요청해서 UserDetails 가 null 인 경우 UNACCEPT
        UNACCEPT(HttpStatus.UNAUTHORIZED, "Token is null or too short"),
        MALFORM(HttpStatus.UNAUTHORIZED, "Invalid Token"),
        EXPIRED(HttpStatus.UNAUTHORIZED, "Expired Token"),
        BADSIGN(HttpStatus.FORBIDDEN, "Bad Signatured Token"),
        UNSUPPORTED(HttpStatus.FORBIDDEN, "Unsupported Token");

        private final HttpStatus status;
        private final String message;

        TOKEN_ERROR(HttpStatus status, String message){
            this.status=status;
            this.message=message;
        }
    }

    public TokenException(TOKEN_ERROR error){
        super(error.name());
        this.tokenError=error;
    }

    public HttpStatus getStatus(){
        return tokenError.getStatus();
    }

    @Override
    public String getMessage(){
        return tokenError.getMessage();
    }

    public void sendResponseError(HttpServletResponse resp){
        resp.setStatus(tokenError.getStatus().value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson=new Gson();
        String responseBody=gson.toJson(Map.of("message",tokenError.getMessage(), "time", new Date()));

        try{
            resp.getWriter().println(responseBody);
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
