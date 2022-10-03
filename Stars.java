package com.company;

import java.awt.*;

import java.util.ArrayList;

import java.util.Timer;

import java.util.TimerTask;

public class Stars {

    private final int maxScreenX, maxScreenY; //размер монитора по X и Y

    private final int n; //кол-во звёзд

    private final ArrayList<Star> stars; //лист для них

    public Stars (int n, int maxScreenX, int maxScreenY) { //конструктор

        this.maxScreenX = maxScreenX;

        this.maxScreenY = maxScreenY;

        this.n = n;

        stars = new ArrayList<>(n);

        fill();

        startFlickering();

    }

    public void startFlickering() { //метод запускает мерцание одной звезды (рандомной) раз в n (сами задаём время) секунд

        TimerTask setCords = new TimerTask() {

            public void run() {

                int random = (int)(Math.random() * n);

                stars.get(random).setFlicker(true);

            }
        };

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(setCords,0, 250);

    }

    private void fill(){ //метод заполняет наш массив объектами типа Star (одна звезда)

        for (int i = 0; i < n; i++) {

            stars.add(new Star((int) (Math.random() * (maxScreenX + 1)), (int) (Math.random() * (maxScreenY + 1)), (int) (Math.random() * 4) + 2));

        }
    }

    public void draw(Graphics2D g) { //метод для отрисовки

        for (Star star : stars) {

            star.draw(g);

        }
    }

    private static class Star { //внутренний класс (одна звезда)

        private final int x, y, r; //координаты и радиус

        private final Color color; //цвет

        private boolean isFlicker = false; //мерцать звезде или нет прямо сейчас?

        public Star(int x, int y, int r) { //конструктор

            this.x = x;

            this.y = y;

            this.r = r;

            this.color = Color.WHITE;

        }

        public void setFlicker(boolean flicker) {

            isFlicker = flicker;

        }

        public void draw(Graphics2D g) {

            g.setColor(color);

            g.fillOval(x, y, r, r);

            if (isFlicker) {

                final int nPoints = 4;

                int[] xPoints = new int[nPoints];

                int[] yPoints = new int[nPoints];

                xPoints[0] = x;

                xPoints[1] = x + r * 2;

                xPoints[2] = x;

                xPoints[3] = x - r * 2;

                yPoints[0] = y - r * 2;

                yPoints[1] = y;

                yPoints[2] = y + r * 2;

                yPoints[3] = y;

                g.fillPolygon(xPoints, yPoints, nPoints);

                isFlicker = false;

            }
        }
    }
}