package com.company;

import javax.swing.*;

import java.awt.*;

import java.util.ArrayList;

public class DrawPanel extends JPanel {

    private final Background bg;

    private final Sun sun;

    private final Stars stars;

    private final ArrayList<UFO> ufo;

    private final ArrayList<Comet> comet;

    public DrawPanel() { //конструктор

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize(); //этот объект нужен для того, чтобы узнать размеры монитора

        int x = screenSize.width;

        int y = screenSize.height;

        sun = new Sun(50, 50, 30, x / 2, y / 2); //создаём солнце (радиус, длина лучей, их кол-во, координата X, Y)

        bg = new Background(0,0, x, y, Color.BLACK); //создаём задний фон (координата начала X, Y, ширина монитора, высота, цвет)

        stars = new Stars(3000,x ,y); //создаём объект класса, работающего с массивом звёзд (их кол-во, ширина монитора, высота)

        int UFOCounter = 3000; //кол-во летающих тарелок

        ufo = new ArrayList<>(UFOCounter);

        for (int i = 0; i < UFOCounter; i++) {

            ufo.add(new UFO(50, 150, (int)(x * 0.75),(sun.getR() + sun.getL()) + 100, x, y, 3, 10));

            //создаём НЛО (минимальная ширина, максимальная, радиус большой орбиты (за пределами видимого экрана), малой, ширина монитора, высота, минимальное время полёта от большой орбиты к солнцу, максимальное)

        }

        int cometsCounter = 0; //кол-во комет

        comet = new ArrayList<>(cometsCounter);

        for (int i = 0; i < cometsCounter; i++) {

            comet.add(new Comet(10, sun.getR() / 2, (int)(x * 0.75), x, y,3, 5));

            //создаём кометы (минимальный радиус, максимальный, радиус большой орбиты (за пределами видимого экрана), ширина монитора, высота, минимальное время полёта от большой орбиты к центру монитора по оси X, максимальное)

        }
    }

    @Override

    protected void paintComponent(Graphics g) { //основная функция рисования

        Graphics2D gr = (Graphics2D)g;

        bg.draw(gr); //рисуем задний фон

        stars.draw(gr); //рисуем звёзды

        for (Comet comets : comet) {

            comets.draw(gr); //рисуем кометы

        }

        for (UFO ufos: ufo) {

            ufos.draw(gr); //рисуем НЛО

        }

        sun.draw(gr); //рисуем Солнце

        repaint(); //перерисовываем всё (так как у нас динамическая картинка)

    }
}