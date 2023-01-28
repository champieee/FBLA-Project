package com.example.testjav;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Sc {

    @FXML
    TextField textField;
    @FXML
    Text txt,  score, winner;
    @FXML
    Button ent, quit, start, dashDisplay, ret, quit1, showWin, rules;
    @FXML
    MenuItem difficultyLow, difficultyMedium, difficultyHigh;



    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> used = new ArrayList<>();
    private String dash;
    private Stage stage;
    private Scene scene;
    private int turn, score1, score2;
    private int display = 0;
    private int display2 = 0;
    public String win;

    private int difficulty = 0; // 0 = easy, 1 = medium, 2 = hard

    // constructor, sets all the necessary variables to their default values and reads the dictionary file
    public Sc() throws IOException {
        score1 = 0;
        score2 = 0;
        turn = 0;
        Scanner s = new Scanner(new java.io.File("words_alpha.txt"));
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();
        dash = getDash();
    }

    // change the screen to the game screen
    public void setMainScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // change the screen to the rules screen
    public void setRuleScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Rules.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // return to the title screen
    public void returnScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc2.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void setLowDifficulty(ActionEvent e) throws IOException {
        FileWriter diff = new FileWriter("Difficulty.txt");
        diff.write("" + 0);
        diff.close();
    }

    public void setMediumDifficulty(ActionEvent e) throws IOException {
        FileWriter diff = new FileWriter("Difficulty.txt");
        diff.write("" + 2);
        diff.close();
    }

    public void setHighDifficulty(ActionEvent e) throws IOException {
        FileWriter diff = new FileWriter("Difficulty.txt");
        diff.write("" + 4);
        diff.close();
    }

    // decide how many points to give to a player every time they enter a word
    public void enter(ActionEvent e) {
        System.out.println("Clicked");
        String input = textField.getText();
        System.out.println("input: " + input);
        boolean check = checkWord(dash, input);
        System.out.println(check);
        if (check) {
            if (turn % 2 == 0) {
                score1 += input.length() * 5;
                score.setText("Score: " + score1);
                textField.clear();
            } else {
                score2 += input.length() * 5;
                score.setText("Score: " + score2);
                textField.clear();
            }
        }

        // updates the win to whoever has the most points
        if (score1 > score2) {
            win = "1";
        } else if (score2 > score1) {
            win ="2";
        } else {
            win = "Draw";
        }
    }

    // display the dash to the user
    public void showDash(ActionEvent e) {
        System.out.println(win);
        if (display % 2 == 0) {
            txt.setText(dash);
            dashDisplay.setText("Hide Dash");
            display++;
        } else {
            txt.setText("Dash Hid");
            dashDisplay.setText("Show Dash");
            display++;
        }
    }

    // let the user end their turn if they can't think of another word
    public void endTurn(ActionEvent e) throws IOException {
        turn++;
        if (turn < 2) {
            // if only the first player has pressed end turn then the second player's turn starts
            textField.clear();
            dash = getDash();
            txt.setText("Reset Dash");
            dashDisplay.setText("Reset Dash");
            score.setText("Score: " + 0);
        } else {
            /*
             since both players have now decided to end their turn, this writes the winner to the Result file
             and changes the screen to the result screen
            */

            FileWriter myWriter = new FileWriter("Result.txt");
            myWriter.write(win);
            myWriter.close();

            // change the screen to the result screen
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc3.fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene  = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    String result = "";
    // display the winner to the user
    public void setWinner (ActionEvent e) throws FileNotFoundException {
        // read which result was written to the Result file
        Scanner x = new Scanner(new java.io.File("Result.txt"));
        result = x.next();
        x.close();

        // display or hide the winner depending on how many times the button has been pressed
        if (display2 % 2 == 0) {
            if (result.equals("1") || result.equals("2")) {
                winner.setText("Player " + result);
            } else {
                winner.setText(result);
            }
            showWin.setText("Hide Winner");
            display2++;
        } else {
            winner.setText("Winner Hid");
            showWin.setText("Show Winner");
            display2++;
        }
    }

    // reset the game if the user wishes to do so
    public void restart(ActionEvent e) throws IOException {
        // resets all the variables in order for the user to play again
        score1 = 0;
        score2 = 0;
        turn = 0;

        // resets the value of the result file so the same person doesn't keep winning again
        FileWriter retExit = new FileWriter("Result.txt");
        retExit.write("");

        // loads the title screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc2.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // exit the game
    public void quit(ActionEvent e) throws IOException {
        // write a blank string to the Result file so that the result file doesn't display a winner
        FileWriter exit = new FileWriter("Result.txt");
        exit.write("");

        FileWriter exit1 = new FileWriter("Difficulty.txt");
        exit1.write("");

        // exits the game
        System.exit(0);
    }

    // this removes duplicates from the list of letters so the program can check if the word is valid
    public static void removeDuplicates(char[] str) {
        if (str == null) {
            return;
        }
        int len = str.length;
        if (len < 2) {
            return;
        }
        int tail = 1;
        for (int i = 1; i < len; ++i) {
            int j;
            for (j = 0; j < tail; ++j) {
                if (str[i] == str[j]) break;
            }
            if (j == tail) {
                str[tail] = str[i];
                if (i != tail) str[i] = 0;
                ++tail;
            } else {
                str[i] = 0;
            }
        }
    }

    // returns a dash with the correct number of letters
    public String getDash() throws FileNotFoundException {
        // makes a character array with the same number of each letter as Scrabble does
        char[] alpha = {'e','e','e','e','e','e','e','e','e','e','e','e',
                'a','a','a','a','a','a','a','a','a',
                'i','i','i','i','i','i','i','i','i',
                'o','o','o','o','o','o','o','o',
                'n','n','n','n','n','n',
                'r','r','r','r','r','r',
                't','t','t','t','t','t',
                'l','l','l','l',
                's','s','s','s',
                'u','u','u','u',
                'd','d','d','d',
                'g','g','g',
                'b','b',
                'c','c',
                'm','m',
                'p','p',
                'f','f',
                'h','h',
                'v','v',
                'w','w',
                'y','y',
                'k',
                'j',
                'x',
                'q',
                'z'};
        int index;
        ArrayList<Integer> indexes = new ArrayList<>();
        String dash = "";
        char letter;

        Scanner difficultyReader = new Scanner(new java.io.File("Difficulty.txt"));
        difficulty = Integer.parseInt(difficultyReader.next());

        // randomly selects 14 different letters from the character array
        for (int i = 0; i < 14 - difficulty; i++) {
            index = (int) (Math.random() * alpha.length);
            if (!indexes.contains(index)) {
                indexes.add(index);
                letter = alpha[index];
                dash += letter + " ";
            } else {
                do {
                    index = (int) (Math.random() * alpha.length);
                } while (indexes.contains(index));
                indexes.add(index);
                letter = alpha[index];
                dash += letter + " ";
            }

        }

        return dash;
    }

    // checks the word and returns a boolean value depending on whether the word is valid or not
    public boolean checkWord(String dh, String in) {
        char[] letterList = dh.toCharArray();
        int count = 0;
        int usedCount = 0;
        boolean check = false;
        boolean wordCheck = false;
        removeDuplicates(letterList);

        // checks if the word contains only letters found in your dash
        for (int i = 0; i < in.length(); i++) {
            for (int j = 0; j < letterList.length; j++) {
                if (in.charAt(i) == letterList[j]) {
                    count++;
                }
            }
        }

        // checks if the word is found in the dictionary file
        if (count == in.length()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(in) && !(in.equals(""))) {
                    wordCheck = true;
                }
            }
        } else {
            wordCheck = false;
        }

        // checks if the word has already been used and won't give the point if it has
        if (wordCheck) {
            for (int i = 0; i < used.size(); i++) {
                if (used.get(i).equals(in)) {
                    usedCount++;
                }
            }
            if (usedCount == 0) {
                used.add(in);
                check = true;
            }
        }
        return check;
    }
}