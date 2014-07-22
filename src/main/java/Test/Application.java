package Test;

import java.util.LinkedList;
import java.util.List;

public class Application
{
    private List<Point> poligon1=new LinkedList<Point>();    //Poligon from file 1
    private List<Point> poligon2=new LinkedList<Point>();   //Poligon from file 2
    private List<Point> currentPol=new LinkedList<Point>();
    private List<Point> secondPol=new LinkedList<Point>();
    private List<Point> poligonJoin=new LinkedList<Point>();
    private List<Point> pointIntersection=new LinkedList<Point>();  //All Point Intercection
    private List<Integer> indexPoligon1=new LinkedList<Integer>(); //Индексы вершин граней пересечения пол 1
    private List<Integer> indexPoligon2=new LinkedList<Integer>(); //Индексы вершин граней пересечения пол 2
    private List<Integer> curIndex=new LinkedList<Integer>();
    private List<Integer> secIndex=new LinkedList<Integer>();
    private Intersect Inter =new Intersect();
    private final Point BigPoint = new Point(54321, 12345);
    private final int maximumCount=100000;
    private Point Rezult;
    private int indexPoligon;  //Индекс: 1 или 2 полигон обрабатываем
    private int indexPoint;    //0-идем по убыванию индеска; 1-по возрастанию
    int count=0;

    public int intersection(List<Point>pol1,List<Point> pol2) throws Exception{
        poligon1=pol1;
        poligon2=pol2;
        int countIntersection=0;
        boolean flag=true;
        boolean flag1=true;
        for (int i=1; i<poligon1.size(); i++){
            for (int j = 1; j < poligon2.size(); j++) {
                Rezult = Inter.findPointIntersection(poligon1.get(i - 1), poligon1.get(i),
                        poligon2.get(j - 1), poligon2.get(j));
                if (Rezult != null && Rezult.isInter()) {
                    for (Point pp: pointIntersection){
                        if(pp.getX()==Rezult.getX()&&pp.getY()==Rezult.getY()){
                            flag=false;
                        }
                    }
                    if(flag){
                        pointIntersection.add(Rezult);
                        indexPoligon1.add(i);
                        indexPoligon2.add(j);
                    }
                    flag=true;
                }
            }
        }
        if (pointIntersection.size()==0) {
            System.out.println("Полигоны не пересекаются ");
            return 0;
        }
        if (pointIntersection.size()==1) {
            System.out.println("Полигоны касаются друг друга ");
            return 0;
        }
        if (pointIntersection.size()>0){
            System.out.println("размер pointIntersection = " + pointIntersection.size());
            for (int i=0; i<pointIntersection.size();i++){
                if (pointIntersection.get(i).isInter()){
                    System.out.println((i+1)+" Точка пересечения полигонов");
                    System.out.println("x = " + pointIntersection.get(i).getX());
                    System.out.println("y = " + pointIntersection.get(i).getY());
                    System.out.println("indexPoligon1 = " + indexPoligon1.get(i));
                    System.out.println("indexPoligon2 = " + indexPoligon2.get(i));
                }
            }
            poligonJoin.add(pointIntersection.get(0));
        }

        /**Обработка 4 вершин окружающих первую точку пересечения
        *на предмет их нахождения в противоположном полигоне
        **/

        for (int j=0; j<2; j++){
            for (int i=1; i<poligon1.size(); i++){
                Rezult=Inter.findPointIntersection(poligon1.get(i-1), poligon1.get(i),
                        poligon2.get(indexPoligon2.get(0)-j), BigPoint);
                if (Rezult!=null&&Rezult.isInter()) countIntersection++;
            }
            if (countIntersection%2!=0){
                //Если дошли до начала полигона, продолжаем с последней точки
                if ((indexPoligon2.get(0)-j)==0) poligonJoin.add(poligon2.get(poligon2.size()-1));
                //Если дошли до конца полигона, продолжаем с первой точки
                else if ((indexPoligon2.get(0)-j)==(poligon2.size()-1)) poligonJoin.add(poligon2.get(0));
                else if ((indexPoligon2.get(0)-j)!=(poligon2.size()-1)&&(indexPoligon2.get(0)-j)!=0)
                    poligonJoin.add(poligon2.get(indexPoligon2.get(0)-j));
                indexPoligon=2;
                indexPoint=j;
                break;
            }
        }
        if (countIntersection%2==0){
            countIntersection=0;
            for (int j=0; j<2; j++){
                for (int i=1; i<poligon2.size(); i++){
                    Rezult=Inter.findPointIntersection(poligon2.get(i-1), poligon2.get(i),
                           poligon1.get(indexPoligon1.get(0)-j), BigPoint);
                    if (Rezult!=null&&Rezult.isInter()) countIntersection++;
                }
                if (countIntersection%2!=0){
                    //Если дошли до начала полигона, продолжаем с последней точки
                    if ((indexPoligon1.get(0)-j)==0) poligonJoin.add(poligon1.get(poligon1.size()-1));
                    //Если дошли до конца полигона, продолжаем с первой точки
                    else if ((indexPoligon1.get(0)-j)==(poligon1.size()-1)) poligonJoin.add(poligon1.get(0));
                    else if ((indexPoligon1.get(0)-j)!=(poligon1.size()-1)&&(indexPoligon1.get(0)-j)!=0)
                       poligonJoin.add(poligon1.get(indexPoligon1.get(0)-j));
                    indexPoligon=1;
                    indexPoint=j;
                    break;
                }
            }
        }
        // если все 4 вершины лежат вне противотоложного полигона
        if (countIntersection%2==0){
            for (int i=0;i<indexPoligon2.size();i++){
                if (pointIntersection.indexOf(poligonJoin.get(poligonJoin.size()-1))!=i){
                    if (indexPoligon2.get(pointIntersection.indexOf(poligonJoin.get
                            (poligonJoin.size()-1)))==(int)indexPoligon2.get(i)){
                        poligonJoin.add(pointIntersection.get(i));
                        indexPoligon=2; //меняем местами полигоны
                        flag1=false;
                        return 1;
                    }
                }
            }
        }
        if (countIntersection%2==0&&flag1){
            for (int i=0;i<indexPoligon1.size();i++){
                if (pointIntersection.indexOf(poligonJoin.get(poligonJoin.size()-1))!=i){
                    if (indexPoligon1.get(pointIntersection.indexOf(poligonJoin.get
                            (poligonJoin.size()-1)))==(int)indexPoligon1.get(i)){
                        poligonJoin.add(pointIntersection.get(i));
                        indexPoligon=1; //меняем местами полигоны
                        flag1=false;
                        return 1;
                    }
                }
            }
        }
    return 1;
    }

    public  List<Point> findJoinPoligon () throws Exception{
        int countIntersection=0;
        while (true){
            count++;
            if (count==maximumCount){
                System.out.println("ОШИБКА! Расчет текущих полигонов прекращен.");
                return null;
            }
            boolean flag1=true;
            boolean flag2=true;
            boolean flag3=true;
            boolean doIt=true;
            if (indexPoligon==1) {
                currentPol=poligon1;
                curIndex=indexPoligon1;
            }
            if (indexPoligon==2) {
                currentPol=poligon2;
                curIndex=indexPoligon2;
            }
            if( indexPoint==0) indexPoint=1;
            else indexPoint=0;

            //Проверка на равенство первой и последней точек в общем полигоне
            if( poligonJoin.get(0).getX()==poligonJoin.get(poligonJoin.size()-1).getX()&&
                    poligonJoin.get(0).getY()==poligonJoin.get(poligonJoin.size()-1).getY()){
                return poligonJoin;
            }
            //Проверка на равенство последней общей точки вершине полигона и точке пересечения полигонов
            for (int i=0; i<poligon1.size(); i++){
                for (int j=0; j<pointIntersection.size(); j++){
                    if(poligonJoin.get(poligonJoin.size()-1).getX()==poligon1.get(i).getX()&&
                            poligonJoin.get(poligonJoin.size()-1).getX()==pointIntersection.get(j).getX()&&
                            poligonJoin.get(poligonJoin.size()-1).getY()==poligon1.get(i).getY()&&
                            poligonJoin.get(poligonJoin.size()-1).getY()==pointIntersection.get(j).getY()){
                        poligonJoin.remove(poligonJoin.get(poligonJoin.size()-1));
                        poligonJoin.add(pointIntersection.get(j));
                        flag3=false;
                        break;
                    }
                }
                if (!flag3)  break;
            }
            for (int i=0; i<poligon2.size(); i++){
                for (int j=0; j<pointIntersection.size(); j++){
                    if(poligonJoin.get(poligonJoin.size()-1).getX()==poligon2.get(i).getX()&&
                            poligonJoin.get(poligonJoin.size()-1).getX()==pointIntersection.get(j).getX()&&
                            poligonJoin.get(poligonJoin.size()-1).getY()==poligon2.get(i).getY()&&
                            poligonJoin.get(poligonJoin.size()-1).getY()==pointIntersection.get(j).getY()){
                        poligonJoin.remove(poligonJoin.get(poligonJoin.size()-1));
                        poligonJoin.add(pointIntersection.get(j));
                        flag3=false;
                        break;
                    }
                }
                if (!flag3)  break;
            }
            while (flag1&&flag3){
                count++;
                if (count==maximumCount){
                    System.out.println("ОШИБКА! Расчет текущих полигонов прекращен.");
                    return null;
                }
                for (Integer pp: curIndex){
                    flag1=true;
                    if ((currentPol.indexOf(poligonJoin.get(poligonJoin.size()-1))+indexPoint)==pp){
                        flag1=false;
                        break;
                    }
                }
                //Проверка на равенство первой и последней точек в общем полигоне
                if( poligonJoin.get(0).getX()==poligonJoin.get(poligonJoin.size()-1).getX()&&
                        poligonJoin.get(0).getY()==poligonJoin.get(poligonJoin.size()-1).getY()){
                    return poligonJoin;
                }
                if (flag1){
                    //Движемся по вершинам полигона вперед
                    if (indexPoint==1){
                        //Если  дошли до конца полигона, продолжаем с первой точки
                        if((currentPol.get(currentPol.size()-1).equals(poligonJoin.get(poligonJoin.size()-1)))){
                            poligonJoin.remove(currentPol.get(currentPol.size()-1));
                            poligonJoin.add(currentPol.get(0));
                        }
                        else
                            poligonJoin.add(currentPol.get(currentPol.indexOf(poligonJoin.get(poligonJoin.size()-1))+1));
                    }
                    //Движемся по вершинам полигона назад
                    if (indexPoint==0){
                        //Если  дошли до начала полигона, продолжаем с последней точки
                        if(currentPol.get(0).equals(poligonJoin.get(poligonJoin.size() - 1))){
                            poligonJoin.remove(currentPol.get(0));
                            poligonJoin.add(currentPol.get(currentPol.size()-1));
                        }
                        else
                            poligonJoin.add(currentPol.get(currentPol.indexOf(poligonJoin.get(poligonJoin.size()-1))-1));
                    }
                }
            }
            if (!flag1&&flag3){
                // Если flag1=false, то на пути есть точка пересечения полигонов
                poligonJoin.add(pointIntersection.get(curIndex.indexOf(currentPol.indexOf
                        (poligonJoin.get(poligonJoin.size()-1))+indexPoint)));
            }
            //Проверка на равенство первой и последней точек в общем полигоне
            if( poligonJoin.get(0).getX()==poligonJoin.get(poligonJoin.size()-1).getX()&&
                    poligonJoin.get(0).getY()==poligonJoin.get(poligonJoin.size()-1).getY()){
                return poligonJoin;
            }

            /**Обработка двух вершин противоположного полигона
             *на предмет их нахождения в текущем полигоне
             **/

            while (doIt){
                count++;
                countIntersection=0;
                if (count==maximumCount){
                    System.out.println("ОШИБКА! Расчет текущих полигонов прекращен.");
                    return null;
                }
                flag2=true;
                if (indexPoligon==1) {
                    currentPol=poligon1; //текущий
                    curIndex=indexPoligon1;
                    secondPol=poligon2;   //смежный его две точки проверяем
                    secIndex=indexPoligon2;
                }
                if (indexPoligon==2) {
                    currentPol=poligon2;
                    curIndex=indexPoligon2;
                    secondPol=poligon1;
                    secIndex=indexPoligon1;
                }
                //Проверка на равенство первой и последней точек в общем полигоне
                if( poligonJoin.get(0).getX()==poligonJoin.get(poligonJoin.size()-1).getX()&&
                        poligonJoin.get(0).getY()==poligonJoin.get(poligonJoin.size()-1).getY()){
                    return poligonJoin;
                }
                for (int j=0; j<2; j++){
                    for (int i=1; i<currentPol.size(); i++){
                        Rezult=Inter.findPointIntersection(currentPol.get(i-1), currentPol.get(i), secondPol.get(secIndex.
                                get(pointIntersection.indexOf(poligonJoin.get(poligonJoin.size() - 1)))-j), BigPoint);
                        if (Rezult!=null&&Rezult.isInter()) countIntersection++;
                    }

                    if (countIntersection%2!=0){
                        //Если мы дошли до начала полигона, продолжаем с последней точки
                         if ((secIndex.get(pointIntersection.indexOf(poligonJoin.get(poligonJoin.size() - 1)))-j)==0)
                            poligonJoin.add(secondPol.get(secondPol.size()-1));
                            //Если мы дошли до конца полигона, продолжаем с первой точки
                        else if ((secIndex.get(pointIntersection.indexOf(poligonJoin.get(poligonJoin.size()-1)))-j)==
                                (secondPol.size()-1))
                            poligonJoin.add(secondPol.get(0));
                        else if ((secIndex.get(pointIntersection.indexOf(poligonJoin.get(poligonJoin.size()-1)))-j)!=0&&
                                ((secIndex.get(pointIntersection.indexOf(poligonJoin.get(poligonJoin.size()-1)))-j)!=
                                       (secondPol.size()-1)))
                            poligonJoin.add(secondPol.get(secIndex.get(pointIntersection.indexOf
                                    (poligonJoin.get(poligonJoin.size()-1)))-j));
                        if (indexPoligon==1)indexPoligon=2;
                        else indexPoligon=1;
                        indexPoint=j;
                        doIt=false;
                        break;
                    }
                    //Проверка на равенство первой и последней точек в общем полигоне
                    if( poligonJoin.get(0).getX()==poligonJoin.get(poligonJoin.size()-1).getX()&&
                            poligonJoin.get(0).getY()==poligonJoin.get(poligonJoin.size()-1).getY()){
                        return poligonJoin;
                    }
                }
                if (countIntersection%2==0){
                    //Проверяем на наличие двух точек пересечения полигонов на одной грани
                    for (int i=0;i<secIndex.size();i++){
                        if (pointIntersection.indexOf(poligonJoin.get(poligonJoin.size()-1))!=i){
                            if (secIndex.get(pointIntersection.indexOf(poligonJoin.get
                                    (poligonJoin.size()-1)))==(int)secIndex.get(i)){
                                poligonJoin.add(pointIntersection.get(i));
                                if (indexPoligon==1)indexPoligon=2; //меняем местами полигоны
                                else indexPoligon=1;
                                flag2=false;
                                break;
                            }
                        }
                    }
                    if (flag2){
                        //Проверка на равенство первой и последней точек в общем полигоне
                        if(poligonJoin.get(0).getX()==poligonJoin.get(poligonJoin.size() - 1).getX()&&
                                  poligonJoin.get(0).getY()==poligonJoin.get(poligonJoin.size()-1).getY()){
                            return poligonJoin;
                        }
                        poligonJoin.add(poligonJoin.get(0));
                        return poligonJoin;
                    }
                }
            }
        }
    }
}
