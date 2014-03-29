package io.matthieuhostache.loustics.games.labyrinthe;

/**
 * Created by matthieu on 29/03/14.
 */
public class LabyrintheCreator {
    public static Labyrinthe getMaze(int mazeNo) {
        Labyrinthe labyrinthe = null;
        if(mazeNo == 1) {
            labyrinthe = new Labyrinthe();
            boolean[][] vLines = new boolean[][]{
                    {true ,false,false,false,true ,false,false},
                    {true ,false,false,true ,false,true ,true },
                    {false,true ,false,false,true ,false,false},
                    {false,true ,true ,false,false,false,true },
                    {true ,false,false,false,true ,true ,false},
                    {false,true ,false,false,true ,false,false},
                    {false,true ,true ,true ,true ,true ,false},
                    {false,false,false,true ,false,false,false}
            };
            boolean[][] hLines = new boolean[][]{
                    {false,false,true ,true ,false,false,true ,false},
                    {false,false,true ,true ,false,true ,false,false},
                    {true ,true ,false,true ,true ,false,true ,true },
                    {false,false,true ,false,true ,true ,false,false},
                    {false,true ,true ,true ,true ,false,true ,true },
                    {true ,false,false,true ,false,false,true ,false},
                    {false,true ,false,false,false,true ,false,true }
            };
            labyrinthe.setVerticalLines(vLines);
            labyrinthe.setHorizontalLines(hLines);
            labyrinthe.setStartPosition(0, 0);
            labyrinthe.setFinalPosition(7, 7);
        }
        if(mazeNo == 2) {
            labyrinthe = new Labyrinthe();
            boolean[][] vLines = new boolean[][]{
                    {false,false,false,true ,false,false,false},
                    {false,false,true ,false,true ,false,false},
                    {false,false,true ,true ,false,false,false},
                    {false,false,true ,true ,true ,false,false},
                    {false,false,true ,false,true ,false,false},
                    {true ,false,false,true ,false,true ,false},
                    {true ,false,true ,true ,false,false,false},
                    {false,false,true ,false,false,false,true }
            };
            boolean[][] hLines = new boolean[][]{
                    {false,true ,true ,true ,false,true ,true ,true },
                    {true ,true ,false,false,true ,true ,true ,false},
                    {false,true ,true ,false,false,false,true ,true },
                    {true ,true ,false,false,false,true ,true ,false},
                    {false,true ,true ,true ,true ,false,true ,false},
                    {false,false,true ,false,false,true ,true ,true },
                    {false,true ,false,false,true ,true ,false,false}
            };
            labyrinthe.setVerticalLines(vLines);
            labyrinthe.setHorizontalLines(hLines);
            labyrinthe.setStartPosition(0, 7);
            labyrinthe.setFinalPosition(7, 0);
        }
        if(mazeNo == 3) {
            labyrinthe = new Labyrinthe();
            boolean[][] vLines = new boolean[][]{
                    {false,false,true ,false,false,false,true ,false,false,false,false,false},
                    {false,true ,false,false,false,true ,false,false,false,false,true ,true },
                    {true ,false,false,false,false,true ,false,false,false,false,true ,true },
                    {true ,true ,false,false,false,true ,true ,true ,false,false,true ,true },
                    {true ,true ,true ,false,false,true ,true ,false,true ,false,true ,true },
                    {false,true ,true ,true ,false,true ,false,false,false,true ,false,false},
                    {false,false,false,true ,false,true ,false,true ,false,false,false,false},
                    {false,false,true ,false,true ,false,true ,true ,false,true ,false,false},
                    {true ,true ,true ,true ,false,true ,true ,false,false,true ,false,false},
                    {false,false,false,true ,false,false,true ,true ,false,true ,true ,false},
                    {false,false,true ,false,true ,false,true ,false,false,false,false,false},
                    {true ,true ,true ,true ,true ,true ,true ,false,false,true ,false,false},
                    {false,false,true ,false,false,true ,false,false,false,false,true ,false}
            };
            boolean[][] hLines = new boolean[][]{
                    {true ,false,false,true ,true ,false,false,false,true ,true ,true ,true ,false},
                    {false,true ,true ,true ,true ,true ,true ,true ,true ,true ,false,false,false},
                    {false,false,true ,true ,true ,false,false,true ,true ,true ,true ,false,false},
                    {false,false,false,true ,true ,true ,false,false,false,true ,false,false,false},
                    {false,false,false,false,true ,false,false,true ,true ,true ,false,false,false},
                    {true ,true ,false,false,false,true ,true ,true ,true ,false,true ,true ,true },
                    {false,true ,true ,true ,true ,true ,false,false,false,true ,true ,true ,false},
                    {true ,false,false,false,true ,false,true ,false,true ,false,false,true ,true },
                    {false,true ,false,false,false,true ,false,true ,true ,true ,true ,true ,false},
                    {true ,true ,false,true ,false,true ,true ,false,false,true ,false,true ,false},
                    {false,true ,true ,false,true ,false,false,true ,true ,false,true ,true ,true },
                    {false,true ,false,false,true ,false,false,true ,true ,true ,false,false,true }
            };
            labyrinthe.setVerticalLines(vLines);
            labyrinthe.setHorizontalLines(hLines);
            labyrinthe.setStartPosition(0, 0);
            labyrinthe.setFinalPosition(12, 12);
        }
        return labyrinthe;
    }
}