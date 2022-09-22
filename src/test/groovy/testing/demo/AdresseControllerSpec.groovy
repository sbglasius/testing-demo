package testing.demo

import client.AdresseClient
import grails.testing.web.controllers.ControllerUnitTest
import model.AdresseInfo
import spock.lang.Specification

class AdresseControllerSpec extends Specification implements ControllerUnitTest<AdresseController> {

    void setup() {
        controller.adresseClient = Mock(AdresseClient)
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

}
