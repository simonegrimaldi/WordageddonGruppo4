/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.UUID;

/**
 *
 * @author simonegrimaldi
 */
public class Game {

    private UUID id;
    private int points;
    private String username;
    private String difficulty;

    public Game(String difficulty,String username) {
        this.difficulty = difficulty;
        this.username = username;
        id = UUID.randomUUID();
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public UUID getId() {
        return id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getUsername() {
        return username;
    }
    
}
