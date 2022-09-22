package client

import grails.gorm.transactions.Transactional
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import model.AdresseInfo

@Client('https://api.dataforsyningen.dk')
interface AdresseClient {
    
    @Get('/adresser/autocomplete?q={q}')
    List<AdresseInfo> findAllAdresser(@PathVariable String q)
    
    @Get('/adgangsadresser?vejnavn={vejnavn}&husnr=${husnummer}&postnr={postnummer}&fuzzy=true')
    List<AdresseInfo> findAllByVejnavnHusnummerAndPostnr(@PathVariable String vejnavn, @PathVariable String husnummer, @PathVariable String postnummer)
}
