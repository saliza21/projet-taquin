# 🧩 Taquin

Un jeu de **Taquin** (puzzle à glissement) en Java avec deux modes de jeu : **interface graphique** (Swing) et **console**, supportant des grilles de taille personnalisée et des images découpées en pièces.

---

## 📋 Description

Le Taquin est un puzzle classique composé d'une grille de cases numérotées avec une case vide. Le but est de remettre toutes les pièces dans l'ordre en les faisant glisser vers la case vide. Cette implémentation supporte des grilles de n'importe quelle taille (NxM) et permet d'utiliser une **image personnalisée** découpée en tuiles à la place des numéros.

---

## ✨ Fonctionnalités

- 🖥️ **Mode graphique** : interface Swing avec boutons cliquables, compteur de coups, et pop-up de victoire
- 💻 **Mode console** : jeu en ligne de commande avec affichage de la grille et des mouvements possibles
- 🔢 **Grille personnalisable** : choix libre du nombre de lignes et colonnes au démarrage
- 🖼️ **Mode image** : chargement d'une image via un sélecteur de fichiers, découpée et mélangée comme pièces du puzzle
- 🎨 **Highlight des pièces jouables** : les cases déplaçables passent en vert au survol de la souris
- 🔀 **Mélange aléatoire** : le puzzle est mélangé par une série de mouvements valides (entre 10 et 109 coups) pour garantir une position résoluble
- ✅ **Détection automatique de la victoire** : le jeu vérifie à chaque coup si le puzzle est résolu
- ✂️ **Outil ImageCutter** : utilitaire autonome pour découper une image en pièces et les sauvegarder en fichiers PNG

---

## 🏗️ Architecture

Le projet suit le patron **MVC** avec un système d'**Observer** (`ModeleEcoutable` / `EcouteurModele`).

```
src/
├── Main.java                        # Point d'entrée — choix du mode (console ou graphique)
├── ImageCutter.java                 # Utilitaire autonome de découpe d'image en PNG
├── controller/
│   └── PuzzleController.java        # Contrôleur : création du puzzle, délégation des mouvements
├── model/
│   ├── Puzzle.java                  # Modèle : grille, case vide, mélange, mouvements, détection victoire
│   └── PuzzleItem.java              # Représente une case du puzzle (valeur, image, position x/y)
├── util/
│   ├── ModeleEcoutable.java         # Interface Observer
│   ├── AbstrcatModeleEcoutable.java # Implémentation abstraite (fireChangement / ajoutEcouteur)
│   └── EcouteurModele.java          # Interface Écouteur (modeleMisAJour)
└── view/
    └── PuzzleView.java              # Vue principale (JFrame) — grille de boutons, menu, compteur de coups
```

---

## 🎮 Utilisation

### Au lancement, choisir le mode :

```
Choisir un mode pour démarrer le jeu:
1 - interface graphique
2 - console
```

### Mode graphique (mode 1)

- La fenêtre s'ouvre avec un puzzle **3x3** par défaut.
- Via le menu **Game → New game** : saisir le nombre de lignes et colonnes, et optionnellement charger une **image** (bouton *image*).
- Cliquer sur une **pièce qui passe en vert** au survol pour la déplacer.
- Le compteur de coups s'affiche en bas de la fenêtre.
- Une **pop-up de félicitations** apparaît quand le puzzle est résolu.

### Mode console (mode 2)

- Saisir le nombre de lignes puis de colonnes.
- Les mouvements possibles sont affichés à chaque tour :

| Touche | Direction du déplacement de la case vide |
|--------|------------------------------------------|
| `Z`    | Vers le haut                             |
| `S`    | Vers le bas                              |
| `Q`    | Vers la gauche                           |
| `D`    | Vers la droite                           |

- Appuyer sur `q` pour quitter.

---

## ✂️ Utilitaire ImageCutter

`ImageCutter.java` est un outil standalone qui permet de charger une image et de la découper en pièces PNG sauvegardées localement (nommées `image_piece_Y_X.png`).

```bash
java ImageCutter
```

---

## 🚀 Lancer le projet

### Prérequis

- **Java 17** (Amazon Corretto 17 recommandé)
- **IntelliJ IDEA** (configuration `.idea` incluse avec artefact `.jar` préconfiguré)

### Depuis IntelliJ

1. Ouvrir le projet dans IntelliJ IDEA
2. Lancer `Main.java`

### Depuis le `.jar`

```bash
java -jar out/artifacts/Taquin_jar/Taquin.jar
```

---

## 📄 Licence

Projet académique – Java 17 (2024).
