package Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws Exception {
        List<String>allOutputFile=new LinkedList<String>();
        String inputFolfer = "C://Read";              //Директория с входными файлами
        String outputFolder = "C://Read//Result";     //Директория с файлами поересечения полигонов
        String firstFile=null;
        String secondFile=null;
        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        boolean flag;
        int choice;
        while (true){
            File dir = new File(inputFolfer);
            File[] inputFiles = dir.listFiles();
            for (int i=0; i<inputFiles.length;i++) {
                if ((i+1)>=inputFiles.length) break;
                for (int j=i+1; j<inputFiles.length;j++) {
                    if(!inputFiles[i].isDirectory()&&!inputFiles[j].isDirectory()){
                        flag=true;
                        List<Point> pol1=new LinkedList<Point>();   //Poligon from file 1
                        List<Point> pol2=new LinkedList<Point>();
                        Application apl = new Application();
                        builder1.delete(0,200);
                        builder2.delete(0,200);
                        firstFile=inputFiles[i].getName().replaceAll("[.wkt]","");
                        secondFile=inputFiles[j].getName().replaceAll("[.wkt]","");
                        builder1.append(firstFile).append("_").append(secondFile);
                        builder2.append(secondFile).append("_").append(firstFile);
                        for (String str: allOutputFile)
                        {
                            if (str.equals(builder1.toString()) || str.equals(builder2.toString())){
                                flag=false;
                                break;
                            }
                        }
                        if (flag){
                            Reader File1=new Reader(inputFiles[i].getAbsolutePath());
                            Reader File2=new Reader(inputFiles[j].getAbsolutePath());
                            try {
                                pol1= File1.read();
                                pol2= File2.read();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (pol1==null||pol2==null){
                                System.out.println("Ошибка! Сравнение полигонов из файлов "+
                                        inputFiles[i].getAbsolutePath()+" и "+
                                        inputFiles[j].getAbsolutePath()+ " невозможно!");
                            }
                            allOutputFile.add(builder1.toString());
                            builder2.delete(0, 200);
                            builder2.append(outputFolder).append("//").append(builder1).append(".wkt");
                            if (pol1!=null && pol2!=null){
                                choice = apl.intersection(pol1, pol2);
                                if (choice==0){
                                    Writer out=new Writer(builder2.toString(), null);
                                    out.write();
                                }
                                else{
                                    Writer out=new Writer(builder2.toString(), apl.findJoinPoligon());
                                    out.write();
                                }
                            }
                            else {
                                Writer out=new Writer(builder2.toString(), null);
                                out.write();
                            }

                        }
                    }
                }
            }
        }
    }
}
