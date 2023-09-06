public class App {
    public static void main(String[] args) {
        Maze map= new Maze(map1);
        Path path = new Path(map);
        path.findDistance(1,  1,  14,  14);
        path.findPath(1,  1,  14,  14);
        map.print();
    }
    public static final char[][] map1 = {
        "################".toCharArray(),
        "#......####...##".toCharArray(),
        "##.###......#.##".toCharArray(),
        "#..#...##.#.#..#".toCharArray(),
        "####.#.##.#.####".toCharArray(),
        "#.....#........#".toCharArray(),
        "#.##.###########".toCharArray(),
        "#..............#".toCharArray(),
        "######.#.###.#.#".toCharArray(),
        "#....#.##...##.#".toCharArray(),
        "#.##......#....#".toCharArray(),
        "#.##.###########".toCharArray(),
        "#.#..#.#.......#".toCharArray(),
        "#.#.##..##.#.###".toCharArray(),
        "#..........#...#".toCharArray(),
        "################".toCharArray()
    };        
}
