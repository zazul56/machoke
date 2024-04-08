Random stvari za imati na umu:

Paljenje svega:
1. Otvoriti XAMPP i startati MySQL
	- Zgodno je otvoriti i neki preglednik za bazu (DBeaver, SQLyog), connection string: jdbc:mysql://localhost:3306/db_gym username:root (nema pass)
2. Otvoriti Eclipse
   - Tu i tamo povući promjene s gita (git pull na bilo koji način ili u Eclipseu: desni klik na projekt -> Team -> ...)
    - Tu i tamo refreshati projekt/povući dependencyje. Desni klik na build.gradle file -> Gradle -> Refresh gradle project
      
   ![image](https://github.com/zazul56/machoke/assets/146677003/40a261f8-8976-4272-8fcf-e3166e4bebf4)

	2.3. Otvoriti klasu GymApplication.java pa u Eclipse toolbaru kliknuti Run -> Run as -> Java application ili Spring boot app (što god ima ponuđeno)
		- Ima inače i shortcut u oblik ikone (na hover piše Run...)
   
   ![image](https://github.com/zazul56/machoke/assets/146677003/a9454745-2e9f-420a-bccb-2b511d8e5aef)

4. Otvoriti u browseru API tester: base url je http://localhost:8080

NOVO:
Integrirana dokumentacija: http://localhost:8080/swagger-ui/index.html

Trenutno stanje:
- /api/auth/login i /api/auth/register su javni (dostupni svima)
- /api/* -> GET pozivi trenutno dostupni svima (možda promijeniti ubuduće)
- /api/users -> POST - kreira novog korisnika (trenutno radi isto što i register, ali ovu metodu može pokrenuti samo korisnik koji ima ulogu administratora "ROLE_ADMIN").
  - Kako testirati? Prvo pokrenuti /login. Na uspješnom loginu se dobije token koji se dalje šalje kao autorizacijski header. Ovo će trebati slati na svim requestovima koje treba autorizirati
