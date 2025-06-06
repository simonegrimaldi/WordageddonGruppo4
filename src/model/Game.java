/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.EnumDifficulty;
import java.util.UUID;

/**
 *
 * @author simonegrimaldi
 */
public class Game {

    private UUID id;
    private EnumDifficulty difficulty;
    private int points;
    private String username;

    public Game(EnumDifficulty difficulty,String username) {
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

    public EnumDifficulty getDifficulty() {
        return difficulty;
    }

    public String getUsername() {
        return username;
    }
    
}
