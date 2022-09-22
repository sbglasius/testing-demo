package testing.demo

import dk.sector9.grails.Sector9Service
import grails.gorm.transactions.Transactional

@Transactional
class SecurityService {

    Sector9Service sector9Service
    
    String getUsername() {
        return sector9Service.principal?.username
    }
    
    boolean harAdgang() {
        return sector9Service.principal?.hasRole('ROLE_WHATEVER')
    }
}
