# Shareholders App Backend -ohjelman tiivistelmä

`shareholders_app_backend` on monipuolinen Spring Boot -sovellus, joka tarjoaa tehokkaan RESTful API -rajapinnan osakkeenomistajien ja osakkeiden hallintaan.

## Toiminnallisuus

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

- **Luo:** Uuden osakkeenomistajan lisääminen.
- **Hae:** Olemassa olevien osakkeenomistajien hakeminen.
- **Päivitä:** Osakkeenomistajan tietojen päivittäminen.
- **Poista:** Osakkeenomistajan poistaminen.

**Tiedot, joita hallinnoidaan:**

- **Nimi ja tunnus:** Osakkeenomistajan tai yrityksen nimi sekä henkilötunnus tai Y-tunnus.
- **Yhteystiedot:** Osoite, sähköposti, puhelinnumero ja pankkitilitiedot.
- **Osakkeet:** Osakkeiden määrä, alku- ja loppunumerot.
- **Omistus:** Yhteismäärä ja prosentuaalinen osuus omistetuista osakkeista.

#### Esimerkki osakkeenomistajasta:

- **Osakkeenomistaja 1:**
  - Nimi: **PHZ Full Stack Oy**
  - Henkilötunnus tai Y-tunnus: **2765147-9**
  - Osoite: **Katu 1**, Helsinki
  - Sähköposti: **info@phzfullstack.fi**
  - Puhelinnumero: **(+358) 40 123 4567**
  - Pankkitilitiedot: **FI1234567890123456**
  - Yhteensä osakkeita: **3,000,000**
  - Omistusprosentti: **60,00%**

### 2. Osakkeet

- **Luo:** Uuden osakkeen luominen.
- **Hae:** Olemassa olevien osakkeiden hakeminen.
- **Päivitä:** Osakkeen tietojen päivittäminen.
- **Siirrä:** Osakkeiden siirtäminen osakkeenomistajien välillä.

**Osakkeiden tiedot:**

- Osakkeilla on seuraavat tiedot:
  - **ID:** Osakkeen yksilöivä tunnus.
  - **Määrä:** Omistettujen osakkeiden määrä.
  - **Alkunumero:** Ensimmäinen osakkeen numero.
  - **Loppunumero:** Viimeinen osakkeen numero.
  - **Osakkeenomistajan ID:** Osakkeenomistaja, jolle osakkeet kuuluvat.

#### Esimerkkejä osakkeista:

- **ID 1:** Määrä **1,000,000** (alkunumero **1**, loppunumero **1,000,000**) - Osakkeenomistaja **1**
- **ID 2:** Määrä **500,000** (alkunumero **1,000,001**, loppunumero **1,500,000**) - Osakkeenomistaja **2**
- **ID 3:** Määrä **250,000** (alkunumero **1,500,001**, loppunumero **1,750,000**) - Osakkeenomistaja **2**
- **ID 4:** Määrä **100,000** (alkunumero **1,750,001**, loppunumero **1,850,000**) - Osakkeenomistaja **3**
- **ID 5:** Määrä **50,000** (alkunumero **1,850,001**, loppunumero **1,900,000**) - Osakkeenomistaja **3**

### 3. Siirto

- **Luo:** Uuden osakkeen siirron luominen.

#### Siirron tiedot:

- **Nro:** Yksilöivä tunnus siirrolle.
- **Sääntöpäivä:** Siirron toteutuspäivämäärä, jolloin osakkeet virallisesti siirtyvät luovuttajalta saajalle.
- **Maksupvm:** Maksupäivämäärä, joka voi olla sama kuin sääntöpäivä tai muu sovittu päivä. Kenttä voidaan jättää tyhjäksi ja lisätä myöhemmin.
- **Luovuttaja (Myyjä):** Luovuttaja, joka siirtää osakkeet.
- **Saaja (Ostaja):** Saaja, joka vastaanottaa osakkeet.
- **Kpl:** Siirrettävien osakkeiden määrä.
- **Hinta per osake:** Yhden osakkeen hinta.
- **Varainsiirtovero:** Osake-siirrosta perittävä vero. Ilmoita **true**, jos vero peritään, tai **false**, jos vero ei peritä. Tämä tieto tallennetaan siirtohistoriaan.
- **Total:** Lasketaan automaattisesti siirron bruttosumma ennen veroja.
- **Muut tiedot:** Lisätiedot tai huomautukset.

### 4. Osakehistoria

- **Hae:** Aikaisempien siirtojen tarkastelu.
- **Päivitä:** Maksupäivämäärän päivittäminen.

#### Osakehistorian tiedot:

- **Nro:** Yksilöivä tunnus (ID) siirrolle.
- **Sääntöpäivä:** Siirron toteutuspäivämäärä.
- **Maksupvm:** Maksupäivämäärä, jolloin maksu osakkeista on suoritettava.
- **Luovuttaja (Myyjä):** Osakkeiden luovuttaja.
- **Saaja (Ostaja):** Osakkeiden vastaanottaja.
- **Varainsiirtovero:** Ilmoittaa, onko osake-siirrosta peritty vero (**true**, jos peritään; **false**, jos ei).
- **Kpl:** Siirrettyjen tai myytyjen osakkeiden määrä.
- **Hinta per 1 osake:** Yhden osakkeen hinta.
- **Kokonaisarvo:** Myynnin bruttosumma ennen veroja.
- **Muut tiedot:** Lisätiedot tai huomautukset.

### 5. Poikkeusten käsittely

- **Käsittely:** Käyttäjäystävällisten virheilmoitusten tarjoaminen poikkeustilanteissa.

## Kohderyhmät

- **Osakeyhtiöt:** Erityisen hyödyllinen osakeyhtiöille osakkeenomistajatietojen ja -omistusten hallintaan.
- **Taloustoimistot:** Tarjoaa asiakkaille ajankohtaisia tietoja osakeomistuksista ja siirroista.
- **Sijoittajat:** Mahdollistaa omistusten hallinnan ja siirtohistorian seuraamisen.
- **Kehittäjät:** RESTful API:n kautta integraatioiden ja sovellusten kehittäminen osakkeenomistajatietojen hyödyntämiseksi.

---

**Yhteenveto:**  
Sovellus tarjoaa kattavan ja joustavan ratkaisun osakkeenomistajien ja osakkeiden hallintaan, mahdollistaen tietojen tehokkaan hallinnan ja muokkaamisen RESTful API:n kautta.
