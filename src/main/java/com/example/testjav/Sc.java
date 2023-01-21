package com.example.testjav;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    Text txt;
    @FXML
    Text score;
    @FXML
    Text winner;
    @FXML
    Button ent;
    @FXML
    Button quit;
    @FXML
    Button start;
    @FXML
    Button dashDisplay;
    @FXML
    Button ret;
    @FXML
    Button quit1;
    @FXML
    Button showWin;



    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> used = new ArrayList<>();
    private String dash;
    private Stage stage;
    private Scene scene;
    private int turn;
    private int display = 0;
    private int display2 = 0;

    public int score1;
    public int score2;

    public String win;

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

    public void setMainScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void enter(ActionEvent e) {
        System.out.println("Clicked");
        String input = textField.getText();
        System.out.println("input: " + input);
        boolean check = checkWord(dash, input);
        System.out.println(check);
        if (check) {
            if (turn % 2 == 0) {
                score1++;
                score.setText("Score: " + score1);
                textField.clear();
            } else {
                score2++;
                score.setText("Score: " + score2);
                textField.clear();
            }
        }

        if (score1 > score2) {
            win = "1";
        } else if (score2 > score1) {
            win ="2";
        } else {
            win = "Draw";
        }
    }

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

    public void endTurn(ActionEvent e) throws IOException {
        turn++;
        if (turn < 2) {
            textField.clear();
            dash = getDash();
            txt.setText("Reset Dash");
            dashDisplay.setText("Reset Dash");
            score.setText("Score: " + 0);
        } else {
            FileWriter myWriter = new FileWriter("Result.txt");
            myWriter.write(win);
            myWriter.close();

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc3.fxml")));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene  = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    String result = "";
    public void setWinner (ActionEvent e) throws FileNotFoundException {
        Scanner x = new Scanner(new java.io.File("Result.txt"));
        result = x.next();
        x.close();

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

    public void restart(ActionEvent e) throws IOException {
        score1 = 0;
        score2 = 0;
        turn = 0;
        dash = getDash();

        FileWriter retExit = new FileWriter("Result.txt");
        retExit.write("");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Sc2.fxml")));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene  = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void quit(ActionEvent e) throws IOException {
        FileWriter exit = new FileWriter("Result.txt");
        exit.write("");
        System.exit(0);
    }

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

    public String getDash() {
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
        for (int i = 0; i < 14; i++) {
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

    public boolean checkWord(String dh, String in) {
        char[] letterList = dh.toCharArray();
        int count = 0;
        int usedCount = 0;
        boolean check = false;
        boolean wordCheck = false;
        removeDuplicates(letterList);

        for (int i = 0; i < in.length(); i++) {
            for (int j = 0; j < letterList.length; j++) {
                if (in.charAt(i) == letterList[j]) {
                    count++;
                }
            }
        }

        if (count == in.length()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(in) && !(in.equals(""))) {
                    wordCheck = true;
                }
            }
        } else {
            wordCheck = false;
        }

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