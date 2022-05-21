# Monopoly

## Server bug(s)

Na vele testen hebben we alle bugs weten op te lossen. We hebben geprobeerd om nog een bug aan de server te vinden, maar kunnen jammer genoeg geen terugvinden.

| Bug behaviour                                                                       | How to reproduce                                                                                                                                                                                                                                                                                                                                                 | Why it hasn't been fixed                      |
|-------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------|

## Token

### Monopoly Api Bridge
Als je in je endpoint een controle op autorisatie moet doen dan maak je gebruik van onderstaande code:
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

### Hoe wij denken dat de token werkt

User instance is een User (MonopolyUser) instance van de player dat de request zend.
Er is dus geen nood om de game token te controleren. Game token wordt gebruikt om de User instance te verkrijgen.
Als de User instance niet is geautoriseerd wordt null returned (isAuthorised is false).
Als de User instance is geautoriseerd, wordt de meegegeven playerName en gameId van het pad vergeleken met de User instance (isAuthorised is true).
