Eksempel på implementering av CQRS med Event Sourcing i Akka. Noen todos er plassert i koden som en liten start-oppgave...

* * *
Til alle dere andre som også har sett sannheten og lyset:
*   [Bra gjennomgang på MSDN](http://msdn.microsoft.com/en-us/library/jj554200.aspx) 
*   [Rinat Abdullins blogg, mye bra](http://abdullin.com/) 
*   [Google group for CQRS og DDD:](https://groups.google.com/forum/?fromgroups#!forum/dddcqrs) 
*   [Akka google group](https://groups.google.com/forum/?fromgroups#!forum/akka-user) 
*   [Akka](http://akka.io/)
*   [Akka eventsourced](https://github.com/eligosource/eventsourced)

* * *
Noen tips og regler vi har kommet frem til etter en hel masse roting (NB: Sikkert noen som er uenig i en eller flere av disse, dette er vår take hittil)

*   Eventer skal være en minimalistisk beskrivelse av en hendelse som endrer tilstanden i din applikasjon, og ikke inneholde implementasjon-spesifikk informasjon.
*   Motstå fristelsen til å lage generelle hendelser, f.eks. av typen " BrukerInfoEndret" med verdi som beskriver hvilket felt det gjelder.
*   Prøv å unngå CRUD events/commands; meldFlytting -> flyttingMeldt er bedre enn oppdaterAddresse -> adresseOppdatert.
*   En projeksjon skal bare lytte til eventer i ett aggregat.
*   Sagaer og projeksjoner skal bare motta eventer fra event-store, aldri direkte.
*   Event-store skal bare leses sekvensielt for å publisere til projeksjoner eller sager, ingen ad-hock spørringer er lov.
*   Unngå gjenbruk, ikke benytt eventer direkte i projeksjoner. Lag heller nye objekter.
*   Verken command handlere eller projeksjoner skal noen gang dispatche commands.
*   En projeksjon eller saga skal ikke gjøre queries mot andre projeksjoner.
*   Foretrekk flere enkle sagaer over en kompleks.
*   Foretrekk flere brukspesifikke projeksjoner over en generell.
