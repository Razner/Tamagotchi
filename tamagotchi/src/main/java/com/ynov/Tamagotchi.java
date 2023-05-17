package com.ynov;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Tamagotchi {
    private int age; // Âge en unités de temps
    private int happiness; // Niveau de bonheur
    private int happinessMax; // Niveau de bonheur maximal
    private int state; // 0 = oeuf, 1 = bébé, 2 = adulte, 3 = vieillard, 4 = mort
    private int nbPlay = 0; // Nombre de fois que le joueur a joué avec le Tamagotchi durant cette unité de temps
    private int nbEat = 0; // Nombre de fois que le joueur a nourri le Tamagotchi durant cette unité de temps
    private boolean isSick; // Tamagotchi malade ou non
    private boolean firstDayWithoutEating;
    private boolean secondDayWithoutEating;
    private boolean thirdDayWithoutEating;
    private boolean dirtyEnvironment;
    private static Scanner scanner = new Scanner(System.in);

    public Tamagotchi() {
        age = 0; // Âge initial
        happiness = 15; // Niveau de bonheur initial
        happinessMax = 50; // Niveau de bonheur maximal
        state = 0; // Le Tamagotchi commence sous forme d'oeuf
        this.firstDayWithoutEating = true;
        this.secondDayWithoutEating = true;
        this.thirdDayWithoutEating = true;
        this.dirtyEnvironment = false;
        this.isSick = false; // Le Tamagotchi a une chance sur trois d'être malade
    }

    public void passerUniteDeTemps() {
        age++;
        nbPlay = 0; // On réinitialise le nombre de fois que le joueur a joué avec le Tamagotchi
        if (state == 0) { // Phase d'oeuf
            happiness = 15;
            if (age >= 1) { // L'oeuf a éclos
                state = 1; // Passage à l'état de bébé
                System.out.println("Le Tamagotchi est né !");
                }
        } else if (state == 1) { // Phase de bébé
            happiness -= 10; // Le bonheur diminue de 10 à chaque unité de temps
            if (age >= 4 && happiness >= 40) { // Le bébé a été nourri 4 fois et est assez heureux
                state = 2; // Passage à l'état d'adulte
                System.out.println("Le Tamagotchi est devenu adulte !");
            }
        } else if (state == 2) { // Phase d'adulte
            happiness -= 10; // Le bonheur diminue de 10 à chaque unité de temps
            if (age >= 15) { // L'adulte est devenu vieillard
                state = 3; // Passage à l'état de vieillard
                System.out.println("Le Tamagotchi est devenu vieux !");
            }
        } else if (state == 3) { // Phase de vieillard
            happiness -= 10; // Le bonheur diminue de 10 à chaque unité de temps
                if (isSick == Math.random() < 1.0 / 3.0) {
                    System.out.println("Le Tamagotchi est malade.");
                    System.out.println("Il faut le soigner !");
                    heal(); // Appel de la méthode pour soigner le Tamagotchi
            }
            if (age >= 20) { // Le vieillard est mort de vieillesse
                state = 0; // Remise à zéro après la mort
                System.out.println("Le Tamagotchi est mort de vieillesse.");
            }
        }

        if (nbEat == 0 && age >= 1) { // Si le Tamagotchi n'a pas été nourri depuis la dernière unité de temps et qu'il n'est plus à l'état d'oeuf
            int pointsDeBonheurPerdus = 5; // On initialise le nombre de points de bonheur perdus à 5

            if (firstDayWithoutEating) { // Si c'est la première fois que le Tamagotchi ne mange pas
                pointsDeBonheurPerdus = 5; // On lui fait perdre 5 points de bonheur
                firstDayWithoutEating = false; // On indique que ce n'est plus la première fois qu'il ne mange pas
            } else if (secondDayWithoutEating) { // Si c'est le deuxième jour sans manger
                pointsDeBonheurPerdus = 10; // On lui fait perdre 10 points de bonheur
                secondDayWithoutEating = false; // On indique que ce n'est plus le deuxième jour sans manger
            } else if (thirdDayWithoutEating) { // Si c'est le troisième jour sans manger
                pointsDeBonheurPerdus = 15; // On lui fait perdre 15 points de bonheur
                thirdDayWithoutEating = false; // On indique que ce n'est plus le troisième jour sans manger
            }

            happiness -= pointsDeBonheurPerdus; // Le bonheur diminue en conséquence
            System.out.println(
                    "Le Tamagotchi a perdu " + pointsDeBonheurPerdus + " points de bonheur car il n'a pas mangé.");
        } else { // Si le Tamagotchi a mangé, on réinitialise le compteur de jours sans manger et les booléens indiquant les jours sans manger
            if (dirtyEnvironment) {
                happiness -= 3; // Ajouter 3 points de pénalité supplémentaires si l'environnement est sale
            }
            nbEat = 0;
            firstDayWithoutEating = true;
            secondDayWithoutEating = true;
            thirdDayWithoutEating = true;
        }

        if (happiness <= 0) {
            age = 0;
            happiness = 15;
            state = 0; // Remise à zéro car le Tamagotchi est mort
            System.out.println("Le Tamagotchi est mort de tristesse.");
            }
    }

    public void feed() {
        firstDayWithoutEating = false;
        if (nbEat < 1) {
            if (happiness + 5 <= happinessMax) {
                happiness += 5; // On ajoute 5 points de bonheur
            } else {
                happiness = happinessMax; // Le bonheur atteint le niveau maximal
            }
            nbEat++; // On incrémente le nombre de fois que le joueur a nourri le Tamagotchi
            dirtyEnvironment = true; // L'environnement devient sale après avoir mangé
            System.out.println("Le Tamagotchi a mangé !");
        } else if (nbEat >= 1) {
            System.out.println("Le Tamagotchi ne veut plus manger pour l'instant.");
            System.out.println("Votre Tamagotchi a déjà mangé, il pourra manger au prochain tour.");
        }
    }
    
    public void play() {
        if (nbPlay < 3) { // On ne peut pas jouer avec un Tamagotchi mort et on ne peut jouer que 3 fois
            if (happiness + 3 <= happinessMax) {
                happiness += 3; // On ajoute 3 points de bonheur
            } else {
                happiness = happinessMax; // Le bonheur atteint le niveau maximal
            }
            nbPlay++; // On incrémente le nombre de fois que le joueur a joué avec le Tamagotchi
            System.out.println("Le Tamagotchi est content !");
        } else if (nbPlay >= 3) { // Si on a joué 3 fois ce tour-ci
            System.out.println("Le Tamagotchi ne veut plus jouer pour l'instant.");
            System.out.println("Vous avez joué 3 fois, vous pourrez jouer à nouveau au prochain tour.");
        }
    }    

    public void clean() {
        dirtyEnvironment = false; // Marque l'environnement comme étant propre
        System.out.println("L'environnement du Tamagotchi est propre.");
    }

    public void heal() {
        System.out.println("Voulez-vous soigner le Tamagotchi ? (1: oui / 2: non)");
    
        int choix;
    
        while (true) {
            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Lire la ligne vide après avoir lu l'entier
    
                if (choix == 1) {
                    isSick = false; // Le Tamagotchi est soigné
                    System.out.println("Le Tamagotchi a été soigné !");
                } else if (choix == 2) {
                    state = 0; // Remise à zéro après la mort
                    System.out.println("Le Tamagotchi est mort parce que tu l'as pas soigné(e) !");
                } else {
                    System.out.println("Choix invalide !");
                    continue;
                }
    
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choix invalide ! Veuillez entrer un nombre.");
                scanner.nextLine(); // Vider le scanner pour éviter une boucle infinie
            }
        }
    }
       
    public static void main(String[] args) {
        Tamagotchi tamagotchi = new Tamagotchi();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuer = true;

            while (continuer) {
                System.out.println("Le Tamagotchi a " + tamagotchi.happiness + " de bonheur.");
                System.out.println("Que voulez-vous faire ?");
                System.out.println("1 : Passer une unité de temps");
                System.out.println("2 : Nourrir le Tamagotchi");
                System.out.println("3 : Jouer avec le Tamagotchi");
                System.out.println("4 : Nettoyer l'environnement du Tamagotchi");
                System.out.println("5 : Quitter");
                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        tamagotchi.passerUniteDeTemps();
                        break;
                    case 2:
                        tamagotchi.feed();
                        break;
                    case 3:
                        tamagotchi.play();
                        break;
                    case 4:
                        tamagotchi.clean();
                        break;
                    case 5:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide !");
                        break;
                }
            }
        }
    }
}
