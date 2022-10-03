package com.company;

import java.awt.*;

import java.util.Timer;

import java.util.TimerTask;

public class UFO {

    private final int minWidth, maxWidth, bigR, maxScreenX, maxScreenY; //минимальная ширина, максимальная, радиус большой орбиты (за пределами видимого экрана), ширина монитора, высота

    private int x, y, height, width, smallR; //координаты кометы, её высоты, ширина и малый радиус (тот, который рядом с Солнцем)

    private final double minStartTime, maxStartTime; //минимальное время полёта от большой орбиты к солнцу, максимальное

    public UFO(int minWidth, int maxWidth, int d1, int d2, int maxScreenX, int maxScreenY, double minStartTime, double maxStartTime) { //конструктор

        this.minStartTime = minStartTime;

        this.maxStartTime = maxStartTime;

        this.minWidth = minWidth;

        this.maxWidth = maxWidth;

        this.bigR = d1;

        this.smallR = d2;

        this.maxScreenX = maxScreenX;

        this.maxScreenY = maxScreenY;

        restart();

    }

    public void draw(Graphics2D g) { //метод для отрисовки

        g.setColor(new Color(118, 125, 141));

        g.fillArc(x, y, width, height, 180, 180);

        g.setColor(new Color(208, 212, 214));

        g.fillRect(x, y + height / 2 - (int)(height * 0.1), width, (int)(height * 0.1));

        g.setColor(new Color(174, 177, 185));

        g.fillArc(x , y + (int)(height * 0.5 / 2) - (int)(height * 0.1), width , (int)(height * 0.5), 0, 180);

        g.setColor(new Color(118, 186, 212));

        g.fillArc((int)(x + width / 2 * Math.cos(70) * 1.04), y - (int)(height * 0.1 / 4) - 2 - (int)(height * 0.5/4), (int)(width / 2 * Math.cos(70) * 1.085), (int)(width / 2 * Math.cos(70)), -5, 190);

        g.fillArc((int)(x + width / 2 * Math.cos(70) * 1.04), y - (int)(height * 0.1 / 4) - (int)(height * 0.5/4), (int)(width / 2 * Math.cos(70) * 1.085), (int)(width / 2 * Math.cos(70)), -5, 190);

        g.setColor(Color.WHITE);

        g.drawArc(x, y, width, height, 180, 180);

        g.drawRect(x, y + height / 2 - (int)(height * 0.1), width, (int)(height * 0.1));

        g.drawArc(x , y + (int)(height * 0.5 / 2) - (int)(height * 0.1), width , (int)(height * 0.5), 0, 70);

        g.drawArc(x , y + (int)(height * 0.5 / 2) - (int)(height * 0.1), width , (int)(height * 0.5), 110, 70);

        g.drawArc((int)(x + width / 2 * Math.cos(70) * 1.04), y - (int)(height * 0.1 / 4) - 2 - (int)(height * 0.5/4), (int)(width / 2 * Math.cos(70) * 1.085), (int)(width / 2 * Math.cos(70)), 0, 180);

        g.setColor(Color.BLACK);

        g.setFont(new Font("Georgia", Font.PLAIN,width / 4));

        g.drawString("UFO",x + width / 4, (int)(y + height / 1.08));

    }

    public void restart() { //метод запускает тарелку заново по траектории раз в n (рандомное число в заданном диапазоне) секунд

        TimerTask setCords = new TimerTask() {

            public void run() {

                move();

            }
        };

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(setCords,(int)((Math.random() * ((maxStartTime*maxStartTime - minStartTime) + 1)) + minStartTime)* 1000, (int)(maxStartTime * 1000 * 4));

    }

    public void move() { //метод обновления координат для того, чтобы тарелка двигалась по заданной траектории

        int startX, startY, finalX, finalY;

        final int FPS = 60; //частота кадров (для корректной работы движения)

        final int startTime = (int)((Math.random() * ((maxStartTime - minStartTime) + 1)) + minStartTime); //время полёта от большой орбиты к Солнцу

        final double[] step = {startTime};

        width = (int)((Math.random() * ((maxWidth - minWidth) + 1)) + minWidth);

        height = width / 2;

        smallR += width * 2;

        final int[] greatOrbitAngle = new int[1];

        greatOrbitAngle[0] = (int)(Math.random() * (360 + 1));

        x = (int)(maxScreenX / 2 - width / 2  + (Math.cos(Math.toRadians(greatOrbitAngle[0])) * bigR));

        y = (int)(maxScreenY / 2 - height / 2 - (Math.sin(Math.toRadians(greatOrbitAngle[0])) * bigR));

        startX = x;

        startY = y;

        finalX = (int)(maxScreenX / 2 - width / 2  + (Math.cos(Math.toRadians(90 + greatOrbitAngle[0])) * smallR));

        finalY = (int)(maxScreenY / 2 - height / 2  - (Math.sin(Math.toRadians(90 + greatOrbitAngle[0])) * smallR));

        enum motionStates{

            FORWARD,

            INCIRCLE,

            BACKWARD

        }

        final motionStates[] ms = {motionStates.FORWARD};

        Timer timer = new Timer();

        TimerTask changeCords = new TimerTask() {

            public void run() {

                int xStep, yStep;

                if (ms[0] == motionStates.FORWARD) {

                    xStep = (int)Math.round(((double)finalX - x) / FPS / step[0]);

                    yStep = (int)Math.round(((double)finalY - y) / FPS / step[0]);

                    x = x + xStep;

                    y = y + yStep;

                    step[0] -= 1.0 / FPS;

                    if (step[0] < 0) {

                        ms[0] = motionStates.INCIRCLE;

                        step[0] = 0;

                    }
                }

                else if (ms[0] == motionStates.INCIRCLE) {

                    x = (int)(maxScreenX / 2 - width / 2  + (Math.cos(Math.toRadians(90 + greatOrbitAngle[0] + step[0])) * smallR));

                    y = (int)(maxScreenY / 2 - height / 2  - (Math.sin(Math.toRadians(90 + greatOrbitAngle[0] + step[0])) * smallR));

                    double moveTimeInCircle = ((Math.PI * (smallR)) * startTime)/(Math.sqrt((Math.pow(bigR, 2) + (Math.pow(smallR, 2)))));

                    step[0] += ((smallR) * Math.PI) / ((double)(FPS)) / (moveTimeInCircle) / (smallR / 50.0);

                    if (step[0] > 180) {

                        ms[0] = motionStates.BACKWARD;

                        step[0] = startTime;

                    }
                }

                else {

                    xStep = (int)Math.round(((double)startX - x) / FPS / step[0]);

                    yStep =  (int)Math.round(((double)startY - y) / FPS / step[0]);

                    x = x + xStep;

                    y = y + yStep;

                    step[0] -= 1.0 / FPS;

                    if (step[0] < 0) {

                        smallR -= width * 2;

                        timer.cancel();

                    }
                }
            }
        };

        long delay = 1000 / FPS;

        timer.scheduleAtFixedRate(changeCords, 0, delay);

    }
}