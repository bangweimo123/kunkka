package com.leshiguang.arch.kunkka.web.config;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-17 12:47
 * @Modify
 */
public class KunkkaProxyServlet extends ProxyServlet {

    private String index;

    private Pattern ignore;

    public KunkkaProxyServlet(String ignorePattern, String index) {
        this.ignore = Pattern.compile(ignorePattern);
        this.index = index;
    }

    @Override
    protected String rewriteUrlFromRequest(HttpServletRequest servletRequest) {
        if (servletRequest.getPathInfo() != null) {
            Matcher matcher = ignore.matcher(servletRequest.getPathInfo());
            if (matcher.find()) {
                return super.rewriteUrlFromRequest(servletRequest);
            }
        }
        StringBuilder uri = new StringBuilder(500);
        uri.append(getTargetUri(servletRequest));
        uri.append(index);
        return uri.toString();
    }
}
