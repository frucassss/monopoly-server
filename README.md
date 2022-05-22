# Monopoly

## Server bug(s)

Na vele testen hebben we alle bugs weten op te lossen. We hebben geprobeerd om een bug te vinden, maar dit was geen succes.

| Bug behaviour | How to reproduce | Why it hasn't been fixed |
|---------------|------------------|--------------------------|



## Token

### Monopoly Api Bridge
Om een endpoint te beveiligen, heeft u onderstaande code nodig om een request te authentiseren.
```java
if(!request.isAuthorized(gameId,playerName)){
        throw new ForbiddenAccessException(FORBIDDEN_ACCES_MESSAGE);
}
```

### isAuthorized functie in Request
De functie die controlleert of je juist bent geautoriseerd:
```java
public boolean isAuthorized(String expectedGameId,String expectedPlayerName){
        return Objects.equals(expectedGameId,user.getGameId())&&
        Objects.equals(expectedPlayerName,user.getPlayerName());
}
```

### Authenticatie met Bearer Token

Tijdens het creëren van een game wordt er door de server een een Bearer Token gemaakt met de game ID en de spelersnaam.    
> *Een Bearer Token is een herkenbare waarde gecreëerd door de authenticatieserver.   
Het is niet willekeurig; het is gemaakt op basis van de gebruiker die u toegang geeft.*  

Wanneer een gebruiker gebruik maakt van een beveiligde endpoint wordt de Bearer token in de header gesplitst op '-'.  
Zo kan de server de data bruikbaar maken voor de authenticatie.  
> *Bearer token: group28_0-Alice*
```json

{
        "gameId": "group28_0",
        "username": "Alice"
}

```

Deze data wordt tijdens de authenticatie vergeleken met de spelersnaam en de game ID die wordt meegegeven in de url.
