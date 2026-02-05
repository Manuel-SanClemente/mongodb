package com.example;

public class Main {
    public static void main(String[] args) {
        RecordRepo records = RecordRepo.getInstance();

        // Engadir records de xogadores
        
        records.addRecord("Mario", "Space Invaders", 1200, 15, 3);
        records.addRecord("Luigi", "Galaga", 1268, 12, 4);
        records.addRecord("Arturo", "Space Invaders", 2048, 30, 5);
        records.addRecord("Mario", "Donkey Kong", 2156, 27, 4);
        records.addRecord("Anxo", "Donkey Kong", 510, 7, 2);
        records.addRecord("Luigi", "Tetris", 3946, 45, 8);
        

        records.getTotalRecord();
    }
}