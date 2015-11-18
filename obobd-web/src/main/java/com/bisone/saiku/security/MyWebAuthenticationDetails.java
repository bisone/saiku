package com.bisone.saiku.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by will on 15-8-25.
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private Map<String, String> additionalHeaders;

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }

//    @Override
//    protected void doPopulateAdditionalInformation(HttpServletRequest request) {
//        super.doPopulateAdditionalInformation(request);
//
//        additionalHeaders = new HashMap<String, String>();
//
//        Enumeration<String> allHeaders = request.getHeaderNames();
//        while (allHeaders.hasMoreElements()) {
//            String header = allHeaders.nextElement();
//            additionalHeaders.put(header, request.getHeader(header));
//        }
//    }

    public Map<String, String> getAdditionalHeaders() {
        return additionalHeaders;
    }
}