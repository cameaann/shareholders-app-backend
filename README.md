# Shareholders App Backend -ohjelman tiivistelmä

`shareholders_app_backend` on monipuolinen Spring Boot -sovellus, joka tarjoaa tehokkaan RESTful API -rajapinnan osakkeenomistajien ja osakkeiden hallintaan.

Sovelluksen avulla voit:

- **Luoda** uusia osakkeenomistajia ja osakkeita.
- **Hakea** tietoja olemassa olevista osakkeenomistajista ja osakkeista.
- **Päivittää** osakkeenomistajien ja osakkeiden tietoja tarpeen mukaan.
- **Poistaa** osakkeenomistajia ja osakkeita tarvittaessa.
- **Siirtää** osakkeita osakkeenomistajien välillä sekä hallita ja listata siirtohistoriaa.
- **Hallita** varainsiirtoveroa osakkeiden siirtojen yhteydessä.
- **Tarjota** käyttäjäystävällinen käyttöliittymä sekä tehokas virheiden käsittely.

Näiden toimintojen avulla voit toteuttaa tehokasta hallintaa ja muokkaamista osakkeenomistajien tiedoissa.

## Päätoiminnot

### 1. Osakkeenomistajat

- **Luo:** Lisää uusi osakkeenomistaja.
- **Hae:** Hae olemassa olevia osakkeenomistajia.
- **Päivitä:** Päivitä osakkeenomistajan tietoja.
- **Poista:** Poista osakkeenomistaja.

#### Tiedot, joita hallinnoidaan:

- **Nimi ja tunnus:** Osakkeenomistajan tai yrityksen nimi sekä henkilötunnus tai Y-tunnus.
- **Yhteystiedot:** Osoite, sähköposti, puhelinnumero ja pankkitilitiedot.
- **Osakkeet:** Osakkeiden määrä, alku- ja loppunumerot.
- **Omistus:** Yhteismäärä ja prosentuaalinen osuus omistetuista osakkeista.

**Esimerkki osakkeenomistajasta:**

- **Nimi:** PHZ Full Stack Oy
- **Henkilötunnus tai Y-tunnus:** 2765147-9
- **Osoite:** Katu 1, Helsinki
- **Sähköposti:** info@phzfullstack.fi
- **Puhelinnumero:** (+358) 40 123 4567
- **Pankkitilitiedot:** FI1234567890123456
- **Yhteensä osakkeita:** 3,000,000
- **Omistusprosentti:** 60,00%

### 2. Osakkeet

- **Luo:** Luo uusi osake.
- **Hae:** Hae olemassa olevia osakkeita.
- **Päivitä:** Päivitä osakkeen tietoja.
- **Siirrä:** Siirrä osakkeita osakkeenomistajien välillä.

#### Osakkeiden tiedot:

- **ID:** Osakkeen yksilöivä tunnus.
- **Määrä:** Omistettujen osakkeiden määrä.
- **Alkunumero:** Ensimmäinen osakkeen numero.
- **Loppunumero:** Viimeinen osakkeen numero.
- **Osakkeenomistajan ID:** Osakkeenomistaja, jolle osakkeet kuuluvat.

**Esimerkkejä osakkeista:**

- **ID 1:** Määrä 1,000,000 (alkunumero 1, loppunumero 1,000,000) - Osakkeenomistaja 1
- **ID 2:** Määrä 500,000 (alkunumero 1,000,001, loppunumero 1,500,000) - Osakkeenomistaja 2
- **ID 3:** Määrä 250,000 (alkunumero 1,500,001, loppunumero 1,750,000) - Osakkeenomistaja 2
- **ID 4:** Määrä 100,000 (alkunumero 1,750,001, loppunumero 1,850,000) - Osakkeenomistaja 3
- **ID 5:** Määrä 50,000 (alkunumero 1,850,001, loppunumero 1,900,000) - Osakkeenomistaja 3

### 3. Osakehistoria

- **Luo:** Luo uusi siirtohistoria.
- **Hae:** Tarkastele aikaisempia siirtoja.
- **Päivitä:** Päivitä maksupäivämäärä.

#### Osakehistorian tiedot:

- **Nro:** Yksilöivä tunnus.
- **Sääntöpäivä:** Siirron päivämäärä.
- **Maksupvm:** Maksupäivämäärä.
- **Luovuttaja (Myyjä):** Siirron aloittaja.
- **Saaja (Ostaja):** Siirron vastaanottaja.
- **Varainsiirtovero:** Osake-siirrosta perittävä vero (true/false).
- **Kpl:** Siirrettävien osakkeiden määrä.
- **Hinta per 1 osake:** Osakkeen hinta.
- **Kokonaisarvo:** Siirron bruttosumma ennen veroja.
- **Muut tiedot:** Lisätiedot tai huomautukset.

### 4. Poikkeusten käsittely

- **Käsittely:** Tarjoaa käyttäjäystävällisiä virheilmoituksia poikkeustilanteissa.

### Kohderyhmät

- **Osakeyhtiöt:** Erityisen hyödyllinen osakeyhtiöille osakkeenomistajatietojen ja -omistusten hallintaan.
- **Taloustoimistot:** Tarjoaa asiakkaille ajankohtaisia tietoja osakeomistuksista ja siirroista.
- **Sijoittajat:** Mahdollistaa omistusten hallinnan ja siirtohistorian seuraamisen.
- **Kehittäjät:** RESTful API:n kautta integraatioiden ja sovellusten kehittäminen osakkeenomistajatietojen hyödyntämiseksi.

**Yhteenveto:**  
Sovellus tarjoaa kattavan ja joustavan ratkaisun osakkeenomistajien ja osakkeiden hallintaan, mahdollistaen tietojen tehokkaan hallinnan ja muokkaamisen RESTful API:n kautta.
