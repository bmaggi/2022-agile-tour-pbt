# 2022-agile-tour-pbt

live et slides :
[🎥](https://youtu.be/Ta3nztSnze4)
[📄](https://bmaggi.github.io/2022-agile-tour-pbt/PRESENTATION.html#1)

## Proposition faite pour l'Agile Tour Bordeaux 2022

Le property based testing pour répondre aux questions métier

### Présentation des bases du Property Based Testing:
* Les origines avec l'arrivée du functional programming
* Patterns : Round-tripping, Commutativity, Invariant, Idempotence, Oracle
* Réflexions générales : PBT vs fuzzing, performances ...

### Avantage d'un framework sur une approche manuelle avec "du simple random"
* Ingrédients d'un bon générateur : librairie, répartition des datas...
* Shrinking !!!
* Rapport de test (avec possibilité de le rejouer)

### Exemple sur une application maison en Java en utilisant la librairie [jqwik](https://jqwik.net/)

"Suite à une réorganisation un nouveau PO arrive sur le projet, il y trouve une _faible_ documentation
et commence à poser pleins de questions sur les comportements de l'application"

On verra comment les générateurs aident à expliciter les entités métier
et que le PBT peut répondre aux questions ouvertes sur les règles métier

## Sketch note 

![Poster](docs/Poster_PBT_BMA_AgileTour2022.jpg?raw=true "Poster")


## Infos technique

* Source pour le projet bubble factory dans le dossier démo 
* Slides générés grâce à [Marp for VS Code](https://github.com/marp-team/marp-vscode)
* Diagrammes fait avec [Mermaid](https://mermaid-js.github.io)
* Framework PBT utilisé [jqwick](https://jqwik.net/)

## Tips pour une prochaine dois 
* entrainement filmé
* bien indiquer l'accroche de transition dans les notes 
* amener une télécommande pour passer les slides 
* arriver en avance pour valider le matos avant #démo
* avoir deux t-shirt pour en avoir un différent du fond (meilleur rendu sur la vidéo) 
