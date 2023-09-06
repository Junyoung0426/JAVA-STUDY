public class study {
    public static void main(String[] args) {
        String s = "Hello world";
        String ss = "";
        for (int i = 0; i < s.length(); i++) {
            char cc = s.charAt(i);
            if(cc == ' '){
                continue;
            }
            else{
                ss += cc;
            }
        }
        System.out.println(ss);
    }
}
