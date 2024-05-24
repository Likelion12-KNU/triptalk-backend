package com.example.triptalk.security.handler;

import com.example.triptalk.dto.UserInfoDto;
import com.example.triptalk.exception.ErrorResponse;
import com.example.triptalk.exception.TokenException;
import com.example.triptalk.security.UserDetailsImpl;
import com.example.triptalk.service.UserInfoService;
import com.example.triptalk.util.JwtUtil;
import com.google.gson.Gson;
import javax.servlet.*;
import javax.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


@Log4j2
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final UserInfoService userInfoService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Login Success Handler.........");

        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);

        // 토큰 발급
        String accessToken = jwtUtil.getAccessToken(authentication);
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        try {
            // 응답 본문 구성
            Gson gson = new Gson();
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            if(userDetails==null){
                throw new TokenException(TokenException.TOKEN_ERROR.UNACCEPT);
            }
            UserInfoDto userInfoDTO=userInfoService.read(userDetails.getId());

            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.putItem("message","Login Successful");
            errorResponse.putItem("user", userInfoDTO);

            response.getWriter().write(gson.toJson(errorResponse.getResponse()));
        }
        catch(TokenException e){
            log.error("TokenException: "+e.getMessage());
            e.sendResponseError(response);
        }
    }
}
