package com.upcloud_bj.penguin.auth.sidecar.resourceserver.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "penguin")
public class PenguinProperties {

    private OAuth2 oauth2;
    private ResourceServer resourceserver;

    public ResourceServer getResourceserver() {
        return resourceserver;
    }

    public void setResourceserver(ResourceServer resourceserver) {
        this.resourceserver = resourceserver;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2 oauth2) {
        this.oauth2 = oauth2;
    }

    public static class ResourceServer {
        private List<PermitUri> permitUris;
        private List<Permission> permissions;

        public List<PermitUri> getPermitUris() {
            return permitUris;
        }

        public void setPermitUris(List<PermitUri> permitUris) {
            this.permitUris = permitUris;
        }

        public List<Permission> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<Permission> permissions) {
            this.permissions = permissions;
        }
    }

    public static class OAuth2 {
        private String jwtTokenSigningKey;

        public String getJwtTokenSigningKey() {
            return jwtTokenSigningKey;
        }

        public void setJwtTokenSigningKey(String jwtTokenSigningKey) {
            this.jwtTokenSigningKey = jwtTokenSigningKey;
        }
    }

    public static class Permission {
        private String enName;
        private String uri;
        private List<String> methods;

        public String getEnName() {
            return enName;
        }

        public void setEnName(String enName) {
            this.enName = enName;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }
    }

    public static class PermitUri {
        private String uri;
        private List<String> methods;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }
    }
}





