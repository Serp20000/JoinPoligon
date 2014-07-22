package Test;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Reader {
    private String path;
    private List<Point> pointList = new LinkedList<Point>();
    private List<String> pointFromFile = new ArrayList<String>();

    public Reader (String path){
        this.path=path;
    }

    public List<Point> read() throws Exception{
        String[] allPoint=null;
        Float x1=null;
        Float y1=null;
        File file = new File(path);
        if (! file.exists()) {
            System.out.println("File with name "+ path +" not found");
            return null;
        }
        Scanner sc = new Scanner(file);
        while(sc.hasNext()){
            pointFromFile.add(sc.nextLine().replaceAll("[^0-9- ]",""));
            System.out.println("usersInfo = " + pointFromFile);
        }
        sc.close();
        for (String element: pointFromFile){
             allPoint=element.split(" ");
        }
        if(allPoint!=null && allPoint.length>0){
            for (int i=0; i<allPoint.length; i++ ){
                if (!allPoint[i].equals("")){
                    try{
                        if (i%2!=0) x1=Float.parseFloat(allPoint[i]);
                        else{
                            y1=Float.parseFloat(allPoint[i]);
                            pointList.add(new Point(x1,y1));
                        }
                    }
                    catch (Exception e){
                        System.out.println("Ошибка чтения данных.Структура файла изменена.");
                        e.printStackTrace();
                    }
                }
            }
            for(int i=0; i<pointList.size(); i++ ){
                System.out.println("i = "+i+" x = " + pointList.get(i).getX());
                System.out.println("i = "+i+" y = " + pointList.get(i).getY());
            }
            return pointList;
        }
        else  return null;
    }
}
