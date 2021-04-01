package me.acdean.swarm;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PApplet;

/*
** A bunch of boxes with random positions within a range.
*/
public class SList {

    private final PApplet p;
    private static final int COUNT = 3;
    private final List<S> list = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(SList.class);

    SList(PApplet p) {
        this.p = p;
    }

    void init() {
        for (int c = 0 ; c < 2 ; c++) {
            for (int i = 0 ; i < COUNT ; i++) {
                PApplet.println("Adding ", c, i);
                S s;
                int retries = 0;
                do {
                    PApplet.println("Retry ", retries);
                    s = new S(p, c, (int)p.random(40, 200), (int)p.random(-200 + 200 * c, 200 * c));
                    retries++;
                } while (collision(s, this.list) && retries < 100);
                if (retries >= 100) {
                    PApplet.println("Too many retries", this.list.size());
                    return;
                }
                this.list.add(s);
            }
        }
    }

    void draw() {
        for (S s : list) {
            s.draw();
        }
    }

    boolean collision(S s, List<S> list) {
        for (S s2 : list) {
            if (s.x + S.SIZE > s2.x
                    && s.x - S.SIZE < s2.x
                    && s.y + S.SIZE > s2.y
                    && s.y - S.SIZE < s2.y
            ) {
                return true;
            }
        }
        return false;
    }
}

class S {
    public static final int SIZE = 20;
    PApplet p;
    int c;
    float x, y, z;

    S(PApplet p, int c, int x, int y) {
        this.p = p;
        if (c == 0) {
            this.c = 0xff880000;
        } else {
            this.c = 0xff008800;
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
