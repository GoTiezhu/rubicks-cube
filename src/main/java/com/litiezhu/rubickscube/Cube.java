package com.litiezhu.rubickscube;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li Kai
 */
@Getter
public class Cube {
    public static final int BLUE = 0;
    public static final int ORANGE = 1;
    public static final int YELLOW = 2;
    public static final int GREEN = 3;
    public static final int RED = 4;
    public static final int WHITE = 5;

    public static final int FACE_NUMBER = 6;
    public static final int STICKER_IN_A_FACE = 9;

    private static Map<Character, Integer> dir2Color;

//    private static final Map<Integer, Integer> backFace = new HashMap<>();
//    private static final Map<Integer, Integer> upFace = new HashMap<>();
//    private static final Map<Integer, Integer> bottomFace = new HashMap<>();
//    private static final Map<Integer, Integer> leftFace = new HashMap<>();
//    private static final Map<Integer, Integer> rightFace = new HashMap<>();
//
//
//    static {
//        backFace.put(BLUE, GREEN);
//        upFace.put(BLUE, YELLOW);
//        bottomFace.put(BLUE, WHITE);
//        leftFace.put(BLUE, ORANGE);
//        rightFace.put(BLUE, RED);
//
//        backFace.put(ORANGE, RED);
//        upFace.put(ORANGE, YELLOW);
//        bottomFace.put(ORANGE, WHITE);
//        leftFace.put(ORANGE, GREEN);
//        rightFace.put(ORANGE, BLUE);
//
//        backFace.put(YELLOW, WHITE);
//        upFace.put(YELLOW, GREEN);
//        bottomFace.put(YELLOW, BLUE);
//        leftFace.put(YELLOW, ORANGE);
//        rightFace.put(YELLOW, RED);
//
//        backFace.put(GREEN, BLUE);
//        upFace.put(GREEN, WHITE);
//        bottomFace.put(GREEN, YELLOW);
//        leftFace.put(GREEN, ORANGE);
//        rightFace.put(GREEN, RED);
//
//        backFace.put(RED, ORANGE);
//        upFace.put(RED, YELLOW);
//        bottomFace.put(RED, WHITE);
//        leftFace.put(RED, BLUE);
//        rightFace.put(RED, GREEN);
//
//        backFace.put(WHITE, YELLOW);
//        upFace.put(WHITE, BLUE);
//        bottomFace.put(WHITE, GREEN);
//        leftFace.put(WHITE, ORANGE);
//        rightFace.put(WHITE, RED);
//    }

    static {
        dir2Color = new HashMap<>();
        dir2Color.put('F', BLUE);
        dir2Color.put('R', RED);
        dir2Color.put('U', YELLOW);
        dir2Color.put('B', GREEN);
        dir2Color.put('L', ORANGE);
        dir2Color.put('D', WHITE);
    }


    private Map<Integer, List<Integer>> faces = new HashMap<>(8);

    private static boolean checkFaces(Map<Integer, List<Integer>> _faces) {
        if(_faces == null || _faces.size() != FACE_NUMBER) return false;

        for (int f : _faces.keySet()) {
            if(f < 0 || f >= FACE_NUMBER) return false;
            List<Integer> tmp = _faces.get(f);
            if(tmp == null || tmp.size() != STICKER_IN_A_FACE) return false;
            for(int s: tmp) {
                if(s < 0 || s >= FACE_NUMBER) return false;
            }
        }
        return true;
    }

    public Cube() {
        for (int i = 0; i < FACE_NUMBER; i++) {
            List<Integer> face = new ArrayList<>(9);
            for (int j = 0; j < STICKER_IN_A_FACE; j++) {
                face.add(i);
            }
            faces.put(i, face);
        }
    }

    public Cube(Map<Integer, List<Integer>> _faces) {
        this();
        if(checkFaces(_faces)) {
            for (int f : _faces.keySet()) {
                faces.put(f, new ArrayList<>(_faces.get(f)));
            }
        }
    }

    // 将某个面顺时针/逆时针旋转90度
    public Cube twist(int mainFace, boolean clockwise) {
        Cube after = new Cube(this.faces);

        int[] rotateMapClockwise = new int[]{6, 3, 0, 7, 4, 1, 8, 5, 2};
        int[] rotateMapNotClockwise = new int[]{2, 5, 8, 1, 4, 7, 0, 3, 6};

        int[] buf678 = new int[]{6, 7, 8};
        int[] buf852 = new int[]{8, 5, 2};
        int[] buf210 = new int[]{2, 1, 0};
        int[] buf036 = new int[]{0, 3, 6};
        if (clockwise) {
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < STICKER_IN_A_FACE; i++) {
                tmp.add(this.faces.get(mainFace).get(rotateMapClockwise[i]));
            }
            after.faces.put(mainFace, tmp);

            int[] buf;

            switch (mainFace) {
                case BLUE:
                    changeTo(after, YELLOW, buf678, ORANGE, buf852);
                    changeTo(after, RED, buf036, YELLOW, buf678);
                    changeTo(after, WHITE, buf210, RED, buf678);
                    changeTo(after, ORANGE, buf852, WHITE, buf210);
                    break;
                case ORANGE:
                    changeTo(after, YELLOW, buf036, GREEN, buf036);
                    changeTo(after, BLUE, buf036, YELLOW, buf036);
                    changeTo(after, WHITE, buf036, BLUE, buf036);
                    changeTo(after, GREEN, buf036, WHITE, buf036);
                    break;
                case RED:
                    changeTo(after, YELLOW, buf852, BLUE, buf852);
                    changeTo(after, GREEN, buf852, YELLOW, buf852);
                    changeTo(after, WHITE, buf852, GREEN, buf852);
                    changeTo(after, BLUE, buf852, WHITE, buf852);
                    break;
                case GREEN:
                    changeTo(after, WHITE, buf678, ORANGE, buf036);
                    changeTo(after, RED, buf852, WHITE, buf678);
                    changeTo(after, YELLOW, buf210, RED, buf852);
                    changeTo(after, ORANGE, buf036, YELLOW, buf210);
                    break;
                case YELLOW:
                    changeTo(after, GREEN, buf678, ORANGE, buf210);
                    changeTo(after, RED, buf210, GREEN, buf678);
                    changeTo(after, BLUE, buf210, RED, buf210);
                    changeTo(after, ORANGE, buf210, BLUE, buf210);
                    break;
                case WHITE:
                    changeTo(after, BLUE, buf678, ORANGE, buf678);
                    changeTo(after, RED, buf678, BLUE, buf678);
                    changeTo(after, GREEN, buf210, RED, buf678);
                    changeTo(after, ORANGE, buf678, GREEN, buf210);
                    break;
            }
        } else {
            List<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < STICKER_IN_A_FACE; i++) {
                tmp.add(this.faces.get(mainFace).get(rotateMapNotClockwise[i]));
            }
            after.faces.put(mainFace, tmp);

            int[] buf = new int[]{6, 7, 8};
            switch (mainFace) {
                case BLUE:
                    changeTo(after, YELLOW, buf678, RED, buf036);
                    changeTo(after, RED, buf036, WHITE, buf210);
                    changeTo(after, WHITE, buf210, ORANGE, buf852);
                    changeTo(after, ORANGE, buf852, YELLOW, buf678);
                    break;
                case ORANGE:
                    changeTo(after, YELLOW, buf036, BLUE, buf036);
                    changeTo(after, BLUE, buf036, WHITE, buf036);
                    changeTo(after, WHITE, buf036, GREEN, buf036);
                    changeTo(after, GREEN, buf036, YELLOW, buf036);
                    break;
                case RED:
                    changeTo(after, YELLOW, buf852, GREEN, buf852);
                    changeTo(after, GREEN, buf852, WHITE, buf852);
                    changeTo(after, WHITE, buf852, BLUE, buf852);
                    changeTo(after, BLUE, buf852, YELLOW, buf852);
                    break;
                case GREEN:
                    changeTo(after, WHITE, buf678, RED, buf852);
                    changeTo(after, RED, buf852, YELLOW, buf210);
                    changeTo(after, YELLOW, buf210, ORANGE, buf036);
                    changeTo(after, ORANGE, buf036, WHITE, buf678);
                    break;
                case YELLOW:
                    changeTo(after, GREEN, buf678, RED, buf210);
                    changeTo(after, RED, buf210, BLUE, buf210);
                    changeTo(after, BLUE, buf210, ORANGE, buf210);
                    changeTo(after, ORANGE, buf210, GREEN, buf678);
                    break;
                case WHITE:
                    changeTo(after, BLUE, buf678, RED, buf678);
                    changeTo(after, RED, buf678, GREEN, buf210);
                    changeTo(after, GREEN, buf210, ORANGE, buf678);
                    changeTo(after, ORANGE, buf678, BLUE, buf678);
                    break;
            }
        }

        return after;

    }

    public Cube twist(String action) {
        if (action == null
                || action.length() < 1
                || action.length() > 2
                || !dir2Color.keySet().contains(action.charAt(0))) {
            return null;
        }
        int tFace = dir2Color.get(action.charAt(0));
        boolean clockwise = true;
        if (action.length() == 2) {
            clockwise = false;
        }

        return twist(tFace, clockwise);
    }

    /**
     * 把本Cube一面的一些颜色换到其他面
     *
     * @param after
     * @param desFace
     * @param des
     * @param srcFace
     * @param src
     */
    private void changeTo(Cube after, int desFace, int[] des, int srcFace, int[] src) {
        List<Integer> afterList = after.faces.get(desFace);
        List<Integer> oldList = this.faces.get(srcFace);

        for (int i = 0; i < des.length; i++) {
            afterList.set(des[i], oldList.get(src[i]));
        }
    }
}
