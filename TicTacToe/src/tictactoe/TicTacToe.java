/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Projet Programmation: TP#1 (TicTacToe)
 *
 * @author Oussama Lourhmati 1739188
 */
public class TicTacToe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner r = new Scanner(System.in);
        String reponse = "";
        bienvenue();
        do {
            jouer();
            System.out.print("Voulez-vous rejouer? : ");
            reponse = r.nextLine();
            System.out.println("");
        } while (reponse.equals("oui") || reponse.equals("Oui"));

    }

    /**
     * Methode bienvenue():Accueil de l'utilisateur, je lu souhaite bienvenue
     */
    private static void bienvenue() {
        String nom = "";
        Scanner r = new Scanner(System.in);
        System.out.println("*****************");
        System.out.println("***TIC*TAC*TOE***");
        System.out.println("*****************");

        System.out.print("Quel est votre nom ? ");
        nom = r.nextLine();

        System.out.println("Bienvenue, " + nom + "! Etes vous pret a jouer ?");
    }

    /**
     * Methode jouer(): Je lui demande les dimensions qu'il veut,je fait la
     * creation du tableau et je fait le tirage random. Je fait appel a tout les
     * methodes, selon le resultat du tirage.
     */
    private static void jouer() {
        Scanner r = new Scanner(System.in);
        Random rd = new Random();
        String resultat = "";
        int n = 0, choix = 0;
        int compteur = 0;
        boolean w = true;

        while (w == true) {
            try {
                w = false;
                System.out.print("Donnez votre dimension du tableau : ");
                n = r.nextInt();
                while (n <= 2) {
                    System.out.println("Donnez une dimension plus grande que 2");
                    System.out.print("Donnez votre dimension du tableau : ");
                    n = r.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrez un entier");
                r.nextLine();
                w = true;
            }
        }

        //Creation du tableau
        String[][] tab = creerTableau(n);

        //Tirage du jeu
        choix = rd.nextInt(2) + 1;
        if (choix == 1) {
            System.out.println("Résultat du tirage: Vous commencez la partie!");
            afficherTableau(tab);
        } else {
            System.out.println("Résultat du tirage: l'IA est premier joueur.");
            afficherTableau(tab);
        }

        while (choix == 1) {

            obtenirCoordUtilisateur(tab);
            verifierMouvement(tab);
            deplacer(tab);
            resultat = verifierGagnant(tab);

            if (resultat.equals("x") || resultat.equals("o") || resultat.equals(" ")) {
                break;
            }

        }

        while (choix == 2) {

            verifierMouvement(tab);
            deplacer(tab);
            obtenirCoordUtilisateur(tab);
            resultat = verifierGagnant(tab);

            if (resultat.equals("x") || resultat.equals("o") || resultat.equals(" ")) {
                break;
            }

        }

        //Designation du vainqueur
        if (resultat.equals("x")) {
            System.out.println("Bravo! Vous avez gagne la partie!");
        }
        if (resultat.equals("o")) {
            System.out.println("GAME OVER! Vous avez perdu, l'IA a gagne!");
        }
        if (resultat.equals(" ")) {
            System.out.println("Aucun vainqueur! Match nul!");
        }

    }

    /**
     * Methode afficherTableau(): Je fait l'affichage du tableau, en utilisant 2
     * boucles for, une pour les barres horizontales (+-), et l'autre pour
     * vertical.
     * @param tab
     */
    private static void afficherTableau(String[][] tab) {

        String a = "", b = "+";
        for (int i = 0; i < tab.length; i++) {
            b += "-+";
        }
        System.out.print(b);
        for (int i = 0; i < tab.length; i++) {
            System.out.print("\n" + "|");
            for (int j = 0; j < tab.length; j++) {
                System.out.printf("%1s", tab[i][j]);
                System.out.print("|");
            }
            System.out.println("");
            System.out.print(b);
        }
        System.out.println("");
    }

    /**
     * Methode obtenirCoordUtilisateur: Je vérifie les coordonnées de
     * l'utilisateur, Je m'assure qu'il donne des entiers et qu'il ne dépasse
     * pas les capacités du tableau (try,catch),et qu'il ne joue pas avec une
     * cellule occupée (while)
     * @param tab
     */
    private static void obtenirCoordUtilisateur(String[][] tab) {
        Scanner r = new Scanner(System.in);
        boolean w = true;

        String caractere = "x";
        int x = 0, y = 0;

        while (w == true) {

            try {
                w = false;
                System.out.print("Donnez vos coordonnees : ");
                x = r.nextInt();
                y = r.nextInt();
                while (tab[x][y].equals("x") || tab[x][y].equals("o")) {
                    System.out.println("Cellule occupée");
                    System.out.print("Donnez vos coordonnees : ");
                    x = r.nextInt();
                    y = r.nextInt();
                }

            } catch (InputMismatchException e) {
                System.out.println("Entrez des entiers");
                r.nextLine();
                w = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Cellule inexistante");
                r.nextLine();
                w = true;
            }

        }
        remplirTableau(tab, caractere, x, y);
    }

    /**
     * Methode remplirTableau(): Je fait une autre verification pour si la
     * cellule est occupée (while) ou si la cellule est inexistante (try,catch).
     * Par la suite je rempli la case selectionné.
     * @param tab
     * @param caractere
     * @param x
     * @param y
     * @return tab
     */
    private static String[][] remplirTableau(String[][] tab, String caractere, int x, int y) {
        Scanner r = new Scanner(System.in);
        boolean w = true;

        while (w == true) {
            try {
                w = false;
                while (tab[x][y].equals("x") || tab[x][y].equals("o")) {
                    System.out.println("Cellule occupée");
                    System.out.print("Donnez vos coordonnees : ");
                    x = r.nextInt();
                    y = r.nextInt();
                }
                tab[x][y] = "x";

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Cellule inexistante");
                r.nextLine();
                w = true;
            }
        }

        afficherTableau(tab);
        return tab;
    }

    /**
     * Methode deplacer():Verifie les mouvements, par la suite, l'ia joue j'ai
     * utilisé des boucles for pour cette methode.
     * @param tab
     */
    private static void deplacer(String[][] tab) {
        Random rd = new Random();
        int compteur = 0, i = 0, j = 0;
        boolean find = false, b = false;
        boolean mouvement;
        mouvement = verifierMouvement(tab);

        if (mouvement) {

            for (i = 0; i < tab.length && !find; i++) {
                for (j = 0; j < tab[i].length; j++) {
                    if (tab[i][j].equals("o")) {
                        compteur++;
                    }
                }

                if (compteur == tab.length - 1) {
                    for (j = 0; j < tab.length; j++) {
                        if (tab[i][j].isEmpty()) {
                            tab[i][j] = "o";
                        }
                    }
                    find = true;
                }
                compteur = 0;
            }

            compteur = 0;

            for (i = 0; i < tab.length && !find; i++) {
                for (j = 0; j < tab[i].length; j++) {
                    if (tab[j][i].equals("o")) {
                        compteur++;
                    }
                }

                if (compteur == tab.length - 1) {

                    for (j = 0; j < tab.length; j++) {
                        if (tab[j][i].isEmpty()) {
                            tab[j][i] = "o";
                        }
                    }

                    find = true;
                }
                compteur = 0;
            }

            compteur = 0;

            for (i = 0; i < tab.length && !find; i++) {
                if (tab[i][i].equals("o")) {
                    compteur++;
                }

                if (compteur == tab.length - 1) {
                    for (i = 0; i < tab.length; i++) {
                        if (tab[i][i].isEmpty()) {
                            tab[i][i] = "o";
                        }

                    }
                    find = true;
                }
            }

            compteur = 0;
            int d = tab.length - 1;//d pour diagonale inverse

            for (i = 0; i < tab.length && !find; i++) {
                if (tab[i][d].equals("o")) {
                    compteur++;
                }
                d--;

                if (compteur == tab.length - 1) {
                    d = tab.length - 1;
                    for (i = 0; i < tab.length; i++) {
                        if (tab[i][d].isEmpty()) {
                            tab[i][i] = "o";
                        }
                        d--;
                    }
                    find = true;
                }
            }

            compteur = 0;

            //Pour les x
            for (i = 0; i < tab.length && !find; i++) {
                for (j = 0; j < tab[i].length; j++) {
                    if (tab[i][j].equals("x")) {
                        compteur++;
                    }
                }

                if (compteur == tab.length - 1) {

                    for (j = 0; j < tab.length; j++) {
                        if (tab[i][j].isEmpty()) {
                            tab[i][j] = "o";
                        }
                    }

                    find = true;
                }
                compteur = 0;
            }

            compteur = 0;

            for (i = 0; i < tab.length && !find; i++) {
                for (j = 0; j < tab[i].length; j++) {
                    if (tab[j][i].equals("x")) {
                        compteur++;
                    }
                }

                if (compteur == tab.length - 1) {
                    for (j = 0; j < tab.length; j++) {
                        if (tab[j][i].isEmpty()) {
                            tab[j][i] = "o";
                        }
                    }
                    find = true;
                }
                compteur = 0;
            }

            compteur = 0;

            for (i = 0; i < tab.length && !find; i++) {

                if (tab[i][i].equals("x")) {
                    compteur++;
                }

                if (compteur == tab.length - 1) {
                    for (i = 0; i < tab.length; i++) {
                        if (tab[i][i].isEmpty()) {
                            tab[i][i] = "o";
                        }
                    }
                    find = true;
                }
            }

            compteur = 0;
            d = tab.length - 1;

            for (i = 0; i < tab.length && !find; i++) {
                if (tab[i][d].equals("x")) {
                    compteur++;
                }
                d--;

                if (compteur == tab.length - 1) {
                    d = tab.length - 1;
                    for (i = 0; i < tab.length; i++) {
                        if (tab[i][d].isEmpty()) {
                            tab[i][i] = "o";
                        }
                        d--;
                    }
                    find = true;
                }
            }

            compteur = 0;

        } else {
            do {
                int x = rd.nextInt(tab.length);
                int y = rd.nextInt(tab.length);

                if (tab[x][y].isEmpty()) {
                    tab[x][y] = "o";
                    b = true;
                }
            } while (!b);

        }

        System.out.println("L'IA a choisi sa cellule:");
        afficherTableau(tab);
    }

    /**
     * Methode verifierMouvement(): Je verifie le mouvement que l'utilisateur a
     * fait avec l'aide des boucles for (horizontal,vertical,diagonale,diagonale
     * inverse).
     * @param tab
     * @return mouvement
     */
    private static boolean verifierMouvement(String[][] tab) {
        int c = 0;//compteur
        boolean mouvement = false;

        //Verification horizontal "o"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j].equals("o")) {
                    c++;
                }
                
            }
            if (c == tab.length - 1) {
                mouvement = true;
            }
        }
        c = 0;
        //Verification vertical "o"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[j][i].equals("o")) {
                    c++;
                }
            }
            if (c == tab.length - 1) {
                mouvement = true;
            }
        }
        c = 0;
        //Verification diagonal "o"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][i].equals("o")) {
                    c++;
                }
            }
            if (c == tab.length - 1) {
                mouvement = true;
            }
        }
        c = 0;
        //Verification diagonale inverse "o"
        int d = tab.length - 1;//variable d, pour diagonale inverse
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][d].equals("o")) {
                c++;
            }
            d--;
        }
        if (c == tab.length - 1) {
            mouvement = true;
        }

        //Verification horizontal "x"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j].equals("x")) {
                    c++;
                }
            }
            if (c == tab.length - 1) {
                mouvement = true;
            }
        }
        c = 0;
        //Verification vertical "x"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[j][i].equals("x")) {
                    c++;
                }
            }
            if (c == tab.length - 1) {
                mouvement = true;
            }
        }
        c = 0;
        //Verification diagonal "x"
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][i].equals("x")) {
                    c++;
                }
            }
            if (c == tab.length - 1) {
                mouvement = true;
            }
        }
        c = 0;
        //Verification diagonale inverse "x"
        d = tab.length - 1;//variable d, pour diagonale inverse
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][d].equals("x")) {
                c++;
            }
            d--;
        }
        if (c == tab.length - 1) {
            mouvement = true;
        }

        return mouvement;
    }

    /**
     * Methode verifierGagnant(): Je verifie si il y a un gagnant avec des
     * boucles, je verifie verticalement, horizontalement, en diagonale et
     * diagonale inverse, pour les "x" et les "o", sinon c'est egalite (" ").
     * @param tab
     * @return resultat
     */
    private static String verifierGagnant(String[][] tab) {
        String resultat = "";
        int compteurO = 0, compteurX = 0, compteur = 0;

        //Verification horizontale
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j].equals("o")) {
                    compteurO++;
                }
                if (tab[i][j].equals("x")) {
                    compteurX++;
                }
            }

            compteurX = 0;
            compteurO = 0;

        }
        if (compteurO == tab.length) {
            resultat = "o";
        }
        if (compteurX == tab.length) {
            resultat = "x";
        }
        compteurX = 0;
        compteurO = 0;

        //Verification verticale 
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[j][i].equals("o")) {
                    compteurO++;
                }

                if (tab[j][i].equals("x")) {
                    compteurX++;
                }
            }
            compteurX = 0;
            compteurO = 0;

        }
        if (compteurO == tab.length) {
            resultat = "o";
        }
        if (compteurX == tab.length) {
            resultat = "x";
        }
        compteurX = 0;
        compteurO = 0;

        //Verification diagonale 
        for (int i = 0; i < tab.length; i++) {

            if (tab[i][i].equals("o")) {
                compteurO++;
            }
            if (tab[i][i].equals("x")) {
                compteurX++;
            }

        }
        if (compteurO == tab.length) {
            resultat = "o";
        }
        if (compteurX == tab.length) {
            resultat = "x";
        }
        compteurX = 0;
        compteurO = 0;

        //Verification diagonale inverse 
        int d = tab.length - 1;//variable d, pour diagonale inverse
        for (int i = 0; i < tab.length; i++) {
            if (tab[i][d].equals("o")) {
                compteurO++;
            }
            if (tab[i][d].equals("x")) {
                compteurX++;
            }
            d--;

        }
        if (compteurO == tab.length) {
            resultat = "o";
        }
        if (compteurX == tab.length) {
            resultat = "x";
        }
        compteurX = 0;
        compteurO = 0;

        //Verification match nul
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (!(tab[i][j].isEmpty())) {
                    compteur++;
                }
            }
        }
        if (compteur == (tab.length * tab.length)) {
            resultat = " ";
        }

        compteur = 0;

        return resultat;
    }

    /**
     * Methode creerTableau(): Je cree le tableau String avec la dimension "n".
     * @param n
     * @return tab
     */
    private static String[][] creerTableau(int n) {
        //Création du tableau
        String[][] tab = new String[n][n];

//J'initalise le tableau avec for, pour ne pas avoir des "null" dans le tableau
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = "";
            }
        }

        return tab;
    }

}
