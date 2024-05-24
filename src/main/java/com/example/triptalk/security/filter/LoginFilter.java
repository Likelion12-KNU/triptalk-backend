package com.example.triptalk.security.filter;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@Log4j2
public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    public LoginFilter(String defaultFilterProcessesUrl){
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("Login Filter.........");

        //GET 방식으로 호출하면 request 에서 NullPointerException 발생
        if(request.getMethod().equalsIgnoreCase("GET")){
            log.info("GET METHOD NOT SUPPORT");
            return null;
        }

        Map<String, String> requestJson=parseRequestJSON(request);
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                requestJson.get("username"),
                requestJson.get("password")
        );
        Authentication authentication=getAuthenticationManager().authenticate(authenticationToken);

        return authentication;
    }

    /**
     * HTTP 요청을 JSON 형태로 변환합니다.
     * @param request HTTP 요청
     * @return JSON 형태의 HTTP 요청
     */
    private Map<String, String> parseRequestJSON(HttpServletRequest request){
        try(Reader reader=new InputStreamReader(request.getInputStream())){
            Gson gson=new Gson();
            return gson.fromJson(reader, Map.class);
        }catch(Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}

