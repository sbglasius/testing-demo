package testing.demo

import client.AdresseClient
import grails.testing.web.controllers.ControllerUnitTest
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AdresseControllerSecurityServiceStubSpec extends Specification implements ControllerUnitTest<AdresseController> {

    @Override
    Closure doWithSpring() {
        return { -> securityService(SecurityServiceStub, true) }
    }

    void setup() {
        controller.adresseClient = Mock(AdresseClient)
    }

    void 'search med adgang uden resultat'() {
        given:
        params.with {
            vejnavn = 'Langelinie Alle'
            husnr = 19
            postnr = 2100
        }
        when:
        controller.show()

        then:
        1 * controller.adresseClient.findAllByVejnavnHusnummerAndPostnr('Langelinie Alle', '19', '2100')

        and:
        response.status == HttpStatus.NOT_FOUND.value()
    }

}
