package com.bisone.saiku.security;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

/**
 * Created by will on 15-8-25.
 */
public class HeaderAuthenticationDetails extends
        WebAuthenticationDetailsSource {

    public HeaderAuthenticationDetails() {
        //super.setClazz(PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails.class);
    }


}
