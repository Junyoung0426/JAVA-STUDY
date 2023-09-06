public class Path {
    private static class Pos {
        public int x, y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Maze maze;
    private Queue<Pos> queue;
    private final static int dx[] = new int[]{-1, 1, 0, 0};
    private final static int dy[] = new int[]{0, 0, -1, 1};

    public Path(Maze map) {
        this.maze = map;
        queue = new ListQueue<Pos>();
    }

    public void findDistance(int fromX, int fromY, int toX, int toY) {
        maze.initDistance();

        if (maze.isEmpty(fromX, fromY)) {
            maze.setDistance(fromX, fromY, 0);
            Pos p = new Pos(fromX,fromY);
            queue.enqueue(p);
            //TODO: set the distance from (fromX, fromY) to itself to 0 and
            //      enqueue the position

        }

        while (!queue.isEmpty()) {
            //TODO: remove a marked position from the queue and
            //      break if (toX, toY) is reached
            Pos p = queue.dequeue();
            if(p.x == toX &&p.y == toY)
                break;

            //Check if distance of the four neighbors of p can be updated                
            for (int i = 0; i < dx.length; i++) {
                int x = p.x + dx[i];    //neighbor's x
                int y = p.y + dy[i];    //neighbor's y
                if(!((0<= x && x<maze.getCol())&&(0<= y && y<maze.getRow())))
                    continue;
                if(!maze.isEmpty(x,y))
                    continue;
                if(maze.getDistance(x,y)<= maze.getDistance(p.x,p.y)+1)
                    continue;
                maze.setDistance(x,y,maze.getDistance(p.x,p.y)+1);
                queue.enqueue(new Pos(x,y));

                //TODO: continue if 0 <= x < maze.col and 0 <= y < maze.row are not true

                //TODO: continue if (x, y) is not empty

                //TODO: continue if the distance to (x, y) is shorter than
                //      the distance to (p.x, p.y) + 1

                //TODO: update the distance to (x, y) to the distance to (p.x, p.y) + 1

                //TODO: enqueue (x, y) so that its neighbors can be updated
            }
        }
    }

    public void findPath(int fromX, int fromY, int toX, int toY) {
        int cx = toX, cy = toY;
        int d = maze.getDistance(cx, cy);

        maze.setPath(cx, cy, true);
        while (cx != fromX || cy != fromY) {
            for (int i = 0; i < dx.length; i++) {
                int x = cx + dx[i];    //neighbor's x
                int y = cy + dy[i];    //neighbor's y
                if(!((0<= x && x<maze.getCol())&&(0<= y && y<maze.getRow())))
                    continue;
                if(!maze.isEmpty(x,y))
                    continue;
                if(!(maze.getDistance(x,y)< d))
                    continue;
                //TODO: continue if 0 <= x < maze.col and 0 <= y < maze.row are not true

                //TODO: continue if (x, y) is not empty

                //TODO: continue unless the distance to (x, y) is shorter than d

                d = maze.getDistance(x, y);
                cx = x;
                cy = y;
                maze.setPath(cx, cy, true);
                break;
            }
        }
    }
}
