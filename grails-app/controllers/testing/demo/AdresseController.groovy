package testing.demo

import client.AdresseClient
import grails.converters.JSON
import model.AdresseInfo
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
            List<AdresseInfo> vejnavnHusnummerAndPostnr = adresseClient.findAllByVejnavnHusnummerAndPostnr(vejnavn, husnr, postnr)
            if(!vejnavnHusnummerAndPostnr) {
                return render(status: HttpStatus.NOT_FOUND)
            }
            render vejnavnHusnummerAndPostnr as JSON
        } else {
            render status: HttpStatus.FORBIDDEN
        }
    }
    
}
