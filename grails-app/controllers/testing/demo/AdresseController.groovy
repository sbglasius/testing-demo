package testing.demo

import client.AdresseClient
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class AdresseController {
    @Autowired
    AdresseClient adresseClient
    
    SecurityService securityService
    
    def find(String adresse) {
        render adresseClient.findAllAdresser(adresse) as JSON
    }
    
    def show(String vejnavn, String husnr, String postnr) {
        if(securityService.harAdgang()) {
            render adresseClient.findAllByVejnavnHusnummerAndPostnr(vejnavn, husnr, postnr) as JSON
        } else {
            render status: HttpStatus.FORBIDDEN
        }
    }
    
}
