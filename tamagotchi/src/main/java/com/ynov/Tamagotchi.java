package com.ynov;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Tamagotchi {
    private int age;
    int happiness;
    private int happinessMax; 
    private int state;
    private int nbPlay = 0;
    private int nbEat = 0; 
    private boolean isSick; 
    private boolean firstDayWithoutEating;
    private boolean secondDayWithoutEating;
    private boolean thirdDayWithoutEating;
    private boolean dirtyEnvironment;
    private static Scanner scanner = new Scanner(System.in);

    public Tamagotchi() {
        age = 0; 
        happiness = 15;
        happinessMax = 50;
        state = 0; 
        this.firstDayWithoutEating = true;
        this.secondDayWithoutEating = true;
        this.thirdDayWithoutEating = true;
        this.dirtyEnvironment = false;
        this.isSick = false; 
    }

    public void UnitTime() {
        age++;
        nbPlay = 0; 
        if (state == 0) {
            happiness = 15;
            if (age >= 1) {
                state = 1; 
                System.out.println("Le Tamagotchi est né !");
                }
        } else if (state == 1) { 
            happiness -= 10; 
            if (age >= 4 && happiness >= 40) { 
                state = 2; 
                System.out.println("Le Tamagotchi est devenu adulte !");
            }
        } else if (state == 2) {
            happiness -= 10; 
            if (age >= 15) { 
                state = 3; 
                System.out.println("Le Tamagotchi est devenu vieux !");
            }
        } else if (state == 3) { 
            happiness -= 10; 
                if (isSick == Math.random() < 1.0 / 3.0) {
                    System.out.println("Le Tamagotchi est malade.");
                    System.out.println("Il faut le soigner !");
                    heal();
            }
            if (age >= 20) {
                state = 0; 
                System.out.println("Le Tamagotchi est mort de vieillesse.");
            }
        }

        if (nbEat == 0 && age >= 1) {
            int pointsDeBonheurPerdus = 5; 

            if (firstDayWithoutEating) {
                pointsDeBonheurPerdus = 5; 
                firstDayWithoutEating = false; 
            } else if (secondDayWithoutEating) {
                pointsDeBonheurPerdus = 10; 
                secondDayWithoutEating = false;
            } else if (thirdDayWithoutEating) {
                pointsDeBonheurPerdus = 15;
                thirdDayWithoutEating = false; 
            }

            happiness -= pointsDeBonheurPerdus; 
            System.out.println(
                    "Le Tamagotchi a perdu " + pointsDeBonheurPerdus + " points de bonheur car il n'a pas mangé.");
        } else { 
            if (dirtyEnvironment) {
                happiness -= 3; 
            }
            nbEat = 0;
            firstDayWithoutEating = true;
            secondDayWithoutEating = true;
            thirdDayWithoutEating = true;
        }

        if (happiness <= 0) {
            age = 0;
            happiness = 15;
            state = 0; 
            System.out.println("Le Tamagotchi est mort de tristesse.");
            }
    }

    public void feed() {
        firstDayWithoutEating = false;
        if (nbEat < 1) {
            if (happiness + 5 <= happinessMax) {
                happiness += 5;
            } else {
                happiness = happinessMax; 
            }
            nbEat++; 
            dirtyEnvironment = true; 
            System.out.println("Le Tamagotchi a mangé !");
        } else if (nbEat >= 1) {
            System.out.println("Le Tamagotchi ne veut plus manger pour l'instant.");
            System.out.println("Votre Tamagotchi a déjà mangé, il pourra manger au prochain tour.");
        }
    }
    
    public void play() {
        if (nbPlay < 3) { 
            if (happiness + 3 <= happinessMax) {
                happiness += 3; 
            } else {
                happiness = happinessMax; 
            }
            nbPlay++; 
            System.out.println("Le Tamagotchi est content !");
        } else if (nbPlay >= 3) {
            System.out.println("Le Tamagotchi ne veut plus jouer pour l'instant.");
            System.out.println("Vous avez joué 3 fois, vous pourrez jouer à nouveau au prochain tour.");
        }
    }    

    public void clean() {
        dirtyEnvironment = false;
        System.out.println("L'environnement du Tamagotchi est propre.");
    }

    public void heal() {
        System.out.println("Voulez-vous soigner le Tamagotchi ? (1: oui / 2: non)");
    
        int choix;
    
        while (true) {
            try {
                choix = scanner.nextInt();
                scanner.nextLine(); 
    
                if (choix == 1) {
                    isSick = false; 
                    System.out.println("Le Tamagotchi a été soigné !");
                } else if (choix == 2) {
                    state = 0; 
                    System.out.println("Le Tamagotchi est mort parce que tu l'as pas soigné(e) !");
                } else {
                    System.out.println("Choix invalide !");
                    continue;
                }
    
                break;
            } catch (InputMismatchException e) {
                System.out.println("Choix invalide ! Veuillez entrer un nombre.");
                scanner.nextLine(); 
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
                        tamagotchi.UnitTime();
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
