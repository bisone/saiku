package com.bisone.saiku.interceptor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles for SSO request headers to create Authorization ids.
 * Optional operations can be assigned by setting the SSOLoginHandler;
 * for example, to create corresponding user accounts if the user doesn't exist.
 */
public class SSORequestHeaderAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final Logger log = LoggerFactory.getLogger(SSORequestHeaderAuthenticationFilter.class);
    private String principalRequestHeader = "REMOTE_USER";
    /**
     * Configure a value in the applicationContext-security for local tests.
     */
    private String testUserId = "admin";
    /**
     * Configure whether a missing SSO header is an exception.
     */
    private boolean exceptionIfHeaderMissing = false;

    /**
     * Read and return header named by <tt>principalRequestHeader</tt> from Request
     *
    * @throws PreAuthenticatedCredentialsNotFoundException if the header is missing and
     *                                                      <tt>exceptionIfHeaderMissing</tt> is set to <tt>true</tt>.
     */
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {


        String principal = request.getHeader(principalRequestHeader);

       log.debug("获得认证信息===" + principal);


        if (principal == null) {
            if (exceptionIfHeaderMissing) {
                throw new PreAuthenticatedCredentialsNotFoundException(principalRequestHeader
                        + " header not found in request.");
           }
            if (StringUtils.isNotBlank(testUserId)) {
                log.warn("spring configuration has a test user id " + testUserId);
                principal = testUserId;
            } else if (request.getSession().getAttribute("session_user") != null) {
                // A bit of a hack for testers - allow the principal to be
                // obtained by session. Must be set by a page with no security filters enabled.
                // should remove for production.
                principal = (String) request.getSession().getAttribute("session_user");
            }
        }
        // also set it into the session, sometimes that's easier for jsp/faces
        // to get at..
        request.getSession().setAttribute("session_user", principal);
        log.debug("获得认证信息===" + principal);
        return principal;
    }

    /**
     * Credentials aren't applicable here for OAM WebGate SSO.
     */
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "password_not_applicable";
    }

    public void setPrincipalRequestHeader(String principalRequestHeader) {
        Assert.hasText(principalRequestHeader, "principalRequestHeader must not be empty or null");
        this.principalRequestHeader = principalRequestHeader;
    }

    public void setTestUserId(String testId) {
        if (StringUtils.isNotBlank(testId)) {
            this.testUserId = testId;
        }
    }

    /**
     * Exception if the principal header is missing. Default <tt>false</tt>
    *
     * @param exceptionIfHeaderMissing
     */
    public void setExceptionIfHeaderMissing(boolean exceptionIfHeaderMissing) {
        this.exceptionIfHeaderMissing = exceptionIfHeaderMissing;
    }

}
