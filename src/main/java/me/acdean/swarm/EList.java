package me.acdean.swarm;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PApplet;

/*
** A bunch of boxes with random positions within a range.
*/
public class EList {

    private final PApplet p;
    private static final int COUNT = 35;
    private final List<E> list = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(EList.class);

    EList(PApplet p) {
        this.p = p;
    }

    void init() {
        for (int c = 0 ; c < 2 ; c++) {
            for (int i = 0 ; i < COUNT ; i++) {
                PApplet.println("Adding ", c, i);
                E e;
                int retries = 0;
                do {
                    PApplet.println("Retry ", retries);
                    e = new E(p, c, (int)p.random(-200, -40), (int)p.random(-200 + 200 * c, 200 * c));
                    retries++;
                } while (collision(e, this.list) && retries < 100);
                if (retries >= 100) {
                    PApplet.println("Too many retries", this.list.size());
                    return;
                }
                this.list.add(e);
            }
        }
    }

    void draw() {
        for (E e : list) {
            e.draw();
        }
    }

    boolean collision(E e, List<E> list) {
        for (E e2 : list) {
            if (e.x + E.SIZE > e2.x
                    && e.x - E.SIZE < e2.x
                    && e.y + E.SIZE > e2.y
                    && e.y - E.SIZE < e2.y
            ) {
                return true;
            }
        }
        return false;
    }
}

class E {
    public static final int SIZE = 20;
    PApplet p;
    int c;
    float x, y, z;

    E(PApplet p, int c, int x, int y) {
        this.p = p;
        if (c == 0) {
            this.c = 0xffff0000;
        } else {
            this.c = 0xff00ff00;
        }
        this.x = x;
        this.y = y;
    }

    void draw() {
        p.pushMatrix();
        p.translate(x, y, z);
        p.stroke(c);
        p.noFill();
        p.box(SIZE);
        p.popMatrix();
    }
}
