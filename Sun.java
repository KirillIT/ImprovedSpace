package com.company;

import java.awt.*;

import java.util.Timer;

import java.util.TimerTask;

public class Sun {

    private final int r, l, n, x, y; //радиус, длина лучей, их кол-во, координата X, Y

    private double initialAngleForRays; //начальный угол для лучей

    private final int FPS = 60; //частота кадров (для корректной работы движения)

    private Color color;

    public Sun(int r, int l, int n, int x, int y) { //конструктор

        this.r = r;

        this.l = l;

        this.n = n;

        this.initialAngleForRays = 0;

        this.x = x;

        this.y = y;

        color = new Color(255, 255, 0);

        rotateBeams();

        changeColor();

    }

    public int getL() { //геттеры

        return l;

    }

    public int getR() {

        return r;

    }

    public void changeColor() { //метод для изменения цвета солнца

        final int[] colorComponent = {255};

        final boolean[] flag = {true};

        TimerTask task = new TimerTask() {

            public void run() {

                if (flag[0]) {

                    colorComponent[0]--;

                    color = new Color(255, colorComponent[0], 0);

                    if (colorComponent[0] == 145){

                        flag[0] = false;

                    }
                }

                else {

                    colorComponent[0]++;

                    color = new Color(255, colorComponent[0], 0);

                    if (colorComponent[0] == 255){

                        flag[0] = true;

                    }
                }
            }
        };

        Timer timer = new Timer("Timer");

        long delay = 1000 / FPS;

        timer.scheduleAtFixedRate(task, 0, delay);

    }

    public void rotateBeams() { //метод для вращения лучей

        TimerTask task = new TimerTask() {

            public void run() {

                initialAngleForRays = initialAngleForRays + 0.001;

            }
        };

        Timer timer = new Timer("Timer");

        long delay = 1000 / FPS;

        timer.scheduleAtFixedRate(task, 0, delay);

    }

    public void draw (Graphics2D g) { //метод для отрисовки

        g.setColor(color);

        g.fillOval(x - r, y - r, 2 * r, 2 * r);

        double angleBetweenBeams = 2 * Math.PI / n; //угол между лучами

        double x1, y1, x2, y2, angleOfParticularBeam; //координаты луча и его улог

        g.setStroke(new BasicStroke(2));

        for (int i = 0; i < n; i++) {

            angleOfParticularBeam = i * angleBetweenBeams + initialAngleForRays;

            x1 = r * Math.cos(angleOfParticularBeam) + x;

            y1 = r * Math.sin(angleOfParticularBeam) + y;

            x2 = (r+ l) * Math.cos(angleOfParticularBeam) + x;

            y2 = (r + l) * Math.sin(angleOfParticularBeam) + y;

            g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);

        }
    }
}