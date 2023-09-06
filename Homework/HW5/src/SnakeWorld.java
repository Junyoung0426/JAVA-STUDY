import java.util.Iterator;
import java.util.Random;

public class SnakeWorld {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 70;
    public static final int MAX_APPLES = 8;

    public static class Pos {
        public int x, y;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean equals(Pos pos) {
            return x == pos.x && y == pos.y;
        }
    }

    //class Apple
    private static class Apple {
        private Wall wall;
        private Snake snake;
        private Pos pos;  //apple position
        private int createCount;

        public Apple(Wall wall, Snake snake) {
            this.wall = wall;
            this.snake = snake;
            init();
        }

        public void init() {
            createCount = 0;
            create();
        }

        public int create() {
            Random rand = new Random();
            //TODO: randomly place an apple at (x,y) such that
            // 1. 0 <= x < WIDTH,
            // 2. 0 <= y < HEIGHT, and
            // 3. it does not hit the wall nor the snake (use the hit method)
            pos = new Pos(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            while (hit(wall, pos) || hit(snake, pos)) {
                pos = new Pos(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            }
            return createCount++;
        }

        public int getCount() {
            return createCount;
        }

        public Pos getPos() {
            return pos;
        }
    }

    //class Wall
    private static class Wall implements Iterable<Pos> {
        private DynamicArrayDeque<Pos> blocks;

        public Wall() {
            init(0);
        }

        public void init(int n) {
            blocks = new DynamicArrayDeque<Pos>();

            for (int x = 0; x < WIDTH; x++) {
                //TODO: add (x, 0) and (x, HEIGHT-1) to blocks
                blocks.addLast(new Pos(x, 0));
                blocks.addLast(new Pos(x, HEIGHT - 1));
            }
            for (int y = 0; y < HEIGHT; y++) {
                //TODO: add (0, y) and (WIDTH-1, y) to blocks
                blocks.addLast(new Pos(0, y));
                blocks.addLast(new Pos(WIDTH - 1, y));
            }
            for (int k = 0; k < n; k++) {
                int x = (k + 1) * WIDTH / (n + 1);
                for (int y = 0; y < HEIGHT * 2 / 5; y++)
                    //TODO add (x, y) to blocks
                    blocks.addLast(new Pos(x,y));
                for (int y = HEIGHT * 3 / 5; y < HEIGHT; y++)
                    //TODO add (x, y) to blocks
                    blocks.addLast(new Pos(x,y));

            }
        }

        public Iterator<Pos> iterator() {
            //TODO: return the iterator of blocks
            return blocks.iterator();
        }
    }

    //class Snake
    private static class Snake implements Iterable<Pos> {
        private DynamicArrayDeque<Pos> body;
        private static final int[] DX = new int[]{0, 1, 0, -1};  //NESW
        private static final int[] DY = new int[]{1, 0, -1, 0};  //NESW
        private int dir;
        private int incr;

        public Snake() {
            init();
        }

        public void init() {
            incr = 1;
            dir = 1; //E
            //TODO: create body and
            //      add Pos(1, HEIGHT/2) to body
            body = new DynamicArrayDeque<>();
            body.addLast(new Pos(1,HEIGHT/2));
        }

        public void turnRight() {
            //TODO: rotate dir in this order: 0 -> 1 -> 2 -> 3 -> 0 -> 1 ...
            if(dir ==0)
                dir =1;
            else if (dir ==1)
                dir=2;
            else if(dir==2)
                dir=3;
            else
                dir=0;
        }

        public void turnLeft() {
            //TODO: rotate dxir in this order: 0 -> 3 -> 2 -> 1 -> 0 -> 3 ...
            if(dir ==0)
                dir =3;
            else if (dir ==3)
                dir=2;
            else if(dir==2)
                dir=1;
            else
                dir=0;
        }

        public Pos nextHeadPos() {
            //TODO: get the head position from body
            //      add DX[dir] and DY[dir] to the head
            //      return the result as a Pos
            Pos head = body.first();
            return new Pos(head.x + DX[dir], head.y + DY[dir]);
        }

        public void move() {
            //TODO: add nextHeadPos() to body
            body.addFirst(nextHeadPos());

            //TODO: if incr > 0 then decrease it by 1
            //      otherwise, remove tail from body
            if ( incr >0)
                incr--;
            else
                body.removeLast();
        }

        public void grow() {
            //TODO: increase incr by the size of body
            incr+= body.size();
        }

        public int size() {
            //TODO: return the size of body
            return body.size();
        }

        public Iterator<Pos> iterator() {
            //TODO: return the iterator of body
            return body.iterator();
        }
    }

    private Apple apple;
    private Wall wall;
    private Snake snake;
    private int stageCount;
    private int score;
    private boolean gameOver;

    public SnakeWorld() {
        init();
    }

    public void init() {
        wall = new Wall();
        snake = new Snake();
        apple = new Apple(wall, snake);
        stageCount = 0;
        score = 0;
        gameOver = false;
    }

    public Iterable<Pos> getWallPos() {
        return wall;
    }

    public Iterable<Pos> getSnakePos() {
        return snake;
    }

    public Pos getApplePos() {
        return apple.getPos();
    }

    public int getAppleCount() {
        return apple.getCount();
    }

    public int getScore() {
        return score;
    }

    public void turnRight() {
        snake.turnRight();
    }

    public void turnLeft() {
        snake.turnLeft();
    }

    public boolean step() {
        if (gameOver)
            return true;
        Pos head = snake.nextHeadPos();


        if(hit(wall.blocks, head) || hit(snake.body,head)) {
            gameOver = true;
            return true;
        }
        //hit the wall or bite itself
        //TODO: using the hit method below, if head hit the wall or
        //      hit the snake itself, set gameOver to true and return true
        System.out.print(apple.getPos());
        if(apple.getPos().equals(head)) {
            score += ((DynamicArrayDeque<Pos>)snake.body).size();
            if(apple.getCount() == MAX_APPLES)
                newStage();
            else {
                snake.grow();
                apple.create();
            }
        }

        //TODO: Check if head hits a wall or bites itself using the
        //      hit method below. If it did, set gameOver to true
        //      and return true

        //TODO: Check if the head hits the apple. If it did
        //      1. increase score by the snake's size
        //      2. if apple.getCount() == MAX_APPLES call newStage()
        //         otherwise, grow the snake and create a new apple

        //move to the next position
        snake.move();

        return false;
    }

    private void newStage() {
        stageCount++;
        wall.init(stageCount);
        snake.init();
        apple.init();
    }

    private static boolean hit(Iterable<Pos> collection, Pos pos) {
        //TODO: return whether collection has pos
        for(Pos e : collection)
            if(e.equals(pos)){
                return true;
            }
        return false;
    }
}