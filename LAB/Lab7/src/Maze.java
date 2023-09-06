public class Maze {
    private int row, col;
    private boolean[][] map;
    private int[][] distance;
    private boolean[][] path;
    
    public Maze(char[][] map) {
        row = map.length;
        col = map[0].length;

        //map[y][x] is true iff it is empty
        this.map = new boolean[row][col];
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                this.map[i][j] = (map[i][j] == '.');
        
        //distance[y][x] is the distance from the source to (x,y)
        this.distance = new int[row][col];
        initDistance();

        //path[y][x] indicates whether (x,y) is in a shortest path
        this.path = new boolean[row][col];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isEmpty(int x, int y) {
        if(!(0 <= x && x < col))
            return false;
        if(!(0 <= y && y < row))
            return false;
        return map[y][x];
    }

    public int getDistance(int x, int y) {
        if(!(0 <= x && x < col))
            return -1;
        if(!(0 <= y && y < row))
            return -1;
        return distance[y][x];
    }

    public void setDistance(int x, int y, int d) {
        if(!(0 <= x && x < col))
            return;
        if(!(0 <= y && y < row))
            return;
        distance[y][x] = d;
    }

    public boolean setPath(int x, int y) {
        if(!(0 <= x && x < col))
            return false;
        if(!(0 <= y && y < row))
            return false;
        return path[y][x];
    }

    public void setPath(int x, int y, boolean p) {
        if(!(0 <= x && x < col))
            return;
        if(!(0 <= y && y < row))
            return;
        path[y][x] = p;
    }

    //initialize distance to the max value
    public void initDistance() {
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++)
                this.distance[i][j] = row*col;
    }
    
    //print the map and the distance
    public void print() {
        String RED    = "\33[1;31m";
        String YELLOW = "\33[1;33m";
        String RESET  = "\33[0m";

        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                if(!map[i][j])
                    System.out.print(RED + "###" + RESET);
                else if(path[i][j])
                    System.out.format(YELLOW + "%3d" + RESET, distance[i][j] );
                else
                    System.out.format("%3d", distance[i][j]);
            }
            System.out.println("");
        }
    }
}
