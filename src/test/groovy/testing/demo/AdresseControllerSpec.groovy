package testing.demo

import client.AdresseClient
import grails.testing.web.controllers.ControllerUnitTest
import model.AdresseInfo
import org.springframework.http.HttpStatus
import spock.lang.Specification

class AdresseControllerSpec extends Specification implements ControllerUnitTest<AdresseController> {

    void setup() {
        controller.adresseClient = Mock(AdresseClient)
        controller.securityService = Stub(SecurityService) { harAdgang() >> true }
    }

    void "find adresser"() {
        given:
        params.adresse = 'Et sted'
        when:
        controller.find()

        then:
        1 * controller.adresseClient.findAllAdresser('Et sted') >> [new AdresseInfo(tekst: 'kryf')]

        and:
        response.json.size() == 1
    }

    void 'search med adgang'() {
        given:
        params.with {
            vejnavn = 'Langelinie Alle'
            husnr = 19
            postnr = 2100
        }
        when:
        controller.show()

        then:
        1 * controller.adresseClient.findAllByVejnavnHusnummerAndPostnr('Langelinie Alle', '19', '2100') >> [
                new AdresseInfo(tekst: 'kryf')]

        and:
        response.json.size() == 1
    }

    void 'search uden adgang'() {
        given:
        controller.securityService = Stub(SecurityService)

        when:
        controller.show()

        then:
        0 * controller.adresseClient.findAllByVejnavnHusnummerAndPostnr(*_)

        and:
        response.status == HttpStatus.FORBIDDEN.value()
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
