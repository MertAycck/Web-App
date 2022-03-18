/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Web.App;

import java.util.ArrayList;

import static spark.Spark.get;
  import static spark.Spark.port;
  import static spark.Spark.post;

  import java.util.ArrayList;
  import java.util.HashMap;
  import java.util.Map;

import com.google.common.collect.ArrayListMultimap;

import spark.ModelAndView;
  import spark.template.mustache.MustacheTemplateEngine;


public class App {
    
    public static boolean isTranspose(ArrayList<ArrayList<Integer>> userMatrix,Integer ... nums) {
        int counter = 0;

        for(Integer i : nums){
            counter ++;
        }

        int len = (int)Math.sqrt(counter);
        
        if((len*len) != counter)
            return false;

        ArrayList<ArrayList<Integer>> ownMatrix = new ArrayList<>();
        ownMatrix.add(new ArrayList<Integer>());

        int line = 0;
        int counterOfNums = 0;
        for(Integer i : nums) {
            
            if(counterOfNums == len){
                line++;
                counterOfNums = 0;
                ownMatrix.add(new ArrayList<Integer>());
            }

            ownMatrix.get(line).add(i);
            counterOfNums++;
        }

        if(userMatrix.size() != ownMatrix.size() || userMatrix.get(0).size() != ownMatrix.get(0).size())
            return false;
        

        for(int i = 0; i < len; i++){
            for(int j = 0; j < len;j++){
                if(userMatrix.get(i).get(j) != ownMatrix.get(j).get(i))
                    return false;
            }
        }
        
        return true;
    }


    public static void main(String[] args) {
        port(getHerokuAssignedPort());

        get("/", (req, res) -> "Hello, World");

        post("/compute", (req, res) -> {

          String input1 = req.queryParams("input1");
          java.util.Scanner sc1 = new java.util.Scanner(input1);
          sc1.useDelimiter("[;\r\n]+");
          

          java.util.ArrayList<java.util.ArrayList<Integer>> matrix = new java.util.ArrayList<>();
          
          while (sc1.hasNextLine())
          {
            java.util.ArrayList<Integer> line = new java.util.ArrayList<>();
            String[] value = sc1.nextLine().split(" ");
            for(int i = 0; i < value.length; i++)
                line.add(Integer.parseInt(value[i]));
            matrix.add(line);
          }
          sc1.close();
          System.out.println(matrix.get(0));


          String input2 = req.queryParams("input2");
          String[] integersString = input2.split(" ");

          Integer[] integers = new Integer[integersString.length];
          for(int i = 0; i < integersString.length; i++){
                integers[i] = Integer.parseInt(integersString[i]);
          }

          boolean result = App.isTranspose(matrix, integers);

          Map<String, Boolean> map = new HashMap<String, Boolean>();
          map.put("result", result);
          return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
            (rq, rs) -> {
              Map<String, String> map = new HashMap<String, String>();
              map.put("result", "not computed yet!");
              return new ModelAndView(map, "compute.mustache");
            },
            new MustacheTemplateEngine());
    }
    
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
