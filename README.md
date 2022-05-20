# Monopoly

## Server bug(s)

| Bug behaviour  | How to reproduce  | Why it hasn't been fixed    |
|---|---|---|
| Wanneer dat de turn aan jou is en je gaat bankrupt. Wordt de turn niet automatisch doorgeschoven naar de volgende player. Je kan dus niet verderspelen, omdat de turn aan de speler is die bankrupt is. Wanneer de speler echter bankrupt gaat wanneer het niet aan zijn beurd is kan het spel verdergespeeld worden zonder die extra persoon. (server side) |   Wanneer iemand bankrupt gaat wordt de turn niet finished gezet. Hierdoor blijft het zogezegd aan de speler die bankrupt is. | tijd, complexitijd. Je zou moeten een nieuwe classe aanmaken of de volledige player classe aanpassen en game meegeven...|