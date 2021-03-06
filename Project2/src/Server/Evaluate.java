package Server;

import com.sun.scenario.effect.impl.sw.java.JSWLinearConvolvePeer;
import java.util.StringTokenizer;

public interface Evaluate {

    static int calculate(String str){ // Can we write code blocks in interfaces ? in which case- it is possible?

        StringTokenizer st = new StringTokenizer(str);
        int ans = 0;
        int v1 = Integer.parseInt(st.nextToken());
        String op = st.nextToken();
        int v2 = Integer.parseInt(st.nextToken());

        switch (op){
            case "+" :
                ans = v1+v2;
                break;
            case "-" :
                ans = v1-v2;
                break;
            case "*" :
                ans = v1*v2;
                break;
            case "/" :
                ans = v1/v2;
                break;

        }
        return ans;
    }
}
