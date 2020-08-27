package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;

    private static class Position {
        int x;
        int y;

        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void fillWithNothing(TETile[][] hw){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                hw[i][j] = Tileset.NOTHING;
            }
        }
    }

    public static void addHexagon(TETile[][] hw, int length, Position p, TETile t) {
        int len = length;
        int sX = p.x;
        for (int j = p.y; j < p.y + length ; j++) {
            for (int i = sX; i < sX + len; i++) {
                hw[i][j] = t;
            }
            sX = sX - 1;
            len = len + 2;
        }
        for (int j = p.y + length; j < p.y + 2 * length; j++) {
            for (int i = sX + 1; i < sX + len - 1; i++) {
                hw[i][j] = t;
            }
            sX = sX + 1;
            len = len -2;
        }
    }

    public static TETile generateRandomTile() {
        Random RANDOM = new Random();
        int tileNum = RANDOM.nextInt(11);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.AVATAR;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.LOCKED_DOOR;
            case 6: return Tileset.MOUNTAIN;
            case 7: return Tileset.SAND;
            case 8: return Tileset.TREE;
            case 9: return Tileset.UNLOCKED_DOOR;
            case 10: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    public static Position upNeighbourStartPoint(int length, Position p) {
        return new Position(p.x, p.y + 2 * length);
    }

    public static Position upLeftNeighbourStartPoint(int length, Position p) {
        return new Position(p.x - length * 2 + 1, p.y + length);
    }

    public static Position upRightNeighbourStartPoint(int length, Position p) {
        return new Position(p.x + length * 2 - 1, p.y + length);
    }

    public static void drawRandomVerticalHexes(TETile[][] hw, int length, Position p, int numOfHexes) {
        for (int i = 0; i < numOfHexes; i++) {
            addHexagon(hw, length, p, generateRandomTile());
            p = upNeighbourStartPoint(length, p);
        }
    }

    public static void drawTesselationOfHexes(TETile[][] hw, int length, Position p) {
        drawRandomVerticalHexes(hw, length, p, 5);

        Position leftPoint1 = upLeftNeighbourStartPoint(length, p);
        drawRandomVerticalHexes(hw, length, leftPoint1, 4);

        Position leftPoint2 = upLeftNeighbourStartPoint(length, leftPoint1);
        drawRandomVerticalHexes(hw, length, leftPoint2, 3);

        Position rightPoint1 = upRightNeighbourStartPoint(length, p);
        drawRandomVerticalHexes(hw, length, rightPoint1, 4);

        Position rightPoint2 = upRightNeighbourStartPoint(length, rightPoint1);
        drawRandomVerticalHexes(hw, length, rightPoint2, 3);
    }

    public static void main(String args[]) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexWorld = new TETile[WIDTH][HEIGHT];
        fillWithNothing(hexWorld);

        drawTesselationOfHexes(hexWorld, 3, new Position(20, 0));
        drawTesselationOfHexes(hexWorld, 2, new Position(45, 0));

        ter.renderFrame(hexWorld);
    }
}