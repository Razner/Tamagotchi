package com.ynov;

import java.util.Scanner;

public class Tamagotchi {
    private int age; // Âge en unités de temps
    private int bonheur; // Niveau de bonheur
    private int etat; // 0 = oeuf, 1 = bébé, 2 = adulte, 3 = vieillard, 4 = mort
    private int nbJouer = 0; // Nombre de fois que le joueur a joué avec le Tamagotchi durant cette unité de temps
    private int nbEat = 0; // Nombre de fois que le joueur a nourri le Tamagotchi durant cette unité de temps
    private boolean premierJourSansManger;

    public Tamagotchi() {
        age = 0; // Âge initial
        bonheur = 15; // Niveau de bonheur initial
        etat = 0; // Le Tamagotchi commence sous forme d'oeuf
        this.premierJourSansManger = true;
    }

    public void passerUniteDeTemps() {
        age++;
        nbJouer = 0; // On réinitialise le nombre de fois que le joueur a joué avec le Tamagotchi
        if (etat == 0) { // Phase d'oeuf
            if (age >= 1) { // L'oeuf a éclos
                etat = 1; // Passage à l'état de bébé
                System.out.println("Le Tamagotchi est né !");
            }
        } else if (etat == 1) { // Phase de bébé
            bonheur -= 10; // Le bonheur diminue de 10 à chaque unité de temps
            if (age >= 4 && bonheur >= 40) { // Le bébé a été nourri 4 fois et est assez heureux
                etat = 2; // Passage à l'état d'adulte
                System.out.println("Le Tamagotchi est devenu adulte !");
            }
        } else if (etat == 2) { // Phase d'adulte
            bonheur -= 10; // Le bonheur diminue de 10 à chaque unité de temps
            if (age >= 15) { // L'adulte est devenu vieillard
                etat = 3; // Passage à l'état de vieillard
                System.out.println("Le Tamagotchi est devenu vieux !");
            }
        } else if (etat == 3) { // Phase de vieillard
            bonheur -= 10; // Le bonheur diminue de 10 à chaque unité de temps
            if (age >= 20) { // Le vieillard est mort de vieillesse
                etat = 4; // Passage à l'état de mort
                System.out.println("Le Tamagotchi est mort de vieillesse.");
            }
        }

        if (nbEat == 0 && age >= 1) { // Si le Tamagotchi n'a pas été nourri depuis la dernière unité de temps et qu'il n'est plus à l'état d'oeuf
            int pointsDeBonheurPerdus = 5; // On initialise le nombre de points de bonheur perdus à 5
            if (premierJourSansManger) { // Si c'est la première fois que le Tamagotchi ne mange pas
                pointsDeBonheurPerdus = (age - 1) * 5; // On calcule le nombre de points de bonheur perdus en fonction du nombre de jours depuis le dernier repas
                premierJourSansManger = false; // On indique que ce n'est plus la première fois qu'il ne mange pas
            }
            bonheur -= pointsDeBonheurPerdus; // Le bonheur diminue en conséquence
            System.out.println(
                    "Le Tamagotchi a perdu " + pointsDeBonheurPerdus + " points de bonheur car il n'a pas mangé.");
        } else { // Si le Tamagotchi a mangé, on réinitialise le compteur de jours sans manger et le booléen indiquant le premier jour sans manger
            nbEat = 0;
            premierJourSansManger = true;
        }

        if (etat == 4) { // Le Tamagotchi est mort, un nouvel oeuf est créé
            age = 0;
            bonheur = 15;
            etat = 0;
            System.out.println("Un nouvel oeuf a été créé.");
        }
    }

    public void nourrir() {
        premierJourSansManger = false;
        if (etat != 4 && nbEat < 1) {
            bonheur += 5; // On ajoute 5 points de bonheur
            nbEat++; // On incrémente le nombre de fois que le joueur a nourri le Tamagotchi
            System.out.println("Le Tamagotchi a mangé !");
        } else if (nbEat >= 1) {
            System.out.println("Le Tamagotchi ne veut plus manger pour l'instant.");
            System.out.println("Votre Tamagotchi a déjà mangé, il pourra manger au prochain tour.");
        }
    }

    public void jouer() {
        if (etat != 4 && nbJouer < 3) { // On ne peut pas jouer avec un Tamagotchi mort et on ne peut jouer que 3 fois
            bonheur += 3; // On ajoute 3 points de bonheur
            nbJouer++; // On incrémente le nombre de fois que le joueur a joué avec le Tamagotchi
            System.out.println("Le Tamagotchi est content !");
        } else if (nbJouer >= 3) { // Si on a joué 3 fois ce tour-ci
            System.out.println("Le Tamagotchi ne veut plus jouer pour l'instant.");
            System.out.println("Vous avez joué 3 fois, vous pourrez jouez à nouveau au prochain tour.");
        }
    }

    public static void main(String[] args) {
        Tamagotchi tamagotchi = new Tamagotchi();
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuer = true;

            while (continuer) {
                System.out.println("Le Tamagotchi a " + tamagotchi.bonheur + " de bonheur.");
                System.out.println("Que voulez-vous faire ?");
                System.out.println("1 : Passer une unité de temps");
                System.out.println("2 : Nourrir le Tamagotchi");
                System.out.println("3 : Jouer avec le Tamagotchi");
                System.out.println("4 : Quitter");
                int choix = scanner.nextInt();

                switch (choix) {
                    case 1:
                        tamagotchi.passerUniteDeTemps();
                        break;
                    case 2:
                        tamagotchi.nourrir();
                        break;
                    case 3:
                        tamagotchi.jouer();
                        break;
                    case 4:
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
