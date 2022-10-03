package com.company;

import java.awt.*;

public class Background {

    private final int x1, y1, x2, y2; //координаты заднего фона

    private final Color color; //цвет

    public Background(int x1, int y1, int x2, int y2, Color color) { //конструктор

        this.x1 = x1;

        this.y1 = y1;

        this.x2 = x2;

        this.y2 = y2;

        this.color = color;

    }

    public void draw(Graphics2D g) { //метод для отрисовки

        g.setColor(color); //устанавливаем цвет

        g.fillRect(x1, y1, x2, y2); //рисуем прямоугольник, заполненный цветом color

    }
}
