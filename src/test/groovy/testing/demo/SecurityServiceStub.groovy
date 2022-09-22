package testing.demo

class SecurityServiceStub extends SecurityService {
    boolean allowAccess 
    
    SecurityServiceStub(boolean allowAccess = true) {
        this.allowAccess = allowAccess
    }
    
    boolean harAdgang() {
        allowAccess
    }
    
    String getUsername() {
        'Svend Bent'
    }
}
