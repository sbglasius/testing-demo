package testing.demo

import client.AdresseClient
import com.agorapulse.gru.Gru
import com.agorapulse.gru.grails.Grails
import grails.testing.web.controllers.ControllerUnitTest
import model.Adresse
import model.AdresseInfo
import spock.lang.AutoCleanup
import spock.lang.Specification

class AdresseControllerGruSpec extends Specification implements ControllerUnitTest<AdresseController> {

    @AutoCleanup
    Gru gru = Gru.create(Grails.create(this)).prepare {
        include UrlMappings
    }

    void setup() {
        controller.adresseClient = Mock(AdresseClient)
        controller.securityService = Stub(SecurityService) { harAdgang() >> true }
    }

    void "find adresser"() {
        when:
        gru.test {
            get '/adresse/find', {
                params adresse: 'Et sted'
            }
            expect {
                json 'find_adresser.json'
            }
        }

        then:
        1 * controller.adresseClient.findAllAdresser('Et sted') >> [adresseInfo]

        and:
        gru.verify()
    }


    void 'search med adgang'() {
        when:
        gru.test {
            get '/adresse/show', {
                params vejnavn: 'Langelinie Alle',
                        husnr: 19,
                        postnr: 2100
            }
            expect {
                json 'show_adresser_ok.json'
            }
        }
        then:
        1 * controller.adresseClient.findAllByVejnavnHusnummerAndPostnr('Langelinie Alle', '19', '2100') >> [adresseInfo]
        and:
        gru.verify()
    }

    void 'search uden adgang'() {
        given:
        controller.securityService = Stub(SecurityService)

        expect:
        gru.test {
            get '/adresse/show', {
                params vejnavn: 'Langelinie Alle',
                        husnr: 19,
                        postnr: 2100
            }
            expect {
                status FORBIDDEN
            }
        }

    }

    void 'search med adgang uden resultat'() {
        expect:
        gru.test {
            get '/adresse/show', {
                params vejnavn: 'Langelinie Alle',
                        husnr: 19,
                        postnr: 2100
            }
            expect {
                status NOT_FOUND
            }
        }
    }

    private AdresseInfo getAdresseInfo() {
        return new AdresseInfo(tekst: 'Langelinie Allé 19, 2100 København Ø', adresse: adresse)
    }

    private Adresse getAdresse() {
        return new Adresse(
                id: '0a3f507a-b3f5-32b8-e044-0003ba298018',
                vejkode: '4143',
                vejnavn: 'Langelinie Allé 19',
                husnr: '19',
                postnr: '2100',
                postnrnavn: 'København Ø')
    }

}
